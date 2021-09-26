package me.gamercoder215.novaeditor.edit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.novaeditor.Main;

public class EditEntity implements CommandExecutor {
	
	protected Main plugin;
	
	public EditEntity(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("editentity").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		return true;
	}
	
}
