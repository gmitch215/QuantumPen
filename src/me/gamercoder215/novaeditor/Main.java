package me.gamercoder215.novaeditor;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.novaeditor.commands.NovaEditor;
import me.gamercoder215.novaeditor.packets.ClientPacket;

public class Main extends JavaPlugin {
	
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "NovaEditor" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
	}

	public static void sendInvalidArgs(CommandSender sender) {
	  	sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "NovaEditor" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide valid arguments.");
	}

	public static void sendNoPermission(CommandSender sender) {
	  	sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "NovaEditor" + ChatColor.GOLD + "] " + ChatColor.RED + "You do not have permission to use this command/command option.");
	}
	
	public void onEnable() {
		new ClientPacket(this);
		
    	new NovaEditor(this);
    	
    	
	}
	
}