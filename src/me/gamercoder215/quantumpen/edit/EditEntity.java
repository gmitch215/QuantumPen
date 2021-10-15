package me.gamercoder215.quantumpen.edit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.quantumpen.Main;

public class EditEntity implements CommandExecutor {
	
	protected Main plugin;
	
	public EditEntity(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("editentity").setExecutor(this);
		plugin.getCommand("editentity").setTabCompleter(new CommandTabCompleter());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an entities's UUID. If you do not know what that is, do some research.");
			return false;
		}

		try {		
			UUID entityUUID = UUID.fromString(args[0]);

			if (Bukkit.getEntity(entityUUID) == null) {
				Main.sendPluginMessage(sender, "Please provide a living entity's UUID.");
				return false;
			}

			Entity entity = Bukkit.getEntity(entityUUID);

			if (!(entity instanceof LivingEntity)) {
				Main.sendPluginMessage(sender, "Please provide a living entity's UUID.");
				return false;
			}

			LivingEntity len = (LivingEntity) entity;

			if (args.length < 2) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid type of editing.");
				return false;
			}

			switch (args[1].toLowerCase()) {
				case "edit":
					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid value to edit.");
						return false;
					}
					try {
						switch (args[2].toLoweCase().replaceAll("minecraft:", "")) {
							case "set_velocity": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an X velocity.");
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a Y velocity.");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a Z velocity.");
									return false;
								}

								len.setVelocity(new Vector(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])));
								break;
							}
							case "set_ai": {
								if (args.length < 4) {
									Main.sendValidType(sender, ArgumentType.BOOLEAN);
									return false;
								}

								len.setAI(Boolean.parseBoolean(args[3]));
								break;
							}
							case "set_collidable": {
								if (args.length < 4) {
									Main.sendValidType(sender, ArgumentType.BOOLEAN);
									return false;
								}

								len.setCollidable(Boolean.parseBoolean(args[3]));
								break;
							}
							case "set_lastdamage": {
								if (args.length < 4) {
									Main.sendValidType(ArgumentType.DECIMAL);
									return false;
								}

								len.setLastDamage(Double.parseDouble(args[3]));
								break;
							}
							case "set_arrowsinbody": {
								if (args.length < 4) {
									Main.sendValidType(sender, ArgumentType.INTEGER);
									return false;
								}

								len.setArrowsInBody(Integer.parseInt(args[3]));
								break;
							}
							case "set_max_air": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid maximum integer, in ticks (seconds x 20)");
									return false;
								}

								len.setMaximumAir(Integer.parseInt(args[3]));
								break;
							}
							case "set_max_nodamageticks": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid maximum integer, in ticks (seconds x 20)");
									return false;
								}

								len.setMaximumNoDamageTicks(Integer.parseInt(args[3]));
								break;
							}
							case "set_nodamageticks": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid maximum integer, in ticks (seconds x 20)");
									return false;
								}

								len.setNoDamageTicks(Integer.parseInt(args[3]));
								break;
							}
							case "set_arrowcooldown": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid maximum integer, in ticks (seconds x 20)");
									return false;
								}

								len.setArrowCooldown(Integer.parseInt(args[3]));
								break;
							}
							default: {
								Main.sendPluginMessage(sender, ChatColor.RED + "This action does not exist.");
								return false;
							}
						}
					} catch (IllegalArgumentException e) {
						Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
						return false;
					} catch (Exception e) {
						Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
						return false;
					}

					break;
				case "do":
					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid action.");
						return false;
					}
					switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
						case "swing_offhand": {
							len.swingOffHand();
							break;
						}
						case "swing_mainhand": {
							len.swingMainHand();
							break;
						}
						case "game_ejectpassenger": {
							if (len.eject()) {
								sender.sendMessage(ChatColor.GREEN + "Eject Successful!");
								return true;
							} else {
								sender.sendMessage(ChatColor.RED + "Eject Unsuccessful.");
								return true;
							}
						}
						case "game_remove": {
							len.remove();
							break;
						}
						case "game_leavevehicle": {
							if (len.leaveVehicle()) {
								sender.sendMessage(ChatColor.GREEN + "Successfully left vehicle!");
								return true;
							} else {
								sender.sendMessage(ChatColor.RED + "Did not successfully leave vehicle.");
							}
							break;
						}
						default: {
							Main.sendPluginMessage(sender, ChatColor.RED + "This is not a valid entity action.");
							return false;
						}
					}
					break;
				default:
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid type of editing.");
					return false;
			}
			if (args[1].equalsIgnoreCase("edit")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully edited entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			} else if (args[1].equalsIgnoreCase("fetch")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully fetched data of entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			} else if (args[1].equalsIgnoreCase("do")) {
				Main.sendPluginMessage(sender, ChatColor.GREEN + "Successfully did action of entity " + (len.getCustomName() == null ? len.getName() : len.getCustomName()));
				return false;
			}

		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
			return false;
		}

		return true;
	}
	
}
