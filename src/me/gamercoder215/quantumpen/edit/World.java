package me.gamercoder215.quantumpen.edit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class World implements CommandExecutor {
	
	protected Main plugin;
	
	public World(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("world").setExecutor(this);
		plugin.getCommand("world").setTabCompleter(new CommandTabCompleter());
	}

	private void success(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Successfully completed action!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world.");
			return false;
		}

		if (Bukkit.getWorld(args[0]) == null) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world.");
			return false;
		}

		org.bukkit.World w = Bukkit.getWorld(args[0]);

		if (args.length < 2) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid action.");
			return false;
		}
		try {
			switch (args[1].replaceAll("minecraft:", "").toLowerCase()) {
				case "server_save": {
					sender.sendMessage(ChatColor.GREEN + "Saving...");
					w.save();
					sender.sendMessage(ChatColor.GREEN + "World Successfully Saved!");
					break;
				}
				case "spawns_ambientlimit": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setAmbientSpawnLimit(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_animallimit": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setAnimalSpawnLimit(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "server_autosave": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.BOOLEAN);
						return false;
					}

					w.setAutoSave(Boolean.parseBoolean(args[3]));
					success(sender);
					break;
				}
				case "game_clearweathertime": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setClearWeatherDuration(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "settings_difficulty": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
						return false;
					}

					Difficulty d = Difficulty.valueOf(args[3].toUpperCase());

					w.setDifficulty(d);
					success(sender);
					break;
				}
				case "settings_hardcore": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.BOOLEAN);
						return false;
					}

					w.setHardcore(Boolean.parseBoolean(args[3]));
					success(sender);
					break;
				}
				case "settings_keepspawnloaded": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.BOOLEAN);
						return false;
					}

					w.setKeepSpawnInMemory(Boolean.parseBoolean(args[3]));
					success(sender);
					break;
				}
				case "settings_pvp": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.BOOLEAN);
						return false;
					}

					w.setPVP(Boolean.parseBoolean(args[3]));
					success(sender);
					break;
				}
				case "spawns_monsterlimit": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setMonsterSpawnLimit(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ticksper_ambient": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setTicksPerAmbientSpawns(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ticksper_animal": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setTicksPerAnimalSpawns(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ticksper_monster": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setTicksPerMonsterSpawns(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ticksper_ambient_water": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setTicksPerWaterAmbientSpawns(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ticksper_animal_water": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setTicksPerWaterAnimalSpawns(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_ambientlimit_water": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setWaterAmbientSpawnLimit(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "spawns_animallimit_water": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					w.setWaterAnimalSpawnLimit(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "game_thunderduration": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.INTEGER);
						return false;
					}

					if (!(w.isThundering())) {
						Main.sendPluginMessage(sender, ChatColor.RED + "The world is not thundering.");
						return false;
					}
					
					w.setThunderDuration(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "game_thundering": {
					if (args.length < 4) {
						Main.sendValidType(sender, ArgumentType.BOOLEAN);
						return false;
					}

					w.setThundering(Boolean.parseBoolean(args[3]));
					success(sender);
					break;
				}
				case "game_strikelightning": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X.");
						return false;
					}

					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Y.");
						return false;
					}

					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z.");
						return false;
					}

					Location loc = new Location(w, Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));

					w.strikeLightning(loc);
					success(sender);
					break;
				}
				case "game_strikelightning_effect": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X.");
						return false;
					}

					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Y.");
						return false;
					}

					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z.");
						return false;
					}

					Location loc = new Location(w, Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));

					w.strikeLightningEffect(loc);
					success(sender);
					break;
				}
				case "game_time": {
					if (args.length < 4) {
						Main.sendValidType(ArgumentType.INTEGER);
						return false;
					}

					w.setTime(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "game_fulltime": {
					if (args.length < 4) {
						Main.sendValidType(ArgumentType.INTEGER);
						return false;
					}

					w.setFullTime(Integer.parseInt(args[3]));
					success(sender);
					break;
				}
				case "game_spawnflags": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the boolean for \"allowMonsters\".");
						return false;
					}
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the boolean for \"allowAnimals\".");
						return false;
					}

					w.setSpawnFlags(Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]));
					success(sender);
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
		}

		
		return false;
	}
	
}
