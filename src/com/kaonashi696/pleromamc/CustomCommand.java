package com.kaonashi696.pleromamc;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomCommand implements CommandExecutor {
	
	PleromaMC core;

	public CustomCommand(PleromaMC core) {
	  this.core = core;
	}

	FileConfiguration config = core.getConfig();
	String post_url = config.getString("post_url") + "api/v1/accounts/update_credentials";
	
    // This method is called, when somebody uses our command
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
//            Player player = (Player) sender;
//
//            // Create a new ItemStack (type: diamond)
//            ItemStack diamond = new ItemStack(Material.DIAMOND);
//
//            // Create a new ItemStack (type: brick)
//            ItemStack bricks = new ItemStack(Material.BRICK, 20);
//
//            // Give the player our items (comma-seperated list of all ItemStack)
//            player.getInventory().addItem(bricks, diamond);
            
            try {
				HTTPSPatchRequest.sendPATCH(core, post_url, "note=" + "Custom command executed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return true;

	}
}