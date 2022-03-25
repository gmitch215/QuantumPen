package me.gamercoder215.quantumpen.packets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.QuantumUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClientPacket implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public ClientPacket(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("clientpacket").setExecutor(this);
		plugin.getCommand("clientpacket").setTabCompleter(new CommandTabCompleter());
	}
	
	public static int getTotalExperience(int level) {
		try {
			if (level > 0 && level <= 15) {
				return (int) (Math.pow(level, 2) + 6 * level);
			} else if (level > 15 && level <= 31) {
				return (int) (2.5 * Math.pow(level, 2) - (40.5 * level) + 360);
			} else if (level > 31) {
				return (int) (4.5 * Math.pow(level, 2) - (162.5 * level) + 2220);
			} else return -1;
		} catch (ClassCastException e) {
			return 0;
		}
	}
	
	public static int matchInventorySlot(String name) {
		String realName = name.toLowerCase().replaceAll("minecraft:", "");

		if (realName.equalsIgnoreCase("armor.head")) return 5;
		else if (realName.equalsIgnoreCase("armor.chest")) return 6;
		else if (realName.equalsIgnoreCase("armor.legs")) return 7;
		else if (realName.equalsIgnoreCase("armor.feet")) return 8;
		else if (realName.equalsIgnoreCase("weapon.offhand")) return 45;
		else if (realName.equalsIgnoreCase("crafting.topleft")) return 1;
		else if (realName.equalsIgnoreCase("crafting.topright")) return 2;
		else if (realName.equalsIgnoreCase("crafting.bottomleft")) return 3;
		else if (realName.equalsIgnoreCase("crafting.bottomright")) return 4;
		else if (realName.equalsIgnoreCase("crafting.result")) return 0;
		else if (realName.equalsIgnoreCase("inventory.row1.1")) return 9;
		else if (realName.equalsIgnoreCase("inventory.row1.2")) return 10;
		else if (realName.equalsIgnoreCase("inventory.row1.3")) return 11;
		else if (realName.equalsIgnoreCase("inventory.row1.4")) return 12;
		else if (realName.equalsIgnoreCase("inventory.row1.5")) return 13;
		else if (realName.equalsIgnoreCase("inventory.row1.6")) return 14;
		else if (realName.equalsIgnoreCase("inventory.row1.7")) return 15;
		else if (realName.equalsIgnoreCase("inventory.row1.8")) return 16;
		else if (realName.equalsIgnoreCase("inventory.row1.9")) return 17;
		else if (realName.equalsIgnoreCase("inventory.row2.1")) return 18;
		else if (realName.equalsIgnoreCase("inventory.row2.2")) return 19;
		else if (realName.equalsIgnoreCase("inventory.row2.3")) return 20;
		else if (realName.equalsIgnoreCase("inventory.row2.4")) return 21;
		else if (realName.equalsIgnoreCase("inventory.row2.5")) return 22;
		else if (realName.equalsIgnoreCase("inventory.row2.6")) return 23;
		else if (realName.equalsIgnoreCase("inventory.row2.7")) return 24;
		else if (realName.equalsIgnoreCase("inventory.row2.8")) return 25;
		else if (realName.equalsIgnoreCase("inventory.row2.9")) return 26;
		else if (realName.equalsIgnoreCase("inventory.row3.1")) return 27;
		else if (realName.equalsIgnoreCase("inventory.row3.2")) return 28;
		else if (realName.equalsIgnoreCase("inventory.row3.3")) return 29;
		else if (realName.equalsIgnoreCase("inventory.row3.4")) return 30;
		else if (realName.equalsIgnoreCase("inventory.row3.5")) return 31;
		else if (realName.equalsIgnoreCase("inventory.row3.6")) return 32;
		else if (realName.equalsIgnoreCase("inventory.row3.7")) return 33;
		else if (realName.equalsIgnoreCase("inventory.row3.8")) return 34;
		else if (realName.equalsIgnoreCase("inventory.row3.9")) return 35;
		else if (realName.equalsIgnoreCase("hotbar.1")) return 36;
		else if (realName.equalsIgnoreCase("hotbar.2")) return 37;
		else if (realName.equalsIgnoreCase("hotbar.3")) return 38;
		else if (realName.equalsIgnoreCase("hotbar.4")) return 39;
		else if (realName.equalsIgnoreCase("hotbar.5")) return 40;
		else if (realName.equalsIgnoreCase("hotbar.6")) return 41;
		else if (realName.equalsIgnoreCase("hotbar.7")) return 42;
		else if (realName.equalsIgnoreCase("hotbar.8")) return 43;
		else if (realName.equalsIgnoreCase("hotbar.9")) return 44;
		else if (realName.equalsIgnoreCase("cursor")) return -1;

		else return 9;
	}
	
	public static EntityType<?> matchEntityType(String oldname) {
		String name = oldname.replaceAll("minecraft:", "");
		
		try {
			return (EntityType<?>) EntityType.class.getField(name.toUpperCase()).get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Motive matchPaintingID(String name) {
		String newName = name.replaceAll("minecraft:", "");
		
		try {
			return (Motive) Motive.class.getField(newName.toUpperCase()).get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return Motive.KEBAB;
		}
	}
	
	public static Difficulty matchDifficulty(String name) {
		try {
			return Difficulty.valueOf(name.toUpperCase());
		} catch (Exception e) {
			return Difficulty.NORMAL;
		}
	}
	
	static Random r = new Random();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 1) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid packet type.");
			return false;
		}
		
		if (args.length < 2) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player to send the packet to.");
			return false;
		}
		
		if (Bukkit.getPlayer(args[1]) == null) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This player does not exist.");
			return false;
		}
		
		Player p = Bukkit.getPlayer(args[1]);
		ServerPlayer cp = QuantumUtils.toNMSPlayer(p);
		
		if (args.length < 3) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a packet to send to the player.");
			return false;
		}
		
		try {
			switch (args[2].replaceAll("minecraft:", "")) {
				case "spawn_entity": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet, and markers are server-side only.");
						return false;
					}
					
					if (args[3].equalsIgnoreCase("minecraft:painting") || args[3].equalsIgnoreCase("minecraft:experience_orb")) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet.");
						return false;
					}
					
					if (matchEntityType(args[3]) == null) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs are used in the spawn_painting and spawn_experience_orb packet, and marks are server-side only.");
						return false;
					}
					
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;	
					}
					
					if (args.length < 6) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					
					ClientboundAddEntityPacket s = new ClientboundAddEntityPacket(r.nextInt(), UUID.randomUUID(), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[6].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), 0, 0, matchEntityType(args[3]), 0, Vec3.ZERO);
					cp.connection.send(s);

					break;
				}
				case "spawn_experience_orb": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide the world to spawn it into.");
						return false;
					}
					
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;	
					}
					
					if (args.length < 6) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					
					if (args.length < 8) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide the experience amount.");
						return false;
					}
					
					Level w = QuantumUtils.toNMSWorld(Bukkit.getWorld(args[3]));
					ClientboundAddExperienceOrbPacket s = new ClientboundAddExperienceOrbPacket(new ExperienceOrb(w, Integer.parseInt(args[3].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), Short.parseShort(args[6])));
					cp.connection.send(s);
					
					break;
				}
				case "spawn_painting": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a painting ID.");
						return false;
					}
					
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;
					}
					
					if (args.length < 6) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					

					Level w = QuantumUtils.toNMSWorld(p.getWorld());
					ClientboundAddPaintingPacket s = new ClientboundAddPaintingPacket(new Painting(w, new BlockPos(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6])), Direction.DOWN, matchPaintingID(args[3])));
					cp.connection.send(s);
					break;
				}
				case "settings_changedifficulty": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
						return false;
					}
					
					if (args.length < 5) {
						ClientboundChangeDifficultyPacket s = new ClientboundChangeDifficultyPacket(matchDifficulty(args[3]), false);
	
						cp.connection.send(s);
					} else {
						boolean lockDifficulty = Boolean.parseBoolean(args[4]);
						ClientboundChangeDifficultyPacket s = new ClientboundChangeDifficultyPacket(matchDifficulty(args[3]), lockDifficulty);
	
						cp.connection.send(s);
					}
	
					break;
				}
				case "gui_close": {
					ClientboundContainerClosePacket s = new ClientboundContainerClosePacket(cp.inventoryMenu.containerId);
					cp.connection.send(s);
					cp.closeContainer();
					break;
				}
				case "kick_player": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a reason to kick the player.");
						return false;
					}
	
					ArrayList<String> reasonArgs = new ArrayList<String>();
					for (int i = 3; i < args.length; i++) {
						reasonArgs.add(args[i]);
					}
					String reason = ChatColor.translateAlternateColorCodes('&', String.join(" ", reasonArgs));
					
					ClientboundLoginDisconnectPacket s = new ClientboundLoginDisconnectPacket(new TextComponent(reason));
					cp.connection.send(s);
					break;
				}
				case "camera_block_break_animation": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X.");
						return false;
					}
	
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Y.");
						return false;
					}
	
					if (args.length < 6) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z.");
						return false;
					}
	
					if (args.length < 7) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stage.");
						return false;
					}
	
					if (!(args[6].startsWith("stage_"))) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stage.");
						return false;
					}
	
					String removedStage = args[6].replaceAll("stage_", "");
	
					int parsedStage = Integer.parseInt(removedStage);

					ClientboundBlockDestructionPacket s = new ClientboundBlockDestructionPacket(p.getEntityId(), new BlockPos(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])), parsedStage);

					cp.connection.send(s);
	
					break;
				}
				case "playergui_set_item_inventory": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a slot to replace.");
						return false;
					}
	
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
						return false;
					}
	
					Material m = Material.matchMaterial(args[4].replaceAll("minecraft:", "").toUpperCase());
	
					if (m == null) m = Material.AIR;
	
					if (args.length < 6) {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						net.minecraft.world.item.ItemStack item = QuantumUtils.toNMSItem(bukkititem);
						
						ClientboundContainerSetSlotPacket s = new ClientboundContainerSetSlotPacket(cp.inventoryMenu.containerId, 0, matchInventorySlot(args[3]), item);
						cp.connection.send(s);
					} else {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						int maxAmount = bukkititem.getMaxStackSize();
						int amount = Integer.parseInt(args[5]);

						if (amount < 1 || amount > maxAmount) {
							QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a number between 1 and " + Integer.toString(maxAmount));
						}

						bukkititem.setAmount(amount);

						net.minecraft.world.item.ItemStack item = QuantumUtils.toNMSItem(bukkititem);
						ClientboundContainerSetSlotPacket s = new ClientboundContainerSetSlotPacket(cp.inventoryMenu.containerId, 0, matchInventorySlot(args[3]), item);
						cp.connection.send(s);
					}
					break;
				}
				case "playergui_set_item_cooldown": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid duration.");
						return false;
					}
					
					ClientboundCooldownPacket s = new ClientboundCooldownPacket(null, Integer.parseInt(args[3]));
					
					cp.connection.send(s);
					break;
				}
				case "camera_create_explosion": {
					List<BlockPos> emptyList = new ArrayList<>();
					
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X.");
						return false;
					}
					
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Y.");
						return false;
					}
					
					if (args.length < 6) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z.");
						return false;
					}
					
					if (args.length < 7) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid explosion power.");
						return false;
					}
					
					ClientboundExplodePacket s = new ClientboundExplodePacket(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Float.parseFloat(args[6]), emptyList, null);
					
					cp.connection.send(s);
					break;
				}
				case "state_weather_rain_start": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_weather_rain_end": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_gamemode_change": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid gamemode.");
						return false;
					}
					
					int gamemode = 0;
					
					if (args[3].equalsIgnoreCase("survival"))
						gamemode = 0;
					else if (args[3].equalsIgnoreCase("creative"))
						gamemode = 1;
					else if (args[3].equalsIgnoreCase("adventure"))
						gamemode = 2;
					else if (args[3].equalsIgnoreCase("spectator"))
						gamemode = 3;
					
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.CHANGE_GAME_MODE, gamemode);
					
					cp.connection.send(s);
					break;
				}
				case "state_win_noendscreen": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_win_endscreen": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 1);
					
					cp.connection.send(s);
					break;
				}
				case "state_arrowhit": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_elderguardianscreen": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_respawnscreen": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, 0);
					
					cp.connection.send(s);
					break;
				}
				case "state_respawnscreen_immediate": {
					ClientboundGameEventPacket s = new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, 1);
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_horse": {
					ClientboundHorseScreenOpenPacket s = new ClientboundHorseScreenOpenPacket(-1, 9, -1);
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_book": {
					ClientboundOpenBookPacket s = new ClientboundOpenBookPacket(InteractionHand.MAIN_HAND);
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_container": {
					MenuType<?> containerType = MenuType.GENERIC_9x3;
					if (args.length < 4)
						containerType = MenuType.GENERIC_9x3;
					else {
						int size = Integer.parseInt(args[3]);
						
						if (size < 9 || size > 54) {
							QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a size between 9 and 54.");
							return false;
						}
						
						if (size % 9 != 0) {
							QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid size divisible by 9.");
							return false;
						}
						
						switch (size) {
							case 9:
								containerType = MenuType.GENERIC_9x1;
								break;
							case 18:
								containerType = MenuType.GENERIC_9x2;
								break;
							case 27:
								containerType = MenuType.GENERIC_9x3;
								break;
							case 36:
								containerType = MenuType.GENERIC_9x4;
								break;
							case 45:
								containerType = MenuType.GENERIC_9x5;
								break;
							case 54:
								containerType = MenuType.GENERIC_9x6;
								break;
						}
					}
					
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, containerType, new TextComponent("Chest"));
				
					cp.connection.send(s);
				}
				case "gui_open_sign": {
					ClientboundOpenSignEditorPacket s = new ClientboundOpenSignEditorPacket(BlockPos.ZERO);
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_beacon": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.BEACON, new TextComponent("Beacon"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_anvil": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.ANVIL, new TextComponent("Anvil"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_enchantment": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.ENCHANTMENT, new TextComponent("Enchanting Table"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_crafting": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.CRAFTING, new TextComponent("Crafting Table"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_smoker": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.SMOKER, new TextComponent("Smoker"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_blastfurnace": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.BLAST_FURNACE, new TextComponent("Blast Furnace"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_furnace": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.FURNACE, new TextComponent("Furnace"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_grindstone": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.GRINDSTONE, new TextComponent("Grindstone"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_cartography": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.CARTOGRAPHY_TABLE, new TextComponent("Cartography Table"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_shulker": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.SHULKER_BOX, new TextComponent("Shulker Box"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_villager": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.MERCHANT, new TextComponent("Merchant"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_open_stonecutter": {
					ClientboundOpenScreenPacket s = new ClientboundOpenScreenPacket(0, MenuType.BEACON, new TextComponent("Stonecutter"));
					
					cp.connection.send(s);
					break;
				}
				case "gui_edit_slotselected": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid slot number.");
						return false;
					}
					
					int slot = Integer.parseInt(args[3]);
					
					if (slot < 1 || slot > 9) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid slot number between 1 and 9.");
						return false;
					}
					
					ClientboundSetCarriedItemPacket s = new ClientboundSetCarriedItemPacket(slot - 1);
					
					cp.connection.send(s);
					break;
				}
				case "combat_enter": {
					ClientboundPlayerCombatEnterPacket s = new ClientboundPlayerCombatEnterPacket();
					
					cp.connection.send(s);
					break;
				}
				case "combat_end": {
					int combatTime = 0;
					if (args.length < 4)
						combatTime = 0;
					else
						combatTime = Integer.parseInt(args[3]) * 20;
					
					
					ClientboundPlayerCombatEndPacket s = new ClientboundPlayerCombatEndPacket(combatTime, -1);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_leavebed": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 2);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_swing_QuantumPenhand": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 0);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_swing_offhand": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 3);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_takedmg": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 1);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_crit": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 4);
					
					cp.connection.send(s);
					break;
				}
				case "animation_play_crit_magical": {
					ClientboundAnimatePacket s = new ClientboundAnimatePacket(cp, 5);
					
					cp.connection.send(s);
					break;
				}
				case "connection_kick_player": {
					String message = "";
					if (args.length >= 4)
					for (int i = 3; i < args.length; i++) {
						message += " " + args[i];
					}
					
					ClientboundDisconnectPacket s = new ClientboundDisconnectPacket(new TextComponent(message));
					
					cp.connection.send(s);
					return false;
				}
				case "playergui_changexp": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid experience level.");
						return false;
					}
					
					if (args.length < 5) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide an experience progress between 1 and 100.");
						return false;
					}
					
					float progress = Float.parseFloat(args[3]);
					
					if (progress < 0 || progress > 100) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide an experience progress between 1 and 100.");
						return false;
					}
					int level = Integer.parseInt(args[3]);
					
					if (level < 0) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid experience level.");
						return false;
					}
					
					int expAmount = getTotalExperience(level);
					
					ClientboundSetExperiencePacket s = new ClientboundSetExperiencePacket(progress / 100, expAmount, level);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_creeper": {
					Creeper entity = new Creeper(EntityType.CREEPER, QuantumUtils.toNMSWorld(p.getWorld()));
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					QuantumUtils.toNMSWorld(p.getWorld()).addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_enderman": {
					EnderMan entity = new EnderMan(EntityType.ENDERMAN, QuantumUtils.toNMSWorld(p.getWorld()));
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					entity.setSilent(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					QuantumUtils.toNMSWorld(p.getWorld()).addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_spider": {
					Spider entity = new Spider(EntityType.SPIDER, QuantumUtils.toNMSWorld(p.getWorld()));
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					entity.setSilent(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					QuantumUtils.toNMSWorld(p.getWorld()).addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "playergui_updatehealth": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid health amount.");
						return false;
					}
					
					float health = Float.parseFloat(args[3]);
					
					if (health < 0 || health > 10000) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid health amount between 0 and 10,000.");
						return false;
					}
					
					ClientboundSetHealthPacket s = new ClientboundSetHealthPacket(health, p.getFoodLevel(), p.getSaturation());
					
					cp.connection.send(s);
					break;
				}
				case "playergui_updatefood": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid food amount.");
						return false;
					}
					
					int food = Integer.parseInt(args[3]);
					
					if (food < 0 || food > 20) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid food amount between 0 and 20.");
						return false;
					}
					
					ClientboundSetHealthPacket s = new ClientboundSetHealthPacket((float) p.getHealth(), food, p.getSaturation());
					
					cp.connection.send(s);
					break;
				}
				case "camera_updateviewdistance": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid render distance.");
						return false;
					}
					
					int distance = Integer.parseInt(args[3]);
					
					if (distance < 2 || distance > 32) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid render distance between 2 and 32.");
						return false;
					}
					
					ClientboundSetChunkCacheRadiusPacket s = new ClientboundSetChunkCacheRadiusPacket(distance);
					
					cp.connection.send(s);
					break;
				}
				default:
					QuantumPen.sendPluginMessage(sender, ChatColor.RED + "The packet you have provided is invalid.");
					return false;
			}
		} catch (IllegalArgumentException e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Error:\n" + e.getLocalizedMessage());
			return false;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Packet sent sucessfully!");
		return true;
	}

}