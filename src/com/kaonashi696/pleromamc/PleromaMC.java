package com.kaonashi696.pleromamc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PleromaMC extends JavaPlugin {
    // Fired when plugin is first enabled
	FileConfiguration config = getConfig();
	
	public FileConfiguration thefile() {
    	return config;
    }  

    @Override
    public void onEnable() {    	
    	getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    	getServer().getPluginManager().registerEvents(new PlayerAchievementsListener(this), this);
    	this.getCommand("customcmd").setExecutor(new CustomCommand(this));
    	
    	config.addDefault("oauth", "TOKEN");
    	config.addDefault("post_url", "https://example.com/api/v1/statuses");
    	config.options().copyDefaults(true);
        saveConfig();

    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
    
}