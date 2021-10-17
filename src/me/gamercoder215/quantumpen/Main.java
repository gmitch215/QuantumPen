package me.gamercoder215.quantumpen;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.commands.PlayerData;
import me.gamercoder215.quantumpen.commands.QuantumPen;
import me.gamercoder215.quantumpen.commands.Server;
import me.gamercoder215.quantumpen.edit.Block;
import me.gamercoder215.quantumpen.edit.Chunk;
import me.gamercoder215.quantumpen.edit.EditEntity;
import me.gamercoder215.quantumpen.edit.Pathfinders;
import me.gamercoder215.quantumpen.edit.World;
import me.gamercoder215.quantumpen.misc.CreateWorld;
import me.gamercoder215.quantumpen.misc.UnloadWorld;
import me.gamercoder215.quantumpen.packets.ClientPacket;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;
import me.gamercoder215.quantumpen.utils.DisabledCommandCatch;

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
	
	public static void sendCommandDisabled(CommandSender sender) {
		sendPluginMessage(sender, ChatColor.RED + "This command is disabled in the QuantumPen configuration.");
	}
	
	public static boolean isCommandDisabled(Main main, String commandName) {
		return (main.getConfig().getStringList("DisabledCommands").contains(commandName.toLowerCase()));
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
		
		new CreateWorld(this);
		new UnloadWorld(this);
		
		new PlayerData(this);
    	new QuantumPen(this);
    	new Server(this);
    	
    	this.saveConfig();
    	
    	// Configuration Checks
    	if (this.getConfig().get("DisabledCommands") == null) {
    		this.getConfig().set("DisabledCommands", new ArrayList<String>());
    	}
    	
    	if (!(this.getConfig().get("DisabledCommands") instanceof ArrayList<?>)) {
    		this.getConfig().set("DisabledCommands", new ArrayList<String>());
    	}
    	
    	if (this.getConfig().get("UseTabComplete") == null) {
    		this.getConfig().set("UseTabComplete", true);
    	}
    	
    	if (!(this.getConfig().get("UseTabComplete") instanceof Boolean)) {
    		this.getConfig().set("UseTabComplete", true);
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
