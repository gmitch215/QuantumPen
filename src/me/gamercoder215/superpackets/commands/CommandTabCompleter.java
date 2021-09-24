package me.gamercoder215.superpackets.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandTabCompleter implements TabCompleter {
	
	public static List<String> getClientPacketList(String type) {
		if (type.equalsIgnoreCase("play")) {

			List<String> packetList = new ArrayList<>();
			
			packetList.add("spawn_entity");
			packetList.add("spawn_experience_orb");
			packetList.add("spawn_painting");
			packetList.add("gui_close");
			packetList.add("camera_block_break_animation");
			packetList.add("playergui_set_item_inventory");
			packetList.add("playergui_set_item_cooldown");
			packetList.add("camera_create_explosion");
			packetList.add("state_weather_rain_start");
			packetList.add("state_weather_rain_end");
			packetList.add("state_gamemode_change");
			packetList.add("state_win_noendscreen");
			packetList.add("state_win_endscreen");
			packetList.add("state_arrowhit");
			packetList.add("state_elderguardianscreen");
			packetList.add("state_respawnscreen");
			packetList.add("state_respawnscreen_immediate");
			packetList.add("gui_open_horse");
			packetList.add("gui_open_sign");
			packetList.add("gui_open_book");
			packetList.add("gui_open_container");
			packetList.add("gui_open_beacon");
			packetList.add("gui_open_anvil");
			packetList.add("gui_open_enchantment");
			packetList.add("gui_open_crafting");
			packetList.add("gui_open_smoker");
			packetList.add("gui_open_blastfurnace");
			packetList.add("gui_open_furnace");
			packetList.add("gui_open_grindstone");
			packetList.add("gui_open_cartography");
			packetList.add("gui_open_shulker");
			packetList.add("gui_open_villager");
			packetList.add("gui_open_stonecutter");
			packetList.add("camera_unload_chunk");
			packetList.add("gui_edit_helditem");
			packetList.add("combat_enter");
			packetList.add("combat_end");
			packetList.add("combat_death");
			packetList.add("playergui_send_actionbar");
			packetList.add("camera_shader_enderman");
			packetList.add("camera_shader_creeper");
			packetList.add("camera_shader_spider");
			packetList.add("playergui_send_bossbar");
			packetList.add("animation_play_leavebed");
			packetList.add("animation_play_swing_mainhand");
			packetList.add("animation_play_swing_offhand");
			packetList.add("animation_play_takedmg");
			packetList.add("animation_play_crit");
			packetList.add("animation_play_crit_magical");
			packetList.add("playergui_send_title");
			packetList.add("playergui_send_subtitle");
			
			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			String[] newPacketList = (String[]) actualPacketList.toArray();
			Arrays.sort(newPacketList);
			
			return (Arrays.asList(newPacketList));
		} else if (type.equalsIgnoreCase("connection")) {
			List<String> packetList = new ArrayList<>();

			packetList.add("connection_kick_player");
			packetList.add("camera_updateviewdistance");

			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			String[] newPacketList = (String[]) actualPacketList.toArray();
			Arrays.sort(newPacketList);
			
			return (Arrays.asList(newPacketList));
		} else if (type.equalsIgnoreCase("settings")) {
			List<String> packetList = new ArrayList<>();

			packetList.add("settings_changedifficulty");
			packetList.add("playergui_changexp");
			packetList.add("playergui_updatehealth");

			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			String[] newPacketList = (String[]) actualPacketList.toArray();
			Arrays.sort(newPacketList);
			
			return (Arrays.asList(newPacketList));
		} else return null;
 	}
 	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clientpacket")) {
			if (args.length < 1) {
				List<String> packetTypes = new ArrayList<>();
				packetTypes.add("play");
				packetTypes.add("connection");
				packetTypes.add("settings");

				return packetTypes;
			}
			else if (args.length < 2) {
				List<String> playersOnline = new ArrayList<>();
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					playersOnline.add(p.getName());
				}
				
				return playersOnline;
			} else if (args.length == 2) {
				return getClientPacketList(args[0]);
			} else if (args.length >= 3) {
				String packet = args[1];
				
				switch (packet.replaceAll("minecraft:", "")) {
					case "spawn_entity":
						if (args.length == 3) {
							List<String> entities = new ArrayList<>();
							
							for (EntityType t : EntityType.values()) {
								entities.add("minecraft:" + t.name().toLowerCase());
							}
						}
						
						break;
					case "spawn_experience_orb":
						if (args.length == 3) {
							List<String> worlds = new ArrayList<>();
							
							for (World w : Bukkit.getServer().getWorlds()) {
								worlds.add("minecraft:" + w.getName());
							}
							
							return worlds;
						}
						break;
					case "spawn_painting":
						if (args.length == 3) {
							List<String> art = new ArrayList<>();

							for (Art a : Art.values()) {
								art.add("minecraft:" + a.name().toLowerCase());
							}

							return art;
						}
						break;
					case "settings_changedifficulty":
						if (args.length == 3) {
							List<String> difficulty = new ArrayList<>();

							difficulty.add("peaceful");
							difficulty.add("easy");
							difficulty.add("normal");
							difficulty.add("hard");

							return difficulty;
						} else if (args.length == 4) {
							List<String> forceDifficulty = new ArrayList<>();

							forceDifficulty.add("true");
							forceDifficulty.add("false");

							return forceDifficulty;
						}
						break;
					case "camera_block_break_animation":
						break;
					case "playergui_set_item_inventory":
						if (args.length == 3) {
							List<String> inventoryList = new ArrayList<>();
	
							inventoryList.add("armor.head");
							inventoryList.add("armor.chest");
							inventoryList.add("armor.legs");
							inventoryList.add("armor.feet");
							inventoryList.add("weapon.offhand");
							inventoryList.add("crafting.topleft");
							inventoryList.add("crafting.topright");
							inventoryList.add("crafting.bottomleft");
							inventoryList.add("crafting.bottomright");
							inventoryList.add("crafting.result");
							inventoryList.add("inventory.row1.1");
							inventoryList.add("inventory.row1.2");
							inventoryList.add("inventory.row1.3");
							inventoryList.add("inventory.row1.4");
							inventoryList.add("inventory.row1.5");
							inventoryList.add("inventory.row1.6");
							inventoryList.add("inventory.row1.7");
							inventoryList.add("inventory.row1.8");
							inventoryList.add("inventory.row1.9");
							inventoryList.add("inventory.row2.1");
							inventoryList.add("inventory.row2.2");
							inventoryList.add("inventory.row2.3");
							inventoryList.add("inventory.row2.4");
							inventoryList.add("inventory.row2.5");
							inventoryList.add("inventory.row2.6");
							inventoryList.add("inventory.row2.7");
							inventoryList.add("inventory.row2.8");
							inventoryList.add("inventory.row2.9");
							inventoryList.add("inventory.row3.1");
							inventoryList.add("inventory.row3.2");
							inventoryList.add("inventory.row3.3");
							inventoryList.add("inventory.row3.4");
							inventoryList.add("inventory.row3.5");
							inventoryList.add("inventory.row3.6");
							inventoryList.add("inventory.row3.7");
							inventoryList.add("inventory.row3.8");
							inventoryList.add("inventory.row3.9");
							inventoryList.add("hotbar.1");
							inventoryList.add("hotbar.2");
							inventoryList.add("hotbar.3");
							inventoryList.add("hotbar.4");
							inventoryList.add("hotbar.5");
							inventoryList.add("hotbar.6");
							inventoryList.add("hotbar.7");
							inventoryList.add("hotbar.8");
							inventoryList.add("hotbar.9");
							inventoryList.add("cursor");
							
							return inventoryList;
						} else if (args.length == 4) {
							List<String> materials = new ArrayList<>();
							
							for (Material m : Material.values()) {
								materials.add("minecraft:" + m.name().toLowerCase());
							}
							
							return materials;
						}
						break;
					default:
						return null;
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("superpackets")) {
			if (args.length < 1) {
				List<String> baseCommandList = new ArrayList<>();

				baseCommandList.add("info");
				baseCommandList.add("help");

				return baseCommandList;
			} else return null;
		}
		
		return null;
	}

}
