package com.kaonashi696.pleromamc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.kaonashi696.pleromamc.commands.CommandReload;
import com.kaonashi696.pleromamc.listeners.PlayerAdvancementDoneListener;
import com.kaonashi696.pleromamc.listeners.PlayerDeathListener;

public final class PleromaMC extends JavaPlugin {
    // Fired when plugin is first enabled
	FileConfiguration config = getConfig();
	
	public FileConfiguration thefile() {
    	return config;
    }  
	
    @Override
    public void onEnable() {    	
    	getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    	getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneListener(this), this);
    	this.getCommand("reload").setExecutor(new CommandReload());
    	
    	config.addDefault("pleroma", false);
    	config.addDefault("pleromaOauth", "TOKEN");
    	config.addDefault("pleromaPostUrl", "https://example.com/api/v1/statuses");
    	config.addDefault("twitter", false);
    	config.addDefault("twitterConsumerKey", "consumerKey (API Key)");
    	config.addDefault("twitterConsumerSecret", "consumerSecret (API Secret key)");
    	config.addDefault("twitterAccessToken", "accessToken");
    	config.addDefault("twitterAccessTokenSecret", "accessTokenSecret");
    	config.options().copyDefaults(true);
        saveConfig();

    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
    
    public static PleromaMC getPlugin() {
        return getPlugin(PleromaMC.class);
    }
    
    private static void logThrowable(Throwable throwable, Consumer<String> logger) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));

        for (String line : stringWriter.toString().split("\n")) logger.accept(line);
    }

    public static void error(String message) {
        getPlugin().getLogger().severe(message);
    }
    public static void error(Throwable throwable) {
         logThrowable(throwable, PleromaMC::error);
    }
    public static void error(String message, Throwable throwable) {
        error(message);
        error(throwable);
    }

    
}