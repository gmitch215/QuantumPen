package me.gamercoder215.superpackets.commands;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.gamercoder215.superpackets.Main;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityTypes;

public class ClientPacket implements CommandExecutor {
	
	protected Main plugin;
	
	public ClientPacket(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("clientpacket").setExecutor(this);
		plugin.getCommand("clientpacket").setTabCompleter(new CommandTabCompleter());
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
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a packet to send to the player.");
			return false;
		}
		
		switch (args[1].replaceAll("minecraft:", "")) {
			case "spawn_entity":
				if (args.length < 3) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet, and markers are server-side only.");
					return false;
				}
				
				if (args[2].equalsIgnoreCase("minecraft:painting") || args[2].equalsIgnoreCase("minecraft:experience_orb")) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Paintings and Experience Orbs use the spawn_paining and spawn_experience_orb packet.");
					return false;
				}
				
				if (matchEntityType(args[2]) == null) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type. Paintings and Experience Orbs are used in the spawn_painting and spawn_experience_orb packet, and marks are server-side only.");
					return false;
				}
				
				if (args.length < 4) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position X.");
					return false;	
				}
				
				if (args.length < 5) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Y.");
					return false;	
				}
				
				if (args.length < 6) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a position Z.");
					return false;	
				}
				
				try {
					PacketPlayOutSpawnEntity s = new PacketPlayOutSpawnEntity(r.nextInt(), UUID.randomUUID(), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), 0, 0, matchEntityType(args[2]), 0, null);
					cp.b.sendPacket(s);
					
				} catch (NumberFormatException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please fix your coordinates.");
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
