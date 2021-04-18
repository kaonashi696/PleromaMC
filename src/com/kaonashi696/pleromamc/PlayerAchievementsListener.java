package com.kaonashi696.pleromamc;

import java.io.IOException;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAchievementsListener implements Listener {
	
	PleromaMC core;

	public PlayerAchievementsListener(PleromaMC core) {
	  this.core = core;
	}
	
	@EventHandler
	public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) throws IOException {
		if (event == null) return;
		
		//Advancement Advancement = event.getAdvancement();
		Advancement advancement = event.getAdvancement();
		//String Player = event.getPlayer().toString();
		Player player = event.getPlayer();
		//if (StringUtils.isBlank(Advancement)) return;
		
		HTTPSPostRequest.sendPOST(core, "status=" + player + " has made the advancement " + advancement);
	}

}
