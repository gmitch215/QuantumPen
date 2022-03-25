package me.gamercoder215.quantumpen.commands;

import org.bukkit.Bukkit;

/*
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
*/

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class QuantumPenCommand implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public QuantumPenCommand(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("quantumpen").setExecutor(this);
		plugin.getCommand("quantumpen").setTabCompleter(new CommandTabCompleter());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			QuantumPen.sendInvalidArgs(sender);
			return false;
		}
		switch (args[0].toLowerCase()) {
			case "info": {
				sender.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.DARK_GREEN + "QuantumPen by GamerCoder215\n\n" + ChatColor.GOLD + "Version v" + plugin.getDescription().getVersion());
				break;
			}
			case "reloadconfig": {
				sender.sendMessage(ChatColor.GREEN + "Reloading Configuration...");
				plugin.getLogger().info("Reloding Configuration...");
				
				plugin.saveConfig();
				plugin.reloadConfig();
				plugin.saveConfig();
				
				
				plugin.getLogger().info("Successfully Reloaded QuantumPen Configuration");
				sender.sendMessage(ChatColor.GREEN + "Configuration Reloaded & Saved! This does not change the QuantumPen JAR; You need to reload or restart your server for the JAR to update.");
				break;
			}
			case "cleartickets": {
				sender.sendMessage(ChatColor.GREEN + "Clearing Chunk Tickets...");
				
				for (World w : Bukkit.getWorlds()) {
					w.removePluginChunkTickets(plugin);
				}
				
				sender.sendMessage(ChatColor.GREEN + "Cleared all Chunk Tickets in every loaded world.");
				break;
			}
		}

		return true;
	}
}
