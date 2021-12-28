package me.gamercoder215.quantumpen.edit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;

public class Block implements CommandExecutor {
	protected Main plugin;

	public Block(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("block").setExecutor(this);
		plugin.getCommand("block").setTabCompleter(new CommandTabCompleter());
	}

	private boolean success(CommandSender sender, String returnType) {
		if (returnType == null) return false;
		sender.sendMessage(ChatColor.GOLD + "Returned: " + ChatColor.BLUE + returnType);
		return true;
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
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X coordinate.");
			return false;
		}

		if (args.length < 3) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Y coordinate.");
			return false;
		}

		if (args.length < 4) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z coordinate.");
			return false;
		}

		try {
			org.bukkit.block.Block b = w.getBlockAt(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

			if (args.length < 5) {
				Main.sendInvalidArgs(sender);
				return false;
			}

			switch (args[4].toLowerCase()) {
				case "get": {
					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid property.");
						return false;
					}

					switch (args[5].toLowerCase().replaceAll("minecraft:", "")) {
						case "game_biome": {
							success(sender, b.getBiome().name().toLowerCase());
							break;
						}
						case "game_redstonepower": {
							success(sender, Integer.toString(b.getBlockPower()));
							break;
						}
						case "game_breakspeed": {
							if (args.length < 7) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player to test break speed.");
							}

							if (Bukkit.getPlayer(args[6]) == null) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player to test break speed.");
							}

							Player p = Bukkit.getPlayer(args[6]);

							success(sender, Float.toString(b.getBreakSpeed(p)));
							break;
						}
						case "game_humidity": {
							success(sender, Double.toString(b.getHumidity()));
							break;
						}
						case "game_light_fromblocks": {
							success(sender, Byte.toString(b.getLightFromBlocks()));
							break;
						}
						case "game_light_fromsky": {
							success(sender, Byte.toString(b.getLightFromSky()));
							break;
						}
						case "game_lightlevel": {
							success(sender, Byte.toString(b.getLightLevel()));
							break;
						}
						case "game_temperature": {
							success(sender, Double.toString(b.getTemperature()));
							break;
						}
						case "game_powered": {
							success(sender, Boolean.toString(b.isBlockPowered()));
							break;
						}
						case "game_powered_indirectly": {
							success(sender, Boolean.toString(b.isBlockIndirectlyPowered()));
							break;
						}
						case "game_isempty": {
							success(sender, Boolean.toString(b.isEmpty()));
							break;
						}
						case "game_ispassable": {
							success(sender, Boolean.toString(b.isPassable()));
							break;
						}
						case "game_isliquid": {
							success(sender, Boolean.toString(b.isLiquid()));
							break;
						}
						case "game_ispreferredmaterial": {
							if (args.length < 7) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
								return false;
							}

							if (Material.matchMaterial(args[6]) == null) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
								return false;
							}

							ItemStack i = new ItemStack(Material.matchMaterial(args[6]));

							success(sender, Boolean.toString(b.isPreferredTool(i)));
							break;
						}
						default: {
							Main.sendPluginMessage(sender, ChatColor.RED + "This property does not exist.");
							return false;
						}
					}
				}
				case "modify": {
					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid property to modify.");
						return false;
					}

					switch (args[5].toLowerCase().replaceAll("minecraft:", "")) {
						case "game_breaknaturally": {
							if (b.breakNaturally()) {
								sender.sendMessage(ChatColor.GREEN + "Block break successful!");
								break;
							} else {
								sender.sendMessage(ChatColor.RED + "Block break unsuccessful.");
								break;
							}
						}
						case "game_breaknaturally_tool": {
							List<String> nbtStrArgs = new ArrayList<>();

							for (int i = 6; i < args.length; i++) {
								nbtStrArgs.add(args[i]);
							}

							String nbtStr = String.join(" ", nbtStrArgs);

							try {
								NBTTagCompound nbt = MojangsonParser.a(nbtStr);
								org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(net.minecraft.world.item.ItemStack.a(nbt));

								if (b.breakNaturally(item)) {
									sender.sendMessage(ChatColor.GREEN + "Block break successful!");
									break;
								} else {
									sender.sendMessage(ChatColor.RED + "Block break unsuccessful.");
									break;
								}
							} catch (CommandSyntaxException e) {
								Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing item NBT. Go here for an example -> https://pastebin.com/raw/gmbWqtJ2");
								return false;
							}
						}
						case "game_setbiome": {
							if (args.length < 7) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid biome.");
								return false;
							}

							if (Biome.valueOf(args[6].toLowerCase().replaceAll("minecraft:", "").toUpperCase()) == null) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid biome.");
								return false;
							}

							Biome biome = Biome.valueOf(args[6].toLowerCase().replaceAll("minecraft:", "").toUpperCase());

							b.setBiome(biome);
							sender.sendMessage(ChatColor.GREEN + "Successfully set biome to \"" + biome.name().toLowerCase() + "\".");
							break;
						}
						case "game_settype": {
							if (args.length < 7) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
								return false;
							}

							if (Material.matchMaterial(args[6].toLowerCase().replaceAll("minecraft:", "").toUpperCase()) == null) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
								return false;
							}

							Material m = Material.matchMaterial(args[6].toLowerCase().replaceAll("minecraft:", "").toUpperCase());

							if (!(m.isBlock())) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
								return false;
							}

							b.setType(m);
							sender.sendMessage(ChatColor.GREEN + "Block type placement successful!");
							break;
						}
						case "game_update": {
							if (args.length < 7) {
								if (b.getState().update(false, true)) {
									sender.sendMessage(ChatColor.GREEN + "Update successful!");
									break;
								} else {
									sender.sendMessage(ChatColor.RED + "Update unsuccessful.");
									break;
								}
							} else {
								if (b.getState().update(Boolean.parseBoolean(args[6]), true)) {
									sender.sendMessage(ChatColor.GREEN + "Update successful!");
									break;
								} else {
									sender.sendMessage(ChatColor.RED + "Update unsuccessful.");
									break;
								}
							}
						}
						default: {
							Main.sendPluginMessage(sender, ChatColor.RED + "This property does not exist.");
							return false;
						}
					}
				}
			}

		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
			return false;
		}
		return true;
	}
}