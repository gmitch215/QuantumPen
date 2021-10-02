package me.gamercoder215.quantumpen.edit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.quantumpen.Main;

public class EditEntity implements CommandExecutor {
	
	protected Main plugin;
	
	public EditEntity(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("editentity").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an entities's UUID. If you do not know what that is, do some research.");
			return false;
		}

		try {
			
			UUID entityUUID = UUID.fromString(args[0]);

			if (Bukkit.getEntity(entityUUID) == null) {
				Main.sendPluginMessage(sender, "Please provide a living entity's UUID.");
				return false;
			}

			Entity entity = Bukkit.getEntity(entityUUID);

			if (!(entity instanceof LivingEntity)) {
				Main.sendPluginMessage(sender, "Please provide a living entity's UUID.");
				return false;
			}

			LivingEntity len = (LivingEntity) entity;

			if (args.length < 2) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid type of editing.");
				return false;
			}

			switch (args[1].toLowerCase()) {
				case "fetch":
					
					break;
				case "edit":
					

					break;
				case "do":
					break;
				default:
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid type of editing.");
					return false;
			}
			if (args[1].equalsIgnoreCase("edit")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully edited entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			} else if (args[1].equalsIgnoreCase("fetch")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully fetched data of entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			} else if (args[1].equalsIgnoreCase("do")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully did action of entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			}

		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
			return false;
		}

		return true;
	}
	
}
