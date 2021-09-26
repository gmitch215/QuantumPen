package me.gamercoder215.novaeditor;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.novaeditor.commands.ClientPacket;
import me.gamercoder215.novaeditor.commands.SuperPackets;

public class Main extends JavaPlugin {
	
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Super Packets" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
	}

	public static void sendInvalidArgs(CommandSender sender) {
	  	sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Super Packets" + ChatColor.GOLD + "] " + ChatColor.RED + "Please provide valid arguments.");
	}
	
	public void onEnable() {
		new ClientPacket(this);
    	new SuperPackets(this);
	}
	
}
