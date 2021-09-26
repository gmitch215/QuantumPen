package me.gamercoder215.novaeditor.packets;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import me.gamercoder215.novaeditor.commands.utils.ClientPacketCommandTabCompleter;
import me.gamercoder215.novaeditor.Main;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityPainting;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.Paintings;
import net.minecraft.world.level.World;

public class ClientPacket implements CommandExecutor {
	
	protected Main plugin;
	
	public ClientPacket(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("clientpacket").setExecutor(this);
		plugin.getCommand("clientpacket").setTabCompleter(new ClientPacketCommandTabCompleter());
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
	
	public static EntityTypes<?> matchEntityType(String oldname) {
		String name = oldname.replaceAll("minecraft:", "");
		
		if (name.equalsIgnoreCase("silverfish")) return EntityTypes.aA;
		else if (name.equalsIgnoreCase("slime")) return EntityTypes.aD;
		else if (name.equalsIgnoreCase("chest_minecart")) return EntityTypes.aa;
		else if (name.equalsIgnoreCase("skeleton")) return EntityTypes.aB;
		else if (name.equalsIgnoreCase("command_block_minecart")) return EntityTypes.ab;
		else if (name.equalsIgnoreCase("skeleton_horse")) return EntityTypes.aC;
		else if (name.equalsIgnoreCase("furnace_minecart")) return EntityTypes.ac;
		else if (name.equalsIgnoreCase("hopper_minecart")) return EntityTypes.ad;
		else if (name.equalsIgnoreCase("small_fireball")) return EntityTypes.aE;
		else if (name.equalsIgnoreCase("spawner_minecart")) return EntityTypes.ae;
		else if (name.equalsIgnoreCase("snow_golem")) return EntityTypes.aF;
		else if (name.equalsIgnoreCase("tnt_minecart")) return EntityTypes.af;
		else if (name.equalsIgnoreCase("mule")) return EntityTypes.ag;
		else if (name.equalsIgnoreCase("snowball")) return EntityTypes.aG;
		else if (name.equalsIgnoreCase("mooshroom")) return EntityTypes.ah;
		else if (name.equalsIgnoreCase("spectral_arrow")) return EntityTypes.aH;
		else if (name.equalsIgnoreCase("ocelot")) return EntityTypes.ai;
		else if (name.equalsIgnoreCase("spider")) return EntityTypes.aI;
		else if (name.equalsIgnoreCase("squid")) return EntityTypes.aJ;
		else if (name.equalsIgnoreCase("panda")) return EntityTypes.ak;
		else if (name.equalsIgnoreCase("stray")) return EntityTypes.aK;
		else if (name.equalsIgnoreCase("parrot")) return EntityTypes.al;
		else if (name.equalsIgnoreCase("strider")) return EntityTypes.aL;
		else if (name.equalsIgnoreCase("phantom")) return EntityTypes.am;
		else if (name.equalsIgnoreCase("egg")) return EntityTypes.aM;
		else if (name.equalsIgnoreCase("pig")) return EntityTypes.an;
		else if (name.equalsIgnoreCase("ender_pearl")) return EntityTypes.aN;
		else if (name.equalsIgnoreCase("piglin")) return EntityTypes.ao;
		else if (name.equalsIgnoreCase("experience_bottle")) return EntityTypes.aO;
		else if (name.equalsIgnoreCase("piglin_brute")) return EntityTypes.ap;
		else if (name.equalsIgnoreCase("potion")) return EntityTypes.aP;
		else if (name.equalsIgnoreCase("pillager")) return EntityTypes.aq;
		else if (name.equalsIgnoreCase("trident")) return EntityTypes.aQ;
		else if (name.equalsIgnoreCase("polar_bear")) return EntityTypes.ar;
		else if (name.equalsIgnoreCase("trader_llama")) return EntityTypes.aR;
		else if (name.equalsIgnoreCase("tnt")) return EntityTypes.as;
		else if (name.equalsIgnoreCase("tropical_fish")) return EntityTypes.aS;
		else if (name.equalsIgnoreCase("pufferfish")) return EntityTypes.at;
		else if (name.equalsIgnoreCase("turtle")) return EntityTypes.aT;
		else if (name.equalsIgnoreCase("rabbit")) return EntityTypes.au;
		else if (name.equalsIgnoreCase("vex")) return EntityTypes.aU;
		else if (name.equalsIgnoreCase("ravager")) return EntityTypes.av;
		else if (name.equalsIgnoreCase("villager")) return EntityTypes.aV;
		else if (name.equalsIgnoreCase("salmon")) return EntityTypes.aw;
		else if (name.equalsIgnoreCase("vindicator")) return EntityTypes.aW;
		else if (name.equalsIgnoreCase("sheep")) return EntityTypes.ax;
		else if (name.equalsIgnoreCase("wandering_trader")) return EntityTypes.aX;
		else if (name.equalsIgnoreCase("shulker")) return EntityTypes.ay;
		else if (name.equalsIgnoreCase("witch")) return EntityTypes.aY;
		else if (name.equalsIgnoreCase("shulker_bullet")) return EntityTypes.az;
		else if (name.equalsIgnoreCase("wither")) return EntityTypes.aZ;
		else if (name.equalsIgnoreCase("wither_skeleton")) return EntityTypes.ba;
		else if (name.equalsIgnoreCase("wither_skull")) return EntityTypes.bb;
		else if (name.equalsIgnoreCase("wolf")) return EntityTypes.bc;
		else if (name.equalsIgnoreCase("zoglin")) return EntityTypes.bd;
		else if (name.equalsIgnoreCase("zombie")) return EntityTypes.be;
		else if (name.equalsIgnoreCase("zombie_horse")) return EntityTypes.bf;
		else if (name.equalsIgnoreCase("zombie_villager")) return EntityTypes.bg;
		else if (name.equalsIgnoreCase("zombie_piglin")) return EntityTypes.bh;
		else if (name.equalsIgnoreCase("area_affect_cloud")) return EntityTypes.b;
		else if (name.equalsIgnoreCase("eye_of_ender")) return EntityTypes.B;
		else if (name.equalsIgnoreCase("armor_stand")) return EntityTypes.c;
		else if (name.equalsIgnoreCase("falling_block")) return EntityTypes.C;
		else if (name.equalsIgnoreCase("axolotl")) return EntityTypes.e;
		else if (name.equalsIgnoreCase("fox")) return EntityTypes.E;
		else if (name.equalsIgnoreCase("bat")) return EntityTypes.f;
		else if (name.equalsIgnoreCase("ghast")) return EntityTypes.F;
		else if (name.equalsIgnoreCase("bee")) return EntityTypes.g;
		else if (name.equalsIgnoreCase("giant")) return EntityTypes.G;
		else if (name.equalsIgnoreCase("blaze")) return EntityTypes.h;
		else if (name.equalsIgnoreCase("glow_item_frame")) return EntityTypes.H;
		else if (name.equalsIgnoreCase("boat")) return EntityTypes.i;
		else if (name.equalsIgnoreCase("glow_squid")) return EntityTypes.I;
		else if (name.equalsIgnoreCase("cat")) return EntityTypes.j;
		else if (name.equalsIgnoreCase("goat")) return EntityTypes.J;
		else if (name.equalsIgnoreCase("cave_spider")) return EntityTypes.k;
		else if (name.equalsIgnoreCase("guardian")) return EntityTypes.K;
		else if (name.equalsIgnoreCase("chicken")) return EntityTypes.l;
		else if (name.equalsIgnoreCase("hoglin")) return EntityTypes.L;
		else if (name.equalsIgnoreCase("cod")) return EntityTypes.m;
		else if (name.equalsIgnoreCase("horse")) return EntityTypes.M;
		else if (name.equalsIgnoreCase("cow")) return EntityTypes.n;
		else if (name.equalsIgnoreCase("husk")) return EntityTypes.N;
		else if (name.equalsIgnoreCase("creeper")) return EntityTypes.o;
		else if (name.equalsIgnoreCase("illusioner")) return EntityTypes.O;
		else if (name.equalsIgnoreCase("dolphin")) return EntityTypes.p;
		else if (name.equalsIgnoreCase("iron_golem")) return EntityTypes.P;
		else if (name.equalsIgnoreCase("donkey")) return EntityTypes.q;
		else if (name.equalsIgnoreCase("item")) return EntityTypes.Q;
		else if (name.equalsIgnoreCase("dragon_fireball")) return EntityTypes.r;
		else if (name.equalsIgnoreCase("item_frame")) return EntityTypes.R;
		else if (name.equalsIgnoreCase("drowned")) return EntityTypes.s;
		else if (name.equalsIgnoreCase("fireball")) return EntityTypes.S;
		else if (name.equalsIgnoreCase("elder_guardian")) return EntityTypes.t;
		else if (name.equalsIgnoreCase("end_crystal")) return EntityTypes.u;
		else if (name.equalsIgnoreCase("lightning")) return EntityTypes.U;
		else if (name.equalsIgnoreCase("ender_dragon")) return EntityTypes.v;
		else if (name.equalsIgnoreCase("llama")) return EntityTypes.V;
		else if (name.equalsIgnoreCase("enderman")) return EntityTypes.w;
		else if (name.equalsIgnoreCase("llama_spit")) return EntityTypes.W;
		else if (name.equalsIgnoreCase("endermite")) return EntityTypes.x;
		else if (name.equalsIgnoreCase("magma_cube")) return EntityTypes.X;
		else if (name.equalsIgnoreCase("evoker")) return EntityTypes.y;
		else if (name.equalsIgnoreCase("evoker_fangs")) return EntityTypes.z;
		else if (name.equalsIgnoreCase("minecart")) return EntityTypes.Z;
		else return null;
	}
	
	public static Paintings matchPaintingID(String name) {
		String newName = name.replaceAll("minecraft:", "");
		if (newName.equalsIgnoreCase("kebab")) return Paintings.a;
		else if (newName.equalsIgnoreCase("aztec")) return Paintings.b;
		else if (newName.equalsIgnoreCase("alban")) return Paintings.c;
		else if (newName.equalsIgnoreCase("aztec2")) return Paintings.d;
		else if (newName.equalsIgnoreCase("bomb")) return Paintings.e;
		else if (newName.equalsIgnoreCase("plant")) return Paintings.f;
		else if (newName.equalsIgnoreCase("wasteland")) return Paintings.g;
		else if (newName.equalsIgnoreCase("pool")) return Paintings.h;
		else if (newName.equalsIgnoreCase("courbet")) return Paintings.i;
		else if (newName.equalsIgnoreCase("sea")) return Paintings.j;
		else if (newName.equalsIgnoreCase("sunset")) return Paintings.k;
		else if (newName.equalsIgnoreCase("crebet")) return Paintings.l;
		else if (newName.equalsIgnoreCase("wanderer")) return Paintings.m;
		else if (newName.equalsIgnoreCase("graham")) return Paintings.n;
		else if (newName.equalsIgnoreCase("match")) return Paintings.o;
		else if (newName.equalsIgnoreCase("bust")) return Paintings.p;
		else if (newName.equalsIgnoreCase("stage")) return Paintings.q;
		else if (newName.equalsIgnoreCase("void")) return Paintings.r;
		else if (name.equalsIgnoreCase("skull_and_roses")) return Paintings.s;
		else if (newName.equalsIgnoreCase("wither")) return Paintings.t;
		else if (newName.equalsIgnoreCase("fighters")) return Paintings.u;
		else if (newName.equalsIgnoreCase("pointer")) return Paintings.v;
		else if (newName.equalsIgnoreCase("pigscene")) return Paintings.w;
		else if (newName.equalsIgnoreCase("burning_skull")) return Paintings.x;
		else if (newName.equalsIgnoreCase("skeleton")) return Paintings.y;
		else if (newName.equalsIgnoreCase("donkey_kong")) return Paintings.z;
		else return Paintings.a;
	}
	
	public static EnumDifficulty matchDifficulty(String name) {
		if (name.equalsIgnoreCase("easy")) return EnumDifficulty.b;
		else if (name.equalsIgnoreCase("normal")) return EnumDifficulty.c;
		else if (name.equalsIgnoreCase("peaceful")) return EnumDifficulty.a;
		else if (name.equalsIgnoreCase("hard")) return EnumDifficulty.d;
		else return EnumDifficulty.c;
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
		EntityPlayer cp = ((CraftPlayer) p).getHandle();
		
		if (args.length < 3) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a packet to send to the player.");
			return false;
		}
		
		switch (args[2].replaceAll("minecraft:", "")) {
			case "spawn_entity":
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
				
				try {
					PacketPlayOutSpawnEntity s = new PacketPlayOutSpawnEntity(r.nextInt(), UUID.randomUUID(), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[6].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), 0, 0, matchEntityType(args[3]), 0, null);
					cp.b.sendPacket(s);
					
				} catch (NumberFormatException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please fix your coordinates.");
					return false;
				}
				break;
			case "spawn_experience_orb":
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
				
				try {
					World w = ((CraftWorld) Bukkit.getWorld(args[3])).getHandle();
					PacketPlayOutSpawnEntityExperienceOrb s = new PacketPlayOutSpawnEntityExperienceOrb(new EntityExperienceOrb(w, Integer.parseInt(args[3].replaceAll("~", Integer.toString(p.getLocation().getBlockX()))), Integer.parseInt(args[4].replaceAll("~", Integer.toString(p.getLocation().getBlockY()))), Integer.parseInt(args[5].replaceAll("~", Integer.toString(p.getLocation().getBlockZ()))), Short.parseShort(args[6])));
					cp.b.sendPacket(s);
				} catch (NumberFormatException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please fix your coordinates and experience amount. The maximum amount of experience allowed is 32,767.");
					return false;
				}
				
				break;
			case "spawn_painting":
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
				
				try {
					World w = ((CraftWorld) p.getWorld()).getHandle();
					PacketPlayOutSpawnEntityPainting s = new PacketPlayOutSpawnEntityPainting(new EntityPainting(w, new BlockPosition(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6])), EnumDirection.a, matchPaintingID(args[3])));
					cp.b.sendPacket(s);
				}
				catch (NumberFormatException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please fix your coordinates.");
					return false;
				}
				break;
			case "settings_changedifficulty":
				if (args.length < 4) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid difficulty.");
					return false;
				}
				
				if (args.length < 5) {
					PacketPlayOutServerDifficulty s = new PacketPlayOutServerDifficulty(matchDifficulty(args[3]), false);

					cp.b.sendPacket(s);
				} else {
					boolean lockDifficulty = Boolean.parseBoolean(args[4]);
					PacketPlayOutServerDifficulty s = new PacketPlayOutServerDifficulty(matchDifficulty(args[3]), lockDifficulty);

					cp.b.sendPacket(s);
				}

				break;
			case "gui_close":
				PacketPlayOutCloseWindow s = new PacketPlayOutCloseWindow(cp.bV.j);
				cp.b.sendPacket(s);
				cp.o();
				break;
			case "kick_player":
				if (args.length < 4) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a reason to kick the player.");
					return false;
				}

				ArrayList<String> reasonArgs = new ArrayList<String>();
				for (int i = 3; i < args.length; i++) {
					reasonArgs.add(args[i]);
				}
				String reason = ChatColor.translateAlternateColorCodes('&', String.join(" ", reasonArgs));
				
				try {
					PacketLoginOutDisconnect s2 = new PacketLoginOutDisconnect(new ChatComponentText(reason));
					cp.b.sendPacket(s2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "camera_block_break_animation":
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

				try {
					int parsedStage = Integer.parseInt(removedStage);

					PacketPlayOutBlockBreakAnimation s2 = new PacketPlayOutBlockBreakAnimation(p.getEntityId(), new BlockPosition(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])), parsedStage);

					cp.b.sendPacket(s2);
				} catch (NumberFormatException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stage and coordinates.");
					return false;
				}

				break;
			case "playergui_set_item_inventory":
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
					
					PacketPlayOutSetSlot s3 = new PacketPlayOutSetSlot(cp.bV.j, 0, matchInventorySlot(args[3]), item);
					cp.b.sendPacket(s3);
				} else {
					try {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						int maxAmount = bukkititem.getMaxStackSize();
						int amount = Integer.parseInt(args[5]);

						if (amount < 1 || amount > maxAmount) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a number between 1 and " + Integer.toString(maxAmount));
						}

						bukkititem.setAmount(amount);

						net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(bukkititem);
						PacketPlayOutSetSlot s3 = new PacketPlayOutSetSlot(cp.bV.j, 0, matchInventorySlot(args[3]), item);
						cp.b.sendPacket(s3);
					} catch (NumberFormatException e) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid amount.");
						return false;
					}
				}
				break;
			default:
				Main.sendPluginMessage(sender, ChatColor.RED + "The packet you have provided is invalid.");
				return false;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Packet sent sucessfully!");
		return true;
	}

}
