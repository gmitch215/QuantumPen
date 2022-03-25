package me.gamercoder215.quantumpen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;

public class PlayerData implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public PlayerData(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("playerdata").setExecutor(this);
		plugin.getCommand("playerdata").setTabCompleter(new CommandTabCompleter());
	}
	
	private void success(CommandSender sender, String data) {
		sender.sendMessage(ChatColor.GOLD + "Returned: " + ChatColor.GREEN + data);
	}
	
	private void success(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Successfully completed action!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
			return false;
		}
		
		if (Bukkit.getPlayer(args[0]) == null) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
			return false;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		
		if (args.length < 2) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid type.");
			return false;
		}
		
		if (args.length < 3) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid " + args[2] + ".");
			return false;
		}
		try {
			switch (args[1].toLowerCase()) {
				case "property": {					
					switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
						case "property_canseeplayer": {
							if (args.length < 4) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							if (Bukkit.getPlayer(args[3]) == null) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							success(sender, Boolean.toString(p.canSee(Bukkit.getPlayer(args[3]))));
							break;
						}
						case "property_canfly": {
							success(sender, Boolean.toString(p.getAllowFlight()));
							break;
						}
						case "property_client_viewdistance": {
							success(sender, Integer.toString(p.getClientViewDistance()));
							break;
						}
						case "property_flyspeed": {
							success(sender, Float.toString(p.getFlySpeed()));
							break;
						}
						case "property_healthscale": {
							success(sender, Double.toString(p.getHealthScale()));
							break;
						}
						case "property_ping": {
							success(sender, Integer.toString(p.getPing()));
							break;
						}
						case "property_name_display": {
							success(sender, p.getDisplayName());
							break;
						}
						case "property_name_custom": {
							success(sender, p.getCustomName());
							break;
						}
						case "property_name_playerlist": {
							success(sender, p.getPlayerListName());
							break;
						}
						case "property_timestamp": {
							success(sender, Long.toString(p.getPlayerTime()));
							break;
						}
						case "property_timestamp_offset": {
							success(sender, Long.toString(p.getPlayerTimeOffset()));
							break;
						}
						case "property_exp": {
							success(sender, Float.toString(p.getExp()));
							break;
						}
						case "property_exp_total": {
							success(sender, Float.toString(p.getTotalExperience()));
							break;
						}
						case "property_flying": {
							success(sender, Boolean.toString(p.isFlying()));
							break;
						}
						case "property_healthscaled": {
							success(sender, Boolean.toString(p.isHealthScaled()));
							break;
						}
						case "property_sleepingignored": {
							success(sender, Boolean.toString(p.isSleepingIgnored()));
							break;
						}
						case "property_timerelative": {
							success(sender, Boolean.toString(p.isPlayerTimeRelative()));
							break;
						}
						case "property_sprinting": {
							success(sender, Boolean.toString(p.isSprinting()));
							break;
						}
						case "property_attackcooldown": {
							success(sender, Float.toString(p.getAttackCooldown()));
							break;
						}
						case "property_exhaustion": {
							success(sender, Float.toString(p.getExhaustion()));
							break;
						}
						case "property_foodlevel": {
							success(sender, Integer.toString(p.getFoodLevel()));
							break;
						}
						case "property_saturation": {
							success(sender, Integer.toString(p.getSaturatedRegenRate()));
							break;
						}
						case "property_sleepticks": {
							success(sender, Integer.toString(p.getSleepTicks()));
							break;
						}
						case "property_usingshield": {
							success(sender, Boolean.toString(p.isBlocking()));
							break;
						}
						default: {
							QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This property does not exist.");
							return false;
						}
					}
					return true;
				}
				case "action": {
					switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
						case "server_loaddata": {
							p.loadData();
							success(sender);
							break;
						}
						case "server_kick": {
							if (args.length < 4) {
								p.kickPlayer("Kicked by " + sender.getName() + " through QuantumPen");
							} else {
								p.kickPlayer(ChatColor.translateAlternateColorCodes('&', args[3]));
							}
							success(sender);
							break;
						}
						case "server_resettime": {
							p.resetPlayerTime();
							success(sender);
							break;
						}
						case "camera_hideplayer": {
							if (args.length < 4) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							if (Bukkit.getPlayer(args[3]) == null) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							p.hidePlayer(plugin, Bukkit.getPlayer(args[3]));
							break;
						}
						case "server_resetweather": {
							p.resetPlayerWeather();
							success(sender);
							break;
						}
						case "playergui_resettitle": {
							p.resetTitle();
							success(sender);
							break;
						}
						case "server_savedata": {
							p.saveData();
							success(sender);
							break;
						}
						case "playergui_healthscale": {
							if (args.length < 4) {
								QuantumPen.sendValidType(sender, ArgumentType.DECIMAL);
								return false;
							}
							
							p.setHealthScale(Double.parseDouble(args[3]));
							success(sender);
							break;
						}
						case "playergui_healthscale_isscaled": {
							if (args.length < 4) {
								QuantumPen.sendValidType(sender, ArgumentType.BOOLEAN);
								return false;
							}
							
							p.setHealthScaled(Boolean.parseBoolean(args[3]));
							success(sender);
							break;
						}
						case "settings_walkspeed": {
							if (args.length < 4) {
								QuantumPen.sendValidType(sender, ArgumentType.DECIMAL);
								return false;
							}
							
							p.setWalkSpeed(Float.parseFloat(args[3]));
							success(sender);
							break;
						}
						case "camera_showplayer": {
							if (args.length < 4) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							if (Bukkit.getPlayer(args[3]) == null) {
								QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player.");
								return false;
							}
							
							p.showPlayer(plugin, Bukkit.getPlayer(args[3]));
							success(sender);
							break;
						}
						case "server_updatecommands": {
							p.updateCommands();
							success(sender);
							break;
						}
						case "server_updateinventory": {
							p.updateInventory();
							success(sender);
							break;
						}
						case "playergui_closeinventory":{ 
							p.closeInventory();
							success(sender);
							break;
						}
						case "playergui_open_enchanting": {
							if (args.length < 4) {
								p.openEnchanting(p.getLocation(), false);
							} else {
								p.openEnchanting(p.getLocation(), Boolean.parseBoolean(args[3]));
							}
							success(sender);
							break;
						}
						case "playergui_open_crafting": {
							if (args.length < 4) {
								p.openWorkbench(p.getLocation(), false);
							} else {
								p.openWorkbench(p.getLocation(), Boolean.parseBoolean(args[3]));
							}
							success(sender);
							break;
						}
						case "playergui_open_merchant": {
							if (args.length < 4) {
								p.openMerchant(Bukkit.createMerchant("Merchant"), false);
							} else {
								p.openMerchant(Bukkit.createMerchant("Merchant"), Boolean.parseBoolean(args[3]));
							}
							success(sender);
							break;
						}
						default: {
							QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This action does not exist.");
							return false;
						}
					}
					return true;
				}
				default: {
					QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This type does not exist.");
				}
			}
		} catch (IllegalArgumentException e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
			return false;
		}
		return true;
	}

}
