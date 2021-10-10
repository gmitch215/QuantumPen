package me.gamercoder215.quantumpen.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;

public class Server implements CommandExecutor {
	
	protected Main plugin;
	
	public Server(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("server").setExecutor(this);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendInvalidArgs(sender);
			return false;
		}
		
		switch (args[0]) {
			case "get": {
				if (args.length < 2) {
					Main.sendInvalidArgs(sender);
					return false;
				}
				
				switch (args[1]) {
					
				}
			}
		}
		
		sender.sendMessage(ChatColor.GOLD + "Returned: ");
		return true;
	}

}
