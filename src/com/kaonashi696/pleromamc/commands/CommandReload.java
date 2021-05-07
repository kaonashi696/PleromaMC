package com.kaonashi696.pleromamc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kaonashi696.pleromamc.PleromaMC;

public class CommandReload implements CommandExecutor {
	
	PleromaMC core;

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if(player.hasPermission("pleromamc.reload"));
        core.reloadConfig();
        player.sendMessage(ChatColor.GREEN + "Configuration Reloaded!");
        return false;
    }
}

