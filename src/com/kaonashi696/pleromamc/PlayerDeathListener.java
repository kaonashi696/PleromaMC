package com.kaonashi696.pleromamc;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener{
	
	PleromaMC core;

	public PlayerDeathListener(PleromaMC core) {
	  this.core = core;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
		String deathMessage = event.getDeathMessage();
		if (StringUtils.isBlank(deathMessage)) return;
		
		HTTPSPostRequest.sendPOST(core, "status=" + deathMessage);
	}
	

}
