package me.gamercoder215.quantumpen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.SpawnCategory;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class Server implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public Server(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("server").setExecutor(this);
		plugin.getCommand("server").setTabCompleter(new CommandTabCompleter());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			QuantumPen.sendInvalidArgs(sender);
			return false;
		}
		org.bukkit.Server srv = Bukkit.getServer();
		switch (args[0]) {
			case "get": {
				if (args.length < 2) {
					QuantumPen.sendInvalidArgs(sender);
					return false;
				}
				Object returnType = "UNKNOWN";
				switch (args[1].replaceAll("minecraft:", "").toLowerCase()) {
					case "settings_allowend": {
						returnType = srv.getAllowEnd();
						break;
					}
					case "settings_allownether": {
						returnType = srv.getAllowNether();
						break;
					}
					case "settings_allowflight": {
						returnType = srv.getAllowFlight();
						break;
					}
					case "chunk_limit_spawnambient": {
						returnType = srv.getSpawnLimit(SpawnCategory.AMBIENT);
						break;
					}
					case "chunk_limit_spawnanimal": {
						returnType = srv.getSpawnLimit(SpawnCategory.ANIMAL);
						break;
					}
					case "bukkit_version": {
						returnType = srv.getBukkitVersion();
						break;
					}
					case "settings_defaultgamemode": {
						returnType = srv.getDefaultGameMode().name();
						break;
					}
					case "settings_generatestructures": {
						returnType = srv.getGenerateStructures();
						break;
					}
					case "server_motd": {
						returnType = srv.getMotd();
						break;
					}
					case "server_name": {
						returnType = srv.getName();
						break;
					}
					case "settings_onlinemode": {
						returnType = srv.getOnlineMode();
						break;
					}
					case "server_version": {
						returnType = srv.getVersion();
						break;
					}
					case "settings_hardcore": {
						returnType = srv.isHardcore();
						break;
					}
					case "settings_viewdistance": {
						returnType = srv.getViewDistance();
						break;
					}
					case "chunk_limit_spawnambient_water": {
						returnType = srv.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
						break;
					}
					case "chunk_limit_spawnanimal_water": {
						returnType = srv.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
						break;
					}
					case "server_defaultworld_type": {
						returnType = srv.getWorldType();
						break;
					}
					case "server_port": {
						returnType = srv.getPort();
						break;
					}
					case "settings_spawnprotection": {
						returnType = srv.getSpawnRadius();
						break;
					}
					case "spawns_ambientticks": {
						returnType = srv.getTicksPerSpawns(SpawnCategory.AMBIENT);
						break;
					}
					case "spawns_animalticks": {
						returnType = srv.getTicksPerSpawns(SpawnCategory.ANIMAL);
						break;
					}
					case "spawns_monsterticks": {
						returnType = srv.getTicksPerSpawns(SpawnCategory.MONSTER);
						break;
					}
					case "spawns_ambientticks_water": {
						returnType = srv.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
						break;
					}
					case "spawns_waterticks": {
						returnType = srv.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
						break;
					}
					case "settings_idletimeout": {
						returnType = srv.getIdleTimeout();
						break;
					}
				}
				sender.sendMessage(ChatColor.GOLD + "Returned:\n" + ChatColor.BLUE + returnType.toString());
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
				QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This option does not exist.");
				return false;
		}
	}

}
