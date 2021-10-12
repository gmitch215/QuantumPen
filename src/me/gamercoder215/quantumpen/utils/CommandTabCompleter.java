package me.gamercoder215.quantumpen.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.world.entity.EntityInsentient;

public class CommandTabCompleter implements TabCompleter {
	
	public enum ArgumentType {
		BOOLEAN;
	}

	public static List<String> getTypes(ArgumentType type) {
		if (type == ArgumentType.BOOLEAN) {
			List<String> bool = new ArrayList<>();
			bool.add("true");
			bool.add("false");

			return bool;
		} else return null;
	}
	
	public static List<String> getWorldActions() {
		String[] actionsList = {
				"server_save",
				"spawns_ambientlimit",
				"spawns_animallimit",
				"server_autosave",
				"game_clearweathertime",
				"settings_difficulty",
				"settomgs_hardcore",
				"settings_keepspawnloaded",
				"settings_pvp",
				"spawns_monsterlimit",
				"spawns_ticksper_ambient",
				"spawns_ticksper_animal",
				"spawns_ticksper_monster",
				"spawns_ticksper_ambient_water",
				"spawns_ticksper_animal_water",
				"game_thunderduration",
				"game_thundering",
				"game_strikelightning",
				"game_strikelightning_effect",
				"game_time",
				"game_weatherduration",
				"game_spawnflags",
		};
		
		List<String> actualActionsList = new ArrayList<>();
		
		for (String s : actionsList) {
			actualActionsList.add("minecraft:" + s);
		}

		
		Collections.sort(actualActionsList);
		
		return (actualActionsList);
	}

	public static List<String> getClientPacketList(String type) {
		if (type.equalsIgnoreCase("play")) {

			String[] packetList = {
			
				"spawn_entity",
				"spawn_experience_orb",
				"spawn_painting",
				"gui_close",
				"camera_block_break_animation",
				"playergui_set_item_inventory",
				"playergui_set_item_cooldown",
				"camera_create_explosion",
				"state_weather_rain_start",
				"state_weather_rain_end",
				"state_gamemode_change",
				"state_win_noendscreen",
				"state_win_endscreen",
				"state_arrowhit",
				"state_elderguardianscreen",
				"state_respawnscreen",
				"state_respawnscreen_immediate",
				"gui_open_horse",
				"gui_open_sign",
				"gui_open_book",
				"gui_open_container",
				"gui_open_beacon",
				"gui_open_anvil",
				"gui_open_enchantment",
				"gui_open_crafting",
				"gui_open_smoker",
				"gui_open_blastfurnace",
				"gui_open_furnace",
				"gui_open_grindstone",
				"gui_open_cartography",
				"gui_open_shulker",
				"gui_open_villager",
				"gui_open_stonecutter",
				"gui_edit_slotselected",
				"combat_enter",
				"combat_end",
				"animation_play_leavebed",
				"animation_play_swing_mainhand",
				"animation_play_swing_offhand",
				"animation_play_takedmg",
				"animation_play_crit",
				"animation_play_crit_magical",
				"camera_shader_enderman",
				"camera_shader_creeper",
				"camera_shader_spider",
			};
			
			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			Collections.sort(actualPacketList);
			
			return (actualPacketList);
		} else if (type.equalsIgnoreCase("connection")) {
			String[] packetList = {

			"connection_kick_player",
			"camera_updateviewdistance"
			
			};

			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			Collections.sort(actualPacketList);
			
			return (actualPacketList);
		} else if (type.equalsIgnoreCase("settings")) {
			String[] packetList = {

				"settings_changedifficulty",
				"playergui_changexp",
				"playergui_updatehealth",
				"playergui_updatefood"
			};
			List<String> actualPacketList = new ArrayList<>();
			
			for (String s : packetList) {
				actualPacketList.add("minecraft:" + s);
			}

			
			Collections.sort(actualPacketList);
			
			return (actualPacketList);
		} else return null;
 	}
	
	public static List<String> getPathfinderList() {
		String[] oldpathfinders = {
				"attack_arrow",
				"villager_tradeplayer",
				"animal_breed",
				"core_waterbreathe",
				"creeper_swell",
				"target_avoid",
				"wolf_beg",
				"attack_range_bow",
				"attack_breakdoor",
				"illager_raid",
				"cat_sit_bed",
				"attack_range_crossbow",
				"ambient_eattile",
				"movement_fleesun",
				"movement_follow_entity",
				"movenent_follow_owner",
				"core_interact_opendoor",
				"movenent_follow_parent",
				"core_lookatentity",
				"attack_melee",
				"movement_restrictsun",
				"random_lookaround",
				"random_swim",
				"random_move",
				"random_fly",
				"random_move_land",
				"golem_offer_flower",
				"movement_throughvillage",
				"movement_towards_target",
				"movement_towards_restriction",
				"movement_nearest_village",
				"dolphin_waterjump",
				"movement_findwater",
				"dragon_perch",
				"zombie_attack",
				"ocelot_attack",
				"attack_nearest_target",
				"attack_defensive",
				"attack_defendvillage",
				"core_panic",
				"core_float",
				"fish_school",
				"movement_follow_boat",
				"cat_sit_block",
				"llama_follow",
				"tameable_sit",
				"core_tempt",
		};
		
		List<String> pathfinders = new ArrayList<>();
		
		for (String s : oldpathfinders) {
			pathfinders.add("minecraft:" + s);
		}
		
		Collections.sort(pathfinders);
		
		return pathfinders;
	}

	public static List<String> getEntityActions(String type) {
		if (type.equalsIgnoreCase("edit")) {
			List<String> edits = new ArrayList<>();
				edits.add("set_velocity");
				edits.add("set_ai");
				edits.add("set_collidable");
				edits.add("set_lastdamage");
				edits.add("set_arrowsinbody");
				edits.add("set_max_air");
				edits.add("set_invisible");
				edits.add("set_health");
				edits.add("set_max_nodamageticks");
				edits.add("set_nodamageticks");
				edits.add("set_arrowcooldown");

			Collections.sort(edits);

			return edits;
		} else return null;
	}
 	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
			case "chunk": {
				switch(args.length) {
					case 1:
						List<String> actions = new ArrayList<>();
						actions.add("load");
						actions.add("unload");
						actions.add("forceload");
						actions.add("unforceload");
						return actions;
					case 2: {
						switch (args[0]) {
							case "load":
							case "unload": {
								return getTypes(ArgumentType.BOOLEAN);
							}
							default:
								return null;
						}
					}
				}
			}
			case "clientpacket":
				switch (args.length) {
					case 1:
						List<String> packetTypes = new ArrayList<>();
						packetTypes.add("play");
						packetTypes.add("connection");
						packetTypes.add("settings");
			
						return packetTypes;
					case 2:
						List<String> playersOnline = new ArrayList<>();
						
						for (Player p : Bukkit.getOnlinePlayers()) {
							playersOnline.add(p.getName());
						}
						
						return playersOnline;
					case 3:
						return getClientPacketList(args[0]);
					case 4:
						Player target = Bukkit.getPlayer(args[1]);
						String packet = args[2];
						
						switch (packet.replaceAll("minecraft:", "")) {
							case "spawn_entity":
								if (args.length == 4) {
									List<String> entities = new ArrayList<>();
									
									for (EntityType t : EntityType.values()) {
										entities.add("minecraft:" + t.name().toLowerCase());
									}
									
									return entities;	
								} else if (args.length == 5) {
									List<String> xPos = new ArrayList<>();
									
									xPos.add(Integer.toString(target.getLocation().getBlockX()));
									return xPos;
								} else if (args.length == 6) {
									List<String> xPos = new ArrayList<>();
									
									xPos.add(Integer.toString(target.getLocation().getBlockY()));
									return xPos;
								} else if (args.length == 7) {
									List<String> xPos = new ArrayList<>();
									
									xPos.add(Integer.toString(target.getLocation().getBlockZ()));
									return xPos;
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
								if (args.length == 4) {
									List<String> difficulty = new ArrayList<>();
			
									difficulty.add("peaceful");
									difficulty.add("easy");
									difficulty.add("normal");
									difficulty.add("hard");
			
									return difficulty;
								} else if (args.length == 5) {
									return getTypes(ArgumentType.BOOLEAN);
								}
								break;
							case "camera_block_break_animation":
								break;
							case "playergui_set_item_inventory":
								if (args.length == 4) {
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
								} else if (args.length == 5) {
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
				break;
			case "editentity":
				switch(args.length) {
					case 1:
						List<String> uuids = new ArrayList<>();

						for (Player p : Bukkit.getOnlinePlayers()) {
							uuids.add(p.getUniqueId().toString());
						}					

						Collections.sort(uuids);
						return uuids;
					case 2:
						return getEntityActions(args[1]);
					
				}
				break;
			case "pathfinders": {
				switch (args.length) {
					case 1:
						List<String> actions = new ArrayList<>();
						
						actions.add("add");
						actions.add("remove");
						actions.add("list");
						actions.add("clear");
						actions.add("add_target");
						actions.add("remove_target");
						
						return actions;
					case 2:
						List<String> uuids = new ArrayList<>();
						
						if (sender instanceof Player) {
							((Player) sender).getNearbyEntities(5, 5, 5).forEach(entity -> {
								if (!(((CraftEntity) entity).getHandle() instanceof EntityInsentient)) return;
								uuids.add(entity.getUniqueId().toString());
							});
							
							return uuids;
						} else return null;
					case 3:
						if (args[0].equalsIgnoreCase("add"))
						return getPathfinderList();
						else if (args[0].equalsIgnoreCase("remove")) {
							List<String> goals = new ArrayList<>();
							
							UUID uid = UUID.fromString(args[1]);
							
							LivingEntity bukkite = (LivingEntity) Bukkit.getEntity(uid);
							
						    EntityInsentient e = (EntityInsentient) ((CraftEntity) bukkite).getHandle();
						    
						    e.bP.c().forEach(pathfinder -> {
						    	goals.add(Integer.toString(pathfinder.h()));
						    });
						    
						    return goals;
						}
						else return null;
					default:
						return null;
				}
			}
			case "quantumpen": {
				switch (args.length) {
					case 1: {
						List<String> actions = new ArrayList<>();
						
						actions.add("info");
						break;
					}
				}
			}
			case "server": {
				switch (args.length) {
					case 1:
						List<String> arg0 = new ArrayList<>();
						arg0.add("get");
						arg0.add("set");
						
						return arg0;
					case 2: 
						switch (args[0]) {
							case "get": {
								
								String[] fetched = {
										"settings_allowend",
										"settings_allownether",
										"settings_allowflight",
										"chunk_limit_spawnambient",
										"chunk_limit_spawnanimal",
										"bukkit_version",
										"settings_defaultgamemode",
										"settings_generatestructures",
										"server_motd",
										"server_name",
										"settings_onlinemode",
										"server_version",
										"settings_viewdistance",
										"chunk_limit_spawnambient_water",
										"chunk_limit_spawnanimal_water",
										"server_defaultworld_type",
										"settings_hardcore",
										"server_port",
										"settings_spawnprotection",
										"spawns_ambientticks",
										"spawns_animalticks",
										"spawns_monsterticks",
										"spawns_ambientticks_water",
										"spawns_waterticks",
										"settings_idletimeout",
								};
								List<String> fetched2 = new ArrayList<>();
								
								
								for (String s : fetched) {
									fetched2.add("minecraft:" + s);
								}

								
								Collections.sort(fetched2);
								
								return fetched2;
							}
						}
					default:
						return null;
				}
			}
		}
		return null;
	}

}