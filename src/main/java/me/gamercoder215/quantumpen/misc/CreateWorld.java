package me.gamercoder215.quantumpen.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;

public class CreateWorld implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public CreateWorld(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("createworld").setExecutor(this);
		plugin.getCommand("createworld").setTabCompleter(new CommandTabCompleter());
	}
	
	Random r = new Random();
	
	private void buffer(WorldCreator w, CommandSender sender, boolean teleport) {
		List<String> msgs = new ArrayList<>();
		
		msgs.add(ChatColor.GREEN + "Saying JavaScript is superior...");
		msgs.add(ChatColor.GREEN + "Buffering...");
		msgs.add(ChatColor.GREEN + "Javaing...");
		msgs.add(ChatColor.GREEN + "Planning to take over the world...");
		msgs.add(ChatColor.GREEN + "Speedrunning...");
		msgs.add(ChatColor.GREEN + "Subscribing to Team Inceptus...");
		msgs.add(ChatColor.GREEN + "Playing Tetris...");
		
		new BukkitRunnable() {
			public void run() {
				int i = r.nextInt(msgs.size());
				sender.sendMessage(msgs.get(i));
				msgs.remove(i);
				new BukkitRunnable() {
					public void run() {
						int i = r.nextInt(msgs.size());
						sender.sendMessage(msgs.get(i));
						msgs.remove(i);
						new BukkitRunnable() {
							public void run() {
								int i = r.nextInt(msgs.size());
								sender.sendMessage(msgs.get(i));
								msgs.remove(i);
								World newWorld = Bukkit.createWorld(w);
								sender.sendMessage(ChatColor.GREEN + "Done!");
								if (sender instanceof Player && teleport) {
									Player p = (Player) sender;
									p.teleport(newWorld.getSpawnLocation());
								}
							}
						}.runTaskLater(plugin, 60);
					}
				}.runTaskLater(plugin, 60);
			}
		}.runTaskLater(plugin, 60);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world name.");
			return false;
		}
		
		if (args.length < 2 && Bukkit.getWorld(args[0]) == null) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world type.");
			return false;
		}
		
		try {
			WorldCreator w = new WorldCreator(args[0]);
			
			List<String> arguments = new ArrayList<>();
			
			for (String s : args) {
				arguments.add(s.toLowerCase());
			}
			if (Bukkit.getWorld(args[0]) == null) {
				
				
				w.environment(Environment.valueOf(args[1].toLowerCase().replaceAll("overworld", "normal").toUpperCase()));

				
				try {
					if (arguments.contains("-str")) {
						w = w.generateStructures(Boolean.parseBoolean(arguments.get(arguments.indexOf("-s") + 1)));
					}
				} catch (IndexOutOfBoundsException e) {
					QuantumPen.sendValidType(sender, ArgumentType.BOOLEAN);
					return false;
				}
				
				try {
					if (arguments.contains("-h")) {
						w = w.hardcore(Boolean.parseBoolean(arguments.get(arguments.indexOf("-h") + 1)));
					}
				} catch (IndexOutOfBoundsException e) {
					QuantumPen.sendValidType(sender, ArgumentType.BOOLEAN);
					return false;
				}
				
				try {
					if (arguments.contains("-s")) {
						w = w.seed(Long.parseLong(arguments.get(arguments.indexOf("-s") + 1)));
					}
				} catch (IndexOutOfBoundsException e) {
					QuantumPen.sendValidType(sender, ArgumentType.INTEGER);
					return false;
				}
				
				boolean teleport = true;
				
				if (arguments.contains("-notp")) teleport = false;
				
				sender.sendMessage(ChatColor.GREEN + "Creating World...");
				buffer(w, sender, teleport);
				return true;
			} else {
				boolean teleport = true;
				
				if (arguments.contains("-notp")) teleport = false;
				
				sender.sendMessage(ChatColor.GREEN + "Loading World...");
				buffer(w, sender, teleport);
				return true;
			}
			
		} catch (IllegalArgumentException e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
			return false;
		}
	}
	
	
	
}
