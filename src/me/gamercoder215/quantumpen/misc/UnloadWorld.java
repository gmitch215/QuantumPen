package me.gamercoder215.quantumpen.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class UnloadWorld implements CommandExecutor {
	
	protected Main plugin;
	
	public UnloadWorld(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("unloadworld").setExecutor(this);
		plugin.getCommand("unloadworld").setTabCompleter(new CommandTabCompleter());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world name.");
			return false;
		}
		
		if (Bukkit.getWorld(args[0]) == null) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world.");
			return false;
		}
		World w = Bukkit.getWorld(args[0]);
		sender.sendMessage(ChatColor.GREEN + "Unloading...");
		
		for (Player p : w.getPlayers()) {
			p.sendMessage(ChatColor.GOLD + "This world is unloading! Evacuating...");
			p.teleport(new Location(Bukkit.getWorlds().get(0), Bukkit.getWorlds().get(0).getSpawnLocation().getX(), Bukkit.getWorlds().get(0).getSpawnLocation().getY(), Bukkit.getWorlds().get(0).getSpawnLocation().getZ()));
		}
		
		if (args.length < 2) {
			if (Bukkit.unloadWorld(w, true)) {
				sender.sendMessage(ChatColor.GREEN + "Successfully unloaded world!");
			} else {
				sender.sendMessage(ChatColor.RED + "Unloading world unsuccessful.");
			}
		} else {
			if (Bukkit.unloadWorld(w, Boolean.parseBoolean(args[1]))) {
				sender.sendMessage(ChatColor.GREEN + "Successfully unloaded world!");
			} else {
				sender.sendMessage(ChatColor.RED + "Unloading world unsuccessful.");
			}
		}
		
		
		return true;
	}

}
