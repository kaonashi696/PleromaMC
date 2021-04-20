package com.kaonashi696.pleromamc.listeners;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import com.kaonashi696.pleromamc.HTTPSPostRequest;
import com.kaonashi696.pleromamc.PleromaMC;

public class PlayerAdvancementDoneListener implements Listener {
	
	PleromaMC core;

	public PlayerAdvancementDoneListener(PleromaMC core) {
	  this.core = core;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        // return if advancement or player objects are knackered because this can apparently happen for some reason
        if (event.getAdvancement() == null || event.getAdvancement().getKey().getKey().contains("recipe/") || event.getPlayer() == null) return;

        Bukkit.getScheduler().runTaskAsynchronously(PleromaMC.getPlugin(), () -> {
			try {
				runAsync(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }
	
	private void runAsync(PlayerAdvancementDoneEvent event) throws IOException {
		
		FileConfiguration config = core.getConfig();
		String post_url = config.getString("post_url") + "api/v1/statuses";
		
        try {
            Object craftAdvancement = ((Object) event.getAdvancement()).getClass().getMethod("getHandle").invoke(event.getAdvancement());
            Object advancementDisplay = craftAdvancement.getClass().getMethod("c").invoke(craftAdvancement);
            boolean display = (boolean) advancementDisplay.getClass().getMethod("i").invoke(advancementDisplay);
            if (!display) return;
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            PleromaMC.error(e);
            return;
        }
        
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        		
        Advancement advancement = event.getAdvancement();
        String advancementTitle = getTitle(advancement);
        
        HTTPSPostRequest.sendPOST(core, post_url, "status=:trophy: " + displayName + " has made the advancement " + advancementTitle);

		}
        
        private static final Map<Advancement, String> ADVANCEMENT_TITLE_CACHE = new ConcurrentHashMap<>();
        public static String getTitle(Advancement advancement) {
            return ADVANCEMENT_TITLE_CACHE.computeIfAbsent(advancement, v -> {
                try {
                    Object handle = advancement.getClass().getMethod("getHandle").invoke(advancement);
                    Object advancementDisplay = Arrays.stream(handle.getClass().getMethods())
                            .filter(method -> method.getReturnType().getSimpleName().equals("AdvancementDisplay"))
                            .filter(method -> method.getParameterCount() == 0)
                            .findFirst().orElseThrow(() -> new RuntimeException("Failed to find AdvancementDisplay getter for advancement handle"))
                            .invoke(handle);
                    if (advancementDisplay == null) throw new RuntimeException("Advancement doesn't have display properties");

                    try {
                        Field advancementMessageField = advancementDisplay.getClass().getDeclaredField("a");
                        advancementMessageField.setAccessible(true);
                        Object advancementMessage = advancementMessageField.get(advancementDisplay);
                        Object advancementTitle = advancementMessage.getClass().getMethod("getString").invoke(advancementMessage);
                        return (String) advancementTitle;
                    } catch (Exception e){
                    }

                    Field titleComponentField = Arrays.stream(advancementDisplay.getClass().getDeclaredFields())
                            .filter(field -> field.getType().getSimpleName().equals("IChatBaseComponent"))
                            .findFirst().orElseThrow(() -> new RuntimeException("Failed to find advancement display properties field"));
                    titleComponentField.setAccessible(true);
                    Object titleChatBaseComponent = titleComponentField.get(advancementDisplay);
                    String title = (String) titleChatBaseComponent.getClass().getMethod("getText").invoke(titleChatBaseComponent);
                    if (StringUtils.isNotBlank(title)) return title;
                    return null;
                } catch (Exception e) {

                    String rawAdvancementName = advancement.getKey().getKey();
                    return Arrays.stream(rawAdvancementName.substring(rawAdvancementName.lastIndexOf("/") + 1).toLowerCase().split("_"))
                            .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                            .collect(Collectors.joining(" "));
                }
            });
        }
        
	
}
