package com.kaonashi696.pleromamc.listeners;

import java.io.IOException;

import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import com.kaonashi696.pleromamc.PleromaMC;
import com.kaonashi696.pleromamc.pleroma.HTTPSPostRequest;
import com.kaonashi696.pleromamc.twitter.tweet;

public class PlayerAchievementsListener implements Listener {
	
	PleromaMC core;

	public PlayerAchievementsListener(PleromaMC core) {
	  this.core = core;
	}
	
	FileConfiguration config = core.getConfig();
	String post_url = config.getString("pleromaPostUrl") + "api/v1/statuses";
	
	@EventHandler
	public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) throws IOException {
		if (event == null) return;
		
		//Advancement Advancement = event.getAdvancement();
		Advancement advancement = event.getAdvancement();
		//String Player = event.getPlayer().toString();
		Player player = event.getPlayer();
		String displayName = player.getDisplayName();
		//if (StringUtils.isBlank(Advancement)) return;
		
		HTTPSPostRequest.sendPOST(core, post_url, "status=" + displayName + " has made the advancement " + advancement);
		tweet.sendPOST(core, displayName + " has made the advancement " + advancement);
	}

}
