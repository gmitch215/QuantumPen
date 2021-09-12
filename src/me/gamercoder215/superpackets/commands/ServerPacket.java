package me.gamercoder215.superpackets.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;

import me.gamercoder215.superpackets.Main;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.world.EnumDifficulty;

public class ServerPacket implements CommandExecutor {
	
	protected Main plugin;
	
	public ServerPacket(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("serverpacket").setExecutor(this);
		plugin.getCommand("serverpacke").setTabCompleter(new CommandTabCompleter());
	}
	
	public static int matchDifficulty(String name) {
		if (name.equalsIgnoreCase("peaceful")) return 0;
		else if (name.equalsIgnoreCase("easy")) return 1;
		else if (name.equalsIgnoreCase("normal")) return 2;
		else if (name.equalsIgnoreCase("hard")) return 3;
		else return 2;
	}
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid packet.");
			return false;
		}
		
		switch (args[0].replaceAll("minecraft:", "")) {
			case "set_difficulty":
				
				if (args.length < 2) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
					return false;
				}
				
				if (!(args[1].equalsIgnoreCase("easy")) || !(args[1].equalsIgnoreCase("normal")) || !(args[1].equalsIgnoreCase("peaceful")) || !(args[1].equalsIgnoreCase("hard"))) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
					return false;
				}
				
				PacketPlayInDifficultyChange s = new PacketPlayInDifficultyChange(EnumDifficulty.getById(matchDifficulty(args[1].toLowerCase())));
				
				
				
				break;
		}
		
		return true;
	}
	
}
