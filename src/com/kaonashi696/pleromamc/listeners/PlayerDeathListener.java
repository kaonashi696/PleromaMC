package com.kaonashi696.pleromamc.listeners;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.kaonashi696.pleromamc.HTTPSPostRequest;
import com.kaonashi696.pleromamc.PleromaMC;

public class PlayerDeathListener implements Listener{
	
	PleromaMC core;

	public PlayerDeathListener(PleromaMC core) {
	  this.core = core;
	  
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        
        Bukkit.getScheduler().runTaskAsynchronously(PleromaMC.getPlugin(), () -> {
			try {
				runAsync(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }
	
	private void runAsync(PlayerDeathEvent event) throws IOException {
		
		FileConfiguration config = core.getConfig();
		String post_url = config.getString("post_url") + "api/v1/statuses";
		
		String deathMessage = event.getDeathMessage();
		List<ItemStack> drops = event.getDrops();
		
		String items = drops.stream()
				.map(item -> String.format(":%s: x%d", item.getType().toString().toLowerCase(), item.getAmount()))
			    .collect(Collectors.joining(", "));
		
		if (StringUtils.isBlank(deathMessage)) return;
		
		boolean ans = drops.isEmpty();
        if (ans == true)
    		HTTPSPostRequest.sendPOST(core, post_url, "status=:skull_crossbones: #" + deathMessage + " and didn't drop anything.");
        else
    		HTTPSPostRequest.sendPOST(core, post_url, "status=:skull_crossbones: #" + deathMessage + " and dropped " + items);
		
	}
	

}
