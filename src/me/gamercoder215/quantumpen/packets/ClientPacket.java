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
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
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
	
	protected Main plugin;
	
	public ClientPacket(Main plugin) {
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
		
		if (name.equalsIgnoreCase("silverfish")) return EntityType.SILVERFISH;
		else if (name.equalsIgnoreCase("slime")) return EntityType.SLIME;
		else if (name.equalsIgnoreCase("chest_minecart")) return EntityType.CHEST_MINECART;
		else if (name.equalsIgnoreCase("skeleton")) return EntityType.SKELETON;
		else if (name.equalsIgnoreCase("command_block_minecart")) return EntityType.COMMAND_BLOCK_MINECART;
		else if (name.equalsIgnoreCase("skeleton_horse")) return EntityType.SKELETON_HORSE;
		else if (name.equalsIgnoreCase("furnace_minecart")) return EntityType.FURNACE_MINECART;
		else if (name.equalsIgnoreCase("hopper_minecart")) return EntityType.HOPPER_MINECART;
		else if (name.equalsIgnoreCase("small_fireball")) return EntityType.SMALL_FIREBALL;
		else if (name.equalsIgnoreCase("spawner_minecart")) return EntityType.SPAWNER_MINECART;
		else if (name.equalsIgnoreCase("snow_golem")) return EntityType.GLOW_ITEM_FRAME;
		else if (name.equalsIgnoreCase("tnt_minecart")) return EntityType.TNT_MINECART;
		else if (name.equalsIgnoreCase("mule")) return EntityType.MULE;
		else if (name.equalsIgnoreCase("snowball")) return EntityType.SNOWBALL;
		else if (name.equalsIgnoreCase("mooshroom")) return EntityType.MOOSHROOM;
		else if (name.equalsIgnoreCase("spectral_arrow")) return EntityType.SPECTRAL_ARROW;
		else if (name.equalsIgnoreCase("ocelot")) return EntityType.OCELOT;
		else if (name.equalsIgnoreCase("spider")) return EntityType.SPIDER;
		else if (name.equalsIgnoreCase("squid")) return EntityType.SQUID;
		else if (name.equalsIgnoreCase("panda")) return EntityType.PANDA;
		else if (name.equalsIgnoreCase("stray")) return EntityType.STRAY;
		else if (name.equalsIgnoreCase("parrot")) return EntityType.PARROT;
		else if (name.equalsIgnoreCase("strider")) return EntityType.STRIDER;
		else if (name.equalsIgnoreCase("phantom")) return EntityType.PHANTOM;
		else if (name.equalsIgnoreCase("egg")) return EntityType.EGG;
		else if (name.equalsIgnoreCase("pig")) return EntityType.PIG;
		else if (name.equalsIgnoreCase("ender_pearl")) return EntityType.ENDER_PEARL;
		else if (name.equalsIgnoreCase("piglin")) return EntityType.PIGLIN;
		else if (name.equalsIgnoreCase("experience_bottle")) return EntityType.EXPERIENCE_BOTTLE;
		else if (name.equalsIgnoreCase("piglin_brute")) return EntityType.PIGLIN_BRUTE;
		else if (name.equalsIgnoreCase("potion")) return EntityType.POTION;
		else if (name.equalsIgnoreCase("pillager")) return EntityType.PILLAGER;
		else if (name.equalsIgnoreCase("trident")) return EntityType.TRIDENT;
		else if (name.equalsIgnoreCase("polar_bear")) return EntityType.POLAR_BEAR;
		else if (name.equalsIgnoreCase("trader_llama")) return EntityType.TRADER_LLAMA;
		else if (name.equalsIgnoreCase("tnt")) return EntityType.TNT;
		else if (name.equalsIgnoreCase("tropical_fish")) return EntityType.TROPICAL_FISH;
		else if (name.equalsIgnoreCase("pufferfish")) return EntityType.PUFFERFISH;
		else if (name.equalsIgnoreCase("turtle")) return EntityType.TURTLE;
		else if (name.equalsIgnoreCase("rabbit")) return EntityType.RABBIT;
		else if (name.equalsIgnoreCase("vex")) return EntityType.VEX;
		else if (name.equalsIgnoreCase("ravager")) return EntityType.RAVAGER;
		else if (name.equalsIgnoreCase("villager")) return EntityType.VILLAGER;
		else if (name.equalsIgnoreCase("salmon")) return EntityType.SALMON;
		else if (name.equalsIgnoreCase("vindicator")) return EntityType.VINDICATOR;
		else if (name.equalsIgnoreCase("sheep")) return EntityType.SHEEP;
		else if (name.equalsIgnoreCase("wandering_trader")) return EntityType.WANDERING_TRADER;
		else if (name.equalsIgnoreCase("shulker")) return EntityType.SHULKER;
		else if (name.equalsIgnoreCase("witch")) return EntityType.WITCH;
		else if (name.equalsIgnoreCase("shulker_bullet")) return EntityType.SHULKER_BULLET;
		else if (name.equalsIgnoreCase("wither")) return EntityType.WITHER;
		else if (name.equalsIgnoreCase("wither_skeleton")) return EntityType.WITHER_SKELETON;
		else if (name.equalsIgnoreCase("wither_skull")) return EntityType.WITHER_SKULL;
		else if (name.equalsIgnoreCase("wolf")) return EntityType.WOLF;
		else if (name.equalsIgnoreCase("zoglin")) return EntityType.ZOGLIN;
		else if (name.equalsIgnoreCase("zombie")) return EntityType.ZOMBIE;
		else if (name.equalsIgnoreCase("zombie_horse")) return EntityType.ZOMBIE_HORSE;
		else if (name.equalsIgnoreCase("zombie_villager")) return EntityType.ZOMBIE_VILLAGER;
		else if (name.equalsIgnoreCase("zombie_piglin")) return EntityType.ZOMBIFIED_PIGLIN;
		else if (name.equalsIgnoreCase("area_affect_cloud")) return EntityType.AREA_EFFECT_CLOUD;
		else if (name.equalsIgnoreCase("eye_of_ender")) return EntityType.EYE_OF_ENDER;
		else if (name.equalsIgnoreCase("armor_stand")) return EntityType.ARMOR_STAND;
		else if (name.equalsIgnoreCase("falling_block")) return EntityType.FALLING_BLOCK;
		else if (name.equalsIgnoreCase("axolotl")) return EntityType.AXOLOTL;
		else if (name.equalsIgnoreCase("fox")) return EntityType.FOX;
		else if (name.equalsIgnoreCase("bat")) return EntityType.BAT;
		else if (name.equalsIgnoreCase("ghast")) return EntityType.GHAST;
		else if (name.equalsIgnoreCase("bee")) return EntityType.BEE;
		else if (name.equalsIgnoreCase("giant")) return EntityType.GIANT;
		else if (name.equalsIgnoreCase("blaze")) return EntityType.BLAZE;
		else if (name.equalsIgnoreCase("glow_item_frame")) return EntityType.GLOW_ITEM_FRAME;
		else if (name.equalsIgnoreCase("boat")) return EntityType.BOAT;
		else if (name.equalsIgnoreCase("glow_squid")) return EntityType.GLOW_SQUID;
		else if (name.equalsIgnoreCase("cat")) return EntityType.CAT;
		else if (name.equalsIgnoreCase("goat")) return EntityType.GOAT;
		else if (name.equalsIgnoreCase("cave_spider")) return EntityType.CAVE_SPIDER;
		else if (name.equalsIgnoreCase("guardian")) return EntityType.GUARDIAN;
		else if (name.equalsIgnoreCase("chicken")) return EntityType.CHICKEN;
		else if (name.equalsIgnoreCase("hoglin")) return EntityType.HOGLIN;
		else if (name.equalsIgnoreCase("cod")) return EntityType.COD;
		else if (name.equalsIgnoreCase("horse")) return EntityType.HORSE;
		else if (name.equalsIgnoreCase("cow")) return EntityType.COW;
		else if (name.equalsIgnoreCase("husk")) return EntityType.HUSK;
		else if (name.equalsIgnoreCase("creeper")) return EntityType.CREEPER;
		else if (name.equalsIgnoreCase("illusioner")) return EntityType.ILLUSIONER;
		else if (name.equalsIgnoreCase("dolphin")) return EntityType.DOLPHIN;
		else if (name.equalsIgnoreCase("iron_golem")) return EntityType.IRON_GOLEM;
		else if (name.equalsIgnoreCase("donkey")) return EntityType.DONKEY;
		else if (name.equalsIgnoreCase("item")) return EntityType.ITEM;
		else if (name.equalsIgnoreCase("dragon_fireball")) return EntityType.DRAGON_FIREBALL;
		else if (name.equalsIgnoreCase("item_frame")) return EntityType.ITEM_FRAME;
		else if (name.equalsIgnoreCase("drowned")) return EntityType.DROWNED;
		else if (name.equalsIgnoreCase("fireball")) return EntityType.FIREBALL;
		else if (name.equalsIgnoreCase("elder_guardian")) return EntityType.ELDER_GUARDIAN;
		else if (name.equalsIgnoreCase("end_crystal")) return EntityType.END_CRYSTAL;
		else if (name.equalsIgnoreCase("lightning")) return EntityType.LIGHTNING_BOLT;
		else if (name.equalsIgnoreCase("ender_dragon")) return EntityType.ENDER_DRAGON;
		else if (name.equalsIgnoreCase("llama")) return EntityType.LLAMA;
		else if (name.equalsIgnoreCase("enderman")) return EntityType.ENDERMAN;
		else if (name.equalsIgnoreCase("llama_spit")) return EntityType.LLAMA_SPIT;
		else if (name.equalsIgnoreCase("endermite")) return EntityType.ENDERMITE;
		else if (name.equalsIgnoreCase("magma_cube")) return EntityType.MAGMA_CUBE;
		else if (name.equalsIgnoreCase("evoker")) return EntityType.EVOKER;
		else if (name.equalsIgnoreCase("evoker_fangs")) return EntityType.EVOKER_FANGS;
		else if (name.equalsIgnoreCase("minecart")) return EntityType.MINECART;
		else return null;
	}
	
	public static Motive matchPaintingID(String name) {
		String newName = name.replaceAll("minecraft:", "");
		if (newName.equalsIgnoreCase("kebab")) return Motive.KEBAB;
		else if (newName.equalsIgnoreCase("aztec")) return Motive.AZTEC;
		else if (newName.equalsIgnoreCase("alban")) return Motive.ALBAN;
		else if (newName.equalsIgnoreCase("aztec2")) return Motive.AZTEC2;
		else if (newName.equalsIgnoreCase("bomb")) return Motive.BOMB;
		else if (newName.equalsIgnoreCase("plant")) return Motive.PLANT;
		else if (newName.equalsIgnoreCase("wasteland")) return Motive.WASTELAND;
		else if (newName.equalsIgnoreCase("pool")) return Motive.POOL;
		else if (newName.equalsIgnoreCase("courbet")) return Motive.COURBET;
		else if (newName.equalsIgnoreCase("sea")) return Motive.SEA;
		else if (newName.equalsIgnoreCase("sunset")) return Motive.SUNSET;
		else if (newName.equalsIgnoreCase("crebet")) return Motive.CREEBET;
		else if (newName.equalsIgnoreCase("wanderer")) return Motive.WANDERER;
		else if (newName.equalsIgnoreCase("graham")) return Motive.GRAHAM;
		else if (newName.equalsIgnoreCase("match")) return Motive.MATCH;
		else if (newName.equalsIgnoreCase("bust")) return Motive.BUST;
		else if (newName.equalsIgnoreCase("stage")) return Motive.STAGE;
		else if (newName.equalsIgnoreCase("void")) return Motive.VOID;
		else if (newName.equalsIgnoreCase("skull_and_roses")) return Motive.SKULL_AND_ROSES;
		else if (newName.equalsIgnoreCase("wither")) return Motive.WITHER;
		else if (newName.equalsIgnoreCase("fighters")) return Motive.FIGHTERS;
		else if (newName.equalsIgnoreCase("pointer")) return Motive.POINTER;
		else if (newName.equalsIgnoreCase("pigscene")) return Motive.PIGSCENE;
		else if (newName.equalsIgnoreCase("burning_skull")) return Motive.BURNING_SKULL;
		else if (newName.equalsIgnoreCase("skeleton")) return Motive.SKELETON;
		else if (newName.equalsIgnoreCase("donkey_kong")) return Motive.DONKEY_KONG;
		else return Motive.KEBAB;
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
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid packet type.");
			return false;
		}
		
		if (args.length < 2) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid player to send the packet to.");
			return false;
		}
		
		if (Bukkit.getPlayer(args[1]) == null) {
			Main.sendPluginMessage(sender, ChatColor.RED + "This player does not exist.");
			return false;
		}
		
		Player p = Bukkit.getPlayer(args[1]);
		ServerPlayer cp = ((CraftPlayer) p).getHandle();
		
		if (args.length < 3) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a packet to send to the player.");
			return false;
		}
		
		try {
			switch (args[2].replaceAll("minecraft:", "")) {
				case "spawn_entity": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet, and markers are server-side only.");
						return false;
					}
					
					if (args[3].equalsIgnoreCase("minecraft:painting") || args[3].equalsIgnoreCase("minecraft:experience_orb")) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet.");
						return false;
					}
					
					if (matchEntityType(args[3]) == null) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs are used in the spawn_painting and spawn_experience_orb packet, and marks are server-side only.");
						return false;
					}
					
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;	
					}
					
					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					
					ClientboundAddEntityPacket s = new ClientboundAddEntityPacket(r.nextInt(), UUID.randomUUID(), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[6].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), 0, 0, matchEntityType(args[3]), 0, Vec3.ZERO);
					cp.connection.send(s);

					break;
				}
				case "spawn_experience_orb": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the world to spawn it into.");
						return false;
					}
					
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;	
					}
					
					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					
					if (args.length < 8) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the experience amount.");
						return false;
					}
					
					Level w = ((CraftWorld) Bukkit.getWorld(args[3])).getHandle();
					ClientboundAddExperienceOrbPacket s = new ClientboundAddExperienceOrbPacket(new ExperienceOrb(w, Integer.parseInt(args[3].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), Short.parseShort(args[6])));
					cp.connection.send(s);
					
					break;
				}
				case "spawn_painting": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a painting ID.");
						return false;
					}
					
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
						return false;
					}
					
					if (args.length < 6) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
						return false;	
					}
					
					if (args.length < 7) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
						return false;	
					}
					

					Level w = ((CraftWorld) p.getWorld()).getHandle();
					ClientboundAddPaintingPacket s = new ClientboundAddPaintingPacket(new Painting(w, new BlockPos(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6])), Direction.DOWN, matchPaintingID(args[3])));
					cp.connection.send(s);
					break;
				}
				case "settings_changedifficulty": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
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
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a reason to kick the player.");
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
	
					if (args.length < 7) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stage.");
						return false;
					}
	
					if (!(args[6].startsWith("stage_"))) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stage.");
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
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a slot to replace.");
						return false;
					}
	
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid material.");
						return false;
					}
	
					Material m = Material.matchMaterial(args[4].replaceAll("minecraft:", "").toUpperCase());
	
					if (m == null) m = Material.AIR;
	
					if (args.length < 6) {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(bukkititem);
						
						ClientboundContainerSetSlotPacket s = new ClientboundContainerSetSlotPacket(cp.inventoryMenu.containerId, 0, matchInventorySlot(args[3]), item);
						cp.connection.send(s);
					} else {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						int maxAmount = bukkititem.getMaxStackSize();
						int amount = Integer.parseInt(args[5]);

						if (amount < 1 || amount > maxAmount) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a number between 1 and " + Integer.toString(maxAmount));
						}

						bukkititem.setAmount(amount);

						net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(bukkititem);
						ClientboundContainerSetSlotPacket s = new ClientboundContainerSetSlotPacket(cp.inventoryMenu.containerId, 0, matchInventorySlot(args[3]), item);
						cp.connection.send(s);
					}
					break;
				}
				case "playergui_set_item_cooldown": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid duration.");
						return false;
					}
					
					ClientboundCooldownPacket s = new ClientboundCooldownPacket(null, Integer.parseInt(args[3]));
					
					cp.connection.send(s);
					break;
				}
				case "camera_create_explosion": {
					List<BlockPos> emptyList = new ArrayList<>();
					
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
					
					if (args.length < 7) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid explosion power.");
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
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid gamemode.");
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
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a size between 9 and 54.");
							return false;
						}
						
						if (size % 9 != 0) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid size divisible by 9.");
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
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid slot number.");
						return false;
					}
					
					int slot = Integer.parseInt(args[3]);
					
					if (slot < 1 || slot > 9) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid slot number between 1 and 9.");
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
				case "animation_play_swing_mainhand": {
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
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid experience level.");
						return false;
					}
					
					if (args.length < 5) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an experience progress between 1 and 100.");
						return false;
					}
					
					float progress = Float.parseFloat(args[3]);
					
					if (progress < 0 || progress > 100) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an experience progress between 1 and 100.");
						return false;
					}
					int level = Integer.parseInt(args[3]);
					
					if (level < 0) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid experience level.");
						return false;
					}
					
					int expAmount = getTotalExperience(level);
					
					ClientboundSetExperiencePacket s = new ClientboundSetExperiencePacket(progress / 100, expAmount, level);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_creeper": {
					Creeper entity = new Creeper(EntityType.CREEPER, ((CraftWorld) p.getWorld()).getHandle());
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					((CraftWorld) p.getWorld()).getHandle().addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_enderman": {
					EnderMan entity = new EnderMan(EntityType.ENDERMAN, ((CraftWorld) p.getWorld()).getHandle());
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					entity.setSilent(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					((CraftWorld) p.getWorld()).getHandle().addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "camera_shader_spider": {
					Spider entity = new Spider(EntityType.SPIDER, ((CraftWorld) p.getWorld()).getHandle());
					
					entity.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
					entity.setNoAi(true);
					entity.setNoGravity(true);
					entity.setInvulnerable(true);
					entity.setSilent(true);
					((LivingEntity) entity.getBukkitEntity()).setInvisible(true);
					
					((CraftWorld) p.getWorld()).getHandle().addFreshEntity(entity);
					
					ClientboundSetCameraPacket s = new ClientboundSetCameraPacket(entity);
					
					cp.connection.send(s);
					break;
				}
				case "playergui_updatehealth": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid health amount.");
						return false;
					}
					
					float health = Float.parseFloat(args[3]);
					
					if (health < 0 || health > 10000) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid health amount between 0 and 10,000.");
						return false;
					}
					
					ClientboundSetHealthPacket s = new ClientboundSetHealthPacket(health, p.getFoodLevel(), p.getSaturation());
					
					cp.connection.send(s);
					break;
				}
				case "playergui_updatefood": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid food amount.");
						return false;
					}
					
					int food = Integer.parseInt(args[3]);
					
					if (food < 0 || food > 20) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid food amount between 0 and 20.");
						return false;
					}
					
					ClientboundSetHealthPacket s = new ClientboundSetHealthPacket((float) p.getHealth(), food, p.getSaturation());
					
					cp.connection.send(s);
					break;
				}
				case "camera_updateviewdistance": {
					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid render distance.");
						return false;
					}
					
					int distance = Integer.parseInt(args[3]);
					
					if (distance < 2 || distance > 32) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid render distance between 2 and 32.");
						return false;
					}
					
					ClientboundSetChunkCacheRadiusPacket s = new ClientboundSetChunkCacheRadiusPacket(distance);
					
					cp.connection.send(s);
					break;
				}
				default:
					Main.sendPluginMessage(sender, ChatColor.RED + "The packet you have provided is invalid.");
					return false;
			}
		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Error:\n" + e.getLocalizedMessage());
			return false;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Packet sent sucessfully!");
		return true;
	}

}
