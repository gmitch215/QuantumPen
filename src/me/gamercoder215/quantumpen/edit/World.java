package me.gamercoder215.quantumpen.edit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class World implements CommandExecutor {
	
	protected Main plugin;
	
	public World(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("world").setExecutor(this);
		plugin.getCommand("world").setTabCompleter(new CommandTabCompleter());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}
	
}
