package com.kaonashi696.pleromamc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

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
    	getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneListener(this), this);
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