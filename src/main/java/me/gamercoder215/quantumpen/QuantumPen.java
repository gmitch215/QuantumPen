package me.gamercoder215.quantumpen;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.commands.PlayerData;
import me.gamercoder215.quantumpen.commands.QuantumPenCommand;
import me.gamercoder215.quantumpen.commands.Server;
import me.gamercoder215.quantumpen.edit.Block;
import me.gamercoder215.quantumpen.edit.Chunk;
import me.gamercoder215.quantumpen.edit.EditEntity;
import me.gamercoder215.quantumpen.edit.Pathfinders;
import me.gamercoder215.quantumpen.edit.World;
import me.gamercoder215.quantumpen.misc.CreateWorld;
import me.gamercoder215.quantumpen.misc.Pen;
import me.gamercoder215.quantumpen.misc.UnloadWorld;
import me.gamercoder215.quantumpen.packets.ClientPacket;
import me.gamercoder215.quantumpen.premium.PremiumFeatures;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;
import me.gamercoder215.quantumpen.utils.DisabledCommandCatch;

public class QuantumPen extends JavaPlugin {

	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "QuantumPen" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
	}

	public static void sendValidType(CommandSender sender, ArgumentType type) {
		switch (type) {
			case BOOLEAN: {
				sendPluginMessage(sender, ChatColor.RED + "Please provide true or false.");
			}
			case INTEGER:{
				sendPluginMessage(sender, ChatColor.RED + "Please provide a valid integer.");
			}
			case ENTITYTYPE: {
				sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type.");
			}
			case DECIMAL: {
				sendPluginMessage(sender, ChatColor.RED + "Please provide a valid decimal.");
			}
			default: {
				sendPluginMessage(sender, ChatColor.RED + "Please provide a valid " + type.name().toLowerCase());
			}
		}
	}

	public static void sendInvalidArgs(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "Please provide valid arguments.");
	}

	public static void sendNoPermission(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "You do not have permission to use this command/command option.");
	}

	public static void sendValidSpeedModifier(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "Please provide a valid speed modifier.");
	}
	
	public static void sendCommandDisabled(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "This command is disabled in the QuantumPen configuration.");
	}

	public static void sendError(CommandSender sender, String message) {
		sendPluginMessage(sender, ChatColor.RED + message);
	}
	
	public static boolean isCommandDisabled(String commandName) {
		return (JavaPlugin.getPlugin(QuantumPen.class).getConfig().getStringList("DisabledCommands").contains(commandName.toLowerCase()));
	}
	
	public void onEnable() {
		this.saveDefaultConfig();
		this.saveConfig();
		
		new ClientPacket(this);
		
		new Pathfinders(this);
		new EditEntity(this);
		
		new Chunk(this);
		new Block(this);
		new World(this);
		
		new Pen(this);
		new CreateWorld(this);
		new UnloadWorld(this);
		
		new PlayerData(this);
    	new QuantumPenCommand(this);
    	new Server(this);
    	
		// Premium

		if (TypeManager.isPremium()) {
			getLogger().info("Premium Detected! Thank you for purchasing :)");

			PremiumFeatures.registerCommands();
		} else {
			getLogger().info("Free Detected! Please consider buying the full version.");
		}

    	// Configuration Checks
    	if (!(this.getConfig().isList("DisabledCommands"))) {
    		this.getConfig().set("DisabledCommands", new ArrayList<String>());
    	}
    	
    	if (!(this.getConfig().isBoolean("UseTabComplete"))) {
    		this.getConfig().set("UseTabComplete", true);
    	}
    	
    	if (!(this.getConfig().isInt("CalculateDigits"))) {
    		this.getConfig().set("CalculateDigits", 7);
    	}
    	
    	this.saveConfig();
    	
    	// Disabled Commands
    	new DisabledCommandCatch(this);
    	
    	// Tab Complete
    	if (!(this.getConfig().getBoolean("UseTabComplete"))) {
			for (Command c : PluginCommandYamlParser.parse(this)) {
				((PluginCommand) c).setTabCompleter(null);
			}
    	}
	}
	
}