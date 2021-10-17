package me.gamercoder215.quantumpen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.commands.PlayerData;
import me.gamercoder215.quantumpen.commands.QuantumPen;
import me.gamercoder215.quantumpen.commands.Server;
import me.gamercoder215.quantumpen.edit.Block;
import me.gamercoder215.quantumpen.edit.Chunk;
import me.gamercoder215.quantumpen.edit.EditEntity;
import me.gamercoder215.quantumpen.edit.Pathfinders;
import me.gamercoder215.quantumpen.edit.World;
import me.gamercoder215.quantumpen.packets.ClientPacket;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;

public class Main extends JavaPlugin {
	
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
	}

	public static void sendValidType(CommandSender sender, CommandTabCompleter.ArgumentType type) {
		if (type == ArgumentType.BOOLEAN) {
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide true or false.");
		} else if (type == ArgumentType.INTEGER) {
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide a valid integer.");
		} else if (type == ArgumentType.ENTITYTYPE) {
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide a valid entity type.");
		} else if (type == ArgumentType.DECIMAL) {
			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "]" + ChatColor.RED + "Please provide a valid decimal.");
		}
	}

	public static void sendInvalidArgs(CommandSender sender) {
	  	sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide valid arguments.");
	}

	public static void sendNoPermission(CommandSender sender) {
	  	sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.RED + "You do not have permission to use this command/command option.");
	}

	public static void sendValidSpeedModifier(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "Please provide a valid speed modifier.");
	}
	
	public void onEnable() {
		new ClientPacket(this);
		
		new Pathfinders(this);
		new EditEntity(this);
		
		new Chunk(this);
		new Block(this);
		new World(this);
		new PlayerData(this);
		
    	new QuantumPen(this);
    	new Server(this);
    	
	}
	
}
