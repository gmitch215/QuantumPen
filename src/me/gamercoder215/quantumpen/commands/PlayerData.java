package me.gamercoder215.quantumpen.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;

public class PlayerData implements CommandExecutor {
	
	protected Main plugin;
	
	public PlayerData(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("playerdata").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
