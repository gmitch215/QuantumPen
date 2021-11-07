package me.gamercoder215.quantumpen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class Server implements CommandExecutor {
	
	protected Main plugin;
	
	public Server(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("server").setExecutor(this);
		plugin.getCommand("server").setTabCompleter(new CommandTabCompleter());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendInvalidArgs(sender);
			return false;
		}
		org.bukkit.Server srv = Bukkit.getServer();
		switch (args[0]) {
			case "get": {
				if (args.length < 2) {
					Main.sendInvalidArgs(sender);
					return false;
				}
				Object returnType = "UNKNOWN";
				switch (args[1].replaceAll("minecraft:", "").toLowerCase()) {
					case "settings_allowend": {
						returnType = srv.getAllowEnd();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_allownether": {
						returnType = srv.getAllowNether();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_allowflight": {
						returnType = srv.getAllowFlight();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "chunk_limit_spawnambient": {
						returnType = srv.getAmbientSpawnLimit();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "chunk_limit_spawnanimal": {
						returnType = srv.getAnimalSpawnLimit();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "bukkit_version": {
						returnType = srv.getBukkitVersion();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_defaultgamemode": {
						returnType = srv.getDefaultGameMode().name();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_generatestructures": {
						returnType = srv.getGenerateStructures();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "server_motd": {
						returnType = srv.getMotd();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "server_name": {
						returnType = srv.getName();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_onlinemode": {
						returnType = srv.getOnlineMode();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "server_version": {
						returnType = srv.getVersion();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_hardcore": {
						returnType = srv.isHardcore();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_viewdistance": {
						returnType = srv.getViewDistance();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "chunk_limit_spawnambient_water": {
						returnType = srv.getWaterAmbientSpawnLimit();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "chunk_limit_spawnanimal_water": {
						returnType = srv.getWaterAnimalSpawnLimit();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "server_defaultworld_type": {
						returnType = srv.getWorldType();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "server_port": {
						returnType = srv.getPort();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_spawnprotection": {
						returnType = srv.getSpawnRadius();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "spawns_ambientticks": {
						returnType = srv.getTicksPerAmbientSpawns();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "spawns_animalticks": {
						returnType = srv.getTicksPerAnimalSpawns();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "spawns_monsterticks": {
						returnType = srv.getTicksPerMonsterSpawns();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "spawns_ambientticks_water": {
						returnType = srv.getTicksPerWaterAmbientSpawns();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "spawns_waterticks": {
						returnType = srv.getTicksPerWaterSpawns();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
					case "settings_idletimeout": {
						returnType = srv.getIdleTimeout();
						sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
						break;
					}
				}
				return true;
			}
			case "reloadwhitelist": {
				sender.sendMessage(ChatColor.GREEN + "Reloading Whitelist...");
				srv.reloadWhitelist();
				sender.sendMessage(ChatColor.GREEN + "Whitelist successfully reloaded!");
				return true;
			}
			case "reloaddata": {
				sender.sendMessage(ChatColor.GREEN + "Reloading Data...");
				srv.reloadData();
				sender.sendMessage(ChatColor.GREEN + "Data successfully reloaded!");
				return true;
			}
			default:
				Main.sendPluginMessage(sender, ChatColor.RED + "This option does not exist.");
				return false;
		}
	}

}
