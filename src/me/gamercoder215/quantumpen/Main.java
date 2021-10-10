package me.gamercoder215.quantumpen;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.commands.QuantumPen;
import me.gamercoder215.quantumpen.commands.Server;
import me.gamercoder215.quantumpen.edit.EditEntity;
import me.gamercoder215.quantumpen.edit.Pathfinders;
import me.gamercoder215.quantumpen.packets.ClientPacket;

public class Main extends JavaPlugin {
	
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
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
		
    	new QuantumPen(this);
    	new Server(this);
    	
	}
	
}
