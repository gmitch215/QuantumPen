package me.gamercoder215.superpackets.commands;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.gamercoder215.superpackets.Main;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.level.EntityPlayer;
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
		plugin.getCommand("clientpacket").setTabCompleter(new CommandTabCompleter());
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
	
	public static EntityTypes<?> matchEntityType(String name) {
		if (name.equalsIgnoreCase("minecraft:silverfish")) return EntityTypes.aA;
		else if (name.equalsIgnoreCase("minecraft:slime")) return EntityTypes.aD;
		else if (name.equalsIgnoreCase("minecraft:chest_minecart")) return EntityTypes.aa;
		else if (name.equalsIgnoreCase("minecraft:skeleton")) return EntityTypes.aB;
		else if (name.equalsIgnoreCase("minecraft:command_block_minecart")) return EntityTypes.ab;
		else if (name.equalsIgnoreCase("minecraft:skeleton_horse")) return EntityTypes.aC;
		else if (name.equalsIgnoreCase("minecraft:furnace_minecart")) return EntityTypes.ac;
		else if (name.equalsIgnoreCase("minecraft:hopper_minecart")) return EntityTypes.ad;
		else if (name.equalsIgnoreCase("minecraft:small_fireball")) return EntityTypes.aE;
		else if (name.equalsIgnoreCase("minecraft:spawner_minecart")) return EntityTypes.ae;
		else if (name.equalsIgnoreCase("minecraft:snow_golem")) return EntityTypes.aF;
		else if (name.equalsIgnoreCase("minecraft:tnt_minecart")) return EntityTypes.af;
		else if (name.equalsIgnoreCase("minecraft:mule")) return EntityTypes.ag;
		else if (name.equalsIgnoreCase("minecraft:snowball")) return EntityTypes.aG;
		else if (name.equalsIgnoreCase("minecraft:mooshroom")) return EntityTypes.ah;
		else if (name.equalsIgnoreCase("minecraft:spectral_arrow")) return EntityTypes.aH;
		else if (name.equalsIgnoreCase("minecraft:ocelot")) return EntityTypes.ai;
		else if (name.equalsIgnoreCase("minecraft:spider")) return EntityTypes.aI;
		else if (name.equalsIgnoreCase("minecraft:squid")) return EntityTypes.aJ;
		else if (name.equalsIgnoreCase("minecraft:panda")) return EntityTypes.ak;
		else if (name.equalsIgnoreCase("minecraft:stray")) return EntityTypes.aK;
		else if (name.equalsIgnoreCase("minecraft:parrot")) return EntityTypes.al;
		else if (name.equalsIgnoreCase("minecraft:strider")) return EntityTypes.aL;
		else if (name.equalsIgnoreCase("minecraft:phantom")) return EntityTypes.am;
		else if (name.equalsIgnoreCase("minecraft:egg")) return EntityTypes.aM;
		else if (name.equalsIgnoreCase("minecraft:pig")) return EntityTypes.an;
		else if (name.equalsIgnoreCase("minecraft:ender_pearl")) return EntityTypes.aN;
		else if (name.equalsIgnoreCase("minecraft:piglin")) return EntityTypes.ao;
		else if (name.equalsIgnoreCase("minecraft:experience_bottle")) return EntityTypes.aO;
		else if (name.equalsIgnoreCase("minecraft:piglin_brute")) return EntityTypes.ap;
		else if (name.equalsIgnoreCase("minecraft:potion")) return EntityTypes.aP;
		else if (name.equalsIgnoreCase("minecraft:pillager")) return EntityTypes.aq;
		else if (name.equalsIgnoreCase("minecraft:trident")) return EntityTypes.aQ;
		else if (name.equalsIgnoreCase("minecraft:polar_bear")) return EntityTypes.ar;
		else if (name.equalsIgnoreCase("minecraft:trader_llama")) return EntityTypes.aR;
		else if (name.equalsIgnoreCase("minecraft:tnt")) return EntityTypes.as;
		else if (name.equalsIgnoreCase("minecraft:tropical_fish")) return EntityTypes.aS;
		else if (name.equalsIgnoreCase("minecraft:pufferfish")) return EntityTypes.at;
		else if (name.equalsIgnoreCase("minecraft:turtle")) return EntityTypes.aT;
		else if (name.equalsIgnoreCase("minecraft:rabbit")) return EntityTypes.au;
		else if (name.equalsIgnoreCase("minecraft:vex")) return EntityTypes.aU;
		else if (name.equalsIgnoreCase("minecraft:ravager")) return EntityTypes.av;
		else if (name.equalsIgnoreCase("minecraft:villager")) return EntityTypes.aV;
		else if (name.equalsIgnoreCase("minecraft:salmon")) return EntityTypes.aw;
		else if (name.equalsIgnoreCase("minecraft:vindicator")) return EntityTypes.aW;
		else if (name.equalsIgnoreCase("minecraft:sheep")) return EntityTypes.ax;
		else if (name.equalsIgnoreCase("minecraft:wandering_trader")) return EntityTypes.aX;
		else if (name.equalsIgnoreCase("minecraft:shulker")) return EntityTypes.ay;
		else if (name.equalsIgnoreCase("minecraft:witch")) return EntityTypes.aY;
		else if (name.equalsIgnoreCase("minecraft:shulker_bullet")) return EntityTypes.az;
		else if (name.equalsIgnoreCase("minecraft:wither")) return EntityTypes.aZ;
		else if (name.equalsIgnoreCase("minecraft:wither_skeleton")) return EntityTypes.ba;
		else if (name.equalsIgnoreCase("minecraft:wither_skull")) return EntityTypes.bb;
		else if (name.equalsIgnoreCase("minecraft:wolf")) return EntityTypes.bc;
		else if (name.equalsIgnoreCase("minecraft:zoglin")) return EntityTypes.bd;
		else if (name.equalsIgnoreCase("minecraft:zombie")) return EntityTypes.be;
		else if (name.equalsIgnoreCase("minecraft:zombie_horse")) return EntityTypes.bf;
		else if (name.equalsIgnoreCase("minecraft:zombie_villager")) return EntityTypes.bg;
		else if (name.equalsIgnoreCase("minecraft:zombie_piglin")) return EntityTypes.bh;
		else if (name.equalsIgnoreCase("minecraft:area_affect_cloud")) return EntityTypes.b;
		else if (name.equalsIgnoreCase("minecraft:eye_of_ender")) return EntityTypes.B;
		else if (name.equalsIgnoreCase("minecraft:armor_stand")) return EntityTypes.c;
		else if (name.equalsIgnoreCase("minecraft:falling_block")) return EntityTypes.C;
		else if (name.equalsIgnoreCase("minecraft:axolotl")) return EntityTypes.e;
		else if (name.equalsIgnoreCase("minecraft:fox")) return EntityTypes.E;
		else if (name.equalsIgnoreCase("minecraft:bat")) return EntityTypes.f;
		else if (name.equalsIgnoreCase("minecraft:ghast")) return EntityTypes.F;
		else if (name.equalsIgnoreCase("minecraft:bee")) return EntityTypes.g;
		else if (name.equalsIgnoreCase("minecraft:giant")) return EntityTypes.G;
		else if (name.equalsIgnoreCase("minecraft:blaze")) return EntityTypes.h;
		else if (name.equalsIgnoreCase("minecraft:glow_item_frame")) return EntityTypes.H;
		else if (name.equalsIgnoreCase("minecraft:boat")) return EntityTypes.i;
		else if (name.equalsIgnoreCase("minecraft:glow_squid")) return EntityTypes.I;
		else if (name.equalsIgnoreCase("minecraft:cat")) return EntityTypes.j;
		else if (name.equalsIgnoreCase("minecraft:goat")) return EntityTypes.J;
		else if (name.equalsIgnoreCase("minecraft:cave_spider")) return EntityTypes.k;
		else if (name.equalsIgnoreCase("minecraft:guardian")) return EntityTypes.K;
		else if (name.equalsIgnoreCase("minecraft:chicken")) return EntityTypes.l;
		else if (name.equalsIgnoreCase("minecraft:hoglin")) return EntityTypes.L;
		else if (name.equalsIgnoreCase("minecraft:cod")) return EntityTypes.m;
		else if (name.equalsIgnoreCase("minecraft:horse")) return EntityTypes.M;
		else if (name.equalsIgnoreCase("minecraft:cow")) return EntityTypes.n;
		else if (name.equalsIgnoreCase("minecraft:husk")) return EntityTypes.N;
		else if (name.equalsIgnoreCase("minecraft:creeper")) return EntityTypes.o;
		else if (name.equalsIgnoreCase("minecraft:illusioner")) return EntityTypes.O;
		else if (name.equalsIgnoreCase("minecraft:dolphin")) return EntityTypes.p;
		else if (name.equalsIgnoreCase("minecraft:iron_golem")) return EntityTypes.P;
		else if (name.equalsIgnoreCase("minecraft:donkey")) return EntityTypes.q;
		else if (name.equalsIgnoreCase("minecraft:item")) return EntityTypes.Q;
		else if (name.equalsIgnoreCase("minecraft:dragon_fireball")) return EntityTypes.r;
		else if (name.equalsIgnoreCase("minecraft:item_frame")) return EntityTypes.R;
		else if (name.equalsIgnoreCase("minecraft:drowned")) return EntityTypes.s;
		else if (name.equalsIgnoreCase("minecraft:fireball")) return EntityTypes.S;
		else if (name.equalsIgnoreCase("minecraft:elder_guardian")) return EntityTypes.t;
		else if (name.equalsIgnoreCase("minecraft:end_crystal")) return EntityTypes.u;
		else if (name.equalsIgnoreCase("minecraft:lightning")) return EntityTypes.U;
		else if (name.equalsIgnoreCase("minecraft:ender_dragon")) return EntityTypes.v;
		else if (name.equalsIgnoreCase("minecraft:llama")) return EntityTypes.V;
		else if (name.equalsIgnoreCase("minecraft:enderman")) return EntityTypes.w;
		else if (name.equalsIgnoreCase("minecraft:llama_spit")) return EntityTypes.W;
		else if (name.equalsIgnoreCase("minecraft:endermite")) return EntityTypes.x;
		else if (name.equalsIgnoreCase("minecraft:magma_cube")) return EntityTypes.X;
		else if (name.equalsIgnoreCase("minecraft:evoker")) return EntityTypes.y;
		else if (name.equalsIgnoreCase("minecraft:evoker_fangs")) return EntityTypes.z;
		else if (name.equalsIgnoreCase("minecraft:minecart")) return EntityTypes.Z;
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
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a player to send the packet to.");
			return false;
		}
		
		if (Bukkit.getPlayer(args[0]) == null) {
			Main.sendPluginMessage(sender, ChatColor.RED + "This player does not exist.");
			return false;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		EntityPlayer cp = ((CraftPlayer) p).getHandle();

		if (args.length < 2) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a packet type.");
			return false;
		}
		
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
					World w = ((CraftWorld) Bukkit.getWorld(args[3])).getHandle();
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
					PacketPlayOutServerDifficulty s = new PacketPlayOutServerDifficulty(matchDifficulty(args[3]), false);

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
				String reason = ChatColor.translateCoString.join(" ", reasonArgs);

				PacketLoginOutDisconnect s = new PacketLoginOutDisconnect(new ChatComponentText(reason));
				cp.b.sendPacket(s);
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

					PacketPlayOutBlockBreakAnimation s = new PacketPlayOutBlockBreakAnimation(p.getEntityId(), new BlockPos(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])), parsedStage);

					cp.b.sendPacket(s);
				} catch (NumberSyntaxException e) {
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

					net.minecraft.world.item.ItemStack item = new org.bukkit.inventory.ItemStack(m);
				} else {
					try {
						org.bukkit.inventory.ItemStack bukkititem = new org.bukkit.inventory.ItemStack(m);
						int maxAmount = bukkititem.getMaxStackSize();
						int amount = Integer.parseInt(args[5]);

						if (amount < 1 || amount > maxAmount) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a number between 1 and " + Integer.toString(maxAmount));
						}

						bukkititem.setAmount(amount);

						net.minecraft.world.item.ItemStack item = ((CraftItemStack) bukkititem).asNMSCopy();
						
					} catch (NumberSyntaxException e) {
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
