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
		BOOLEAN, INTEGER, ENTITYTYPE, DECIMAL;
	}

	public static List<String> getBlockActions() {
		String[] actionList = {
			"game_breaknaturally",
			"game_breaknaturally_tool",
			"game_setbiome",
			"game_settype",
			"game_update"
		};
		List<String> actions = new ArrayList<>();
		for (String s : actionList) {
			actions.add("minecraft:" + s);
		}

		Collections.sort(actions);

		return actions;
	}
	
	public static List<String> getPlayerDataProperties() {
		String[] actionList = {
			"property_canseeplayer",
			"property_canfly",
			"property_client_viewdistance",
			"property_flyspeed",
			"property_healthscale",
			"property_ping",
			"property_name_display",
			"property_name_custom",
			"property_name_playerlist",
			"property_timestamp",
			"property_timestamp_offset",
			"property_exp",
			"property_exp_total",
			"property_flying",
			"property_healthscaled",
			"property_sleepingignored",
			"property_timerelative",
			"property_sprinting",
			"property_attackcooldown",
			"property_exhaustion",
			"property_foodlevel",
			"property_saturation",
			"property_sleepticks",
			"property_usingshield",
		};
		
		List<String> actions = new ArrayList<>();
		
		for (String s : actionList) {
			actions.add("minecraft:" + s);
		}
		
		Collections.sort(actions);
		
		return actions;
	}
	
	public static List<String> getPlayerDataActions() {
		String[] actionList = {
			"camera_hideplayer",
			"server_loaddata",
			"server_kick",
			"server_resettime",
			"server_resetweather",
			"playergui_resettitle",
			"server_savedata",
			"playergui_healthscale",
			"playergui_healthscale_isscaled",
			"settings_walkspeed",
			"camera_showplayer",
			"server_updatecommands",
			"server_updateinventory",
			"playergui_closeinventory",
			"playergui_open_enchanting",
			"playergui_open_crafting",
			"playergui_open_merchant",
		};
		
		List<String> actions = new ArrayList<>();
		
		for (String s : actionList) {
			actions.add("minecraft:" + s);
		}
		
		Collections.sort(actions);
		
		return actions;
	}

	public static List<String> getBlockProperties() {
		String[] propertiesList = {
			"game_biome",
			"game_redstonepower",
			"game_breakspeed",
			"game_humidity",
			"game_light_fromblocks",
			"game_light_fromsky",
			"game_lightlevel",
			"game_temperature",
			"game_powered",
			"game_powered_indirectly",
			"game_isempty",
			"game_ispassable",
			"game_isliquid",
			"game_ispreferredmaterial"
		};
		List<String> properties = new ArrayList<>();
		for (String s : propertiesList) {
			properties.add("minecraft:" + s);
		}

		Collections.sort(properties);

		return properties;
	}

	public static List<String> getTypes(ArgumentType type) {
		if (type == ArgumentType.BOOLEAN) {
			List<String> bool = new ArrayList<>();
			bool.add("true");
			bool.add("false");

			return bool;
		} else if (type == ArgumentType.ENTITYTYPE) {
			List<String> types = new ArrayList<>();
			for (EntityType t : EntityType.values()) {
				types.add("minecraft:" + t.name());
			}

			return types;
		} else return null;
	}
	
	public static List<String> getWorldActions() {
		String[] actionsList = {
				"server_save",
				"spawns_ambientlimit",
				"spawns_animallimit",
				"spawns_ambientlimit_water",
				"spawns_animallimit_water",
				"server_autosave",
				"game_clearweathertime",
				"settings_difficulty",
				"settings_hardcore",
				"settings_keepspawnloaded",
				"settings_pvp",
				"spawns_monsterlimit",
				"spawns_ticksper_ambient",
				"spawns_ticksper_animal",
				"spawns_ticksper_monster",
				"spawns_ticksper_ambient_water",
				"game_thunderduration",
				"game_thundering",
				"game_strikelightning",
				"game_strikelightning_effect",
				"game_time",
				"game_fulltime",
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

	public static List<String> getBehaviorList() {
		String[] oldbehaviors = {
			"behavior_attack",
			"behavior_bedjump",
			"behavior_bell",
			"behavior_bell_alert",
			"behavior_bell_ring",
			"behavior_villager_betterjob",
			"behavior_villager_bonemeal",
			"behavior_villager_career",
			"behavior_villager_celebrate",
			"behavior_celebrate_tolocation",
			"behavior_villager_cooldown",
			"behavior_illager_crossbowattack",
			"behavior_villager_farm",
			"behavior_finditem",
			"behavior_forgetanger",
			"behavior_hide",
			"behavior_findhidingplace",
			"behavior_findhidingplace_raid",
			"behavior_interact_door",
			"behavior_interact_player",
			"behavior_villager_leavejob",
			"behavior_breed_villager",
			"behavior_breed_animal",
			"behavior_villager_nearestvillage",
			"behavior_panic_villager",
			"behavior_play",
			"behavior_villager_potentialjobsite",
			"behavior_villager_profession",
			"behavior_raid",
			"behavior_raid_reset",
			"behavior_retreat",
			"behavior_sleep",
			"behavior_swim",
			"behavior_villager_herogift",
			"behavior_wake",
			"behavior_walkhome",
			"behavior_villager_work",
			"behavior_villager_work_composter",
			"behavior_move",
			"behavior_swim_random",
			"behavior_findwater",
			"behavior_panic_animal",
		};

		List<String> behaviors = new ArrayList<>();

		for (String s : oldbehaviors) {
			behaviors.add("minecraft:" + s);
		}

		Collections.sort(behaviors);

		return behaviors;
	}

	public static List<String> getControllerActions() {
		String[] oldactions = {
			"movement_goto",
			"movement_tick",
			"looking_lookatentity",
			"looking_lookatcoordinates",
			"looking_tick",
			"jumping_jump",
			"jumping_tick",
		};

		List<String> actions = new ArrayList<>();

		for (String s : oldactions) {
			actions.add("minecraft:" + s);
		}

		Collections.sort(actions);

		return actions;
	}

	public static List<String> getEntityActions(String type) {
		if (type.equalsIgnoreCase("edit")) {

			String[] oldedits = {
				"set_velocity",
				"set_ai",
				"set_collidable",
				"set_lastdamage",
				"set_arrowsinbody",
				"set_max_air",
				"set_max_nodamageticks",
				"set_nodamageticks",
				"set_arrowcooldown",
			};
			List<String> edits = new ArrayList<>();
			
			for (String s : oldedits) {
				edits.add("minecraft:" + s);
			}

			Collections.sort(edits);

			return edits;
		} else if (type.equalsIgnoreCase("do")) {
			String[] oldactions = {
				"swing_mainhand",
				"swing_offhand",
				"game_ejectpassenger",
				"game_remove",
				"game_leavevehicle"
			};

			List<String> actions = new ArrayList<>();

			for (String s : oldactions) {
				actions.add("minecraft:" + s);
			}

			Collections.sort(actions);

			return actions;
		} else return null;
	}
 	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
			case "attribute": {
				switch (args.length) {
					case 1: {
						List<String> actions = new ArrayList<>();
						actions.add("add");
						actions.add("set");
						actions.add("has");
						actions.add("list");

						return actions;
					}
					case 2: {
						List<String> uuids = new ArrayList<>();
						
						if (sender instanceof Player) {
							((Player) sender).getNearbyEntities(5, 5, 5).forEach(entity -> {
								if (!(((CraftEntity) entity).getHandle() instanceof EntityCreature)) return;
								uuids.add(entity.getUniqueId().toString());
							});
							
							return uuids;
						} else return null;
					}

				}
			}
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
			case "editentity": {
				switch(args.length) {
					case 1:
						List<String> uuids = new ArrayList<>();

						for (Player p : Bukkit.getOnlinePlayers()) {
							uuids.add(p.getUniqueId().toString());
						}					

						Collections.sort(uuids);
						return uuids;
					case 2:
						List<String> editActions = new ArrayList<>();

						editActions.add("do");
						editActions.add("edit");
						
						return editActions;
					case 3:
						return getEntityActions(args[1]);
					
				}
				break;
			}
			case "playerdata": {
				switch (args.length) {
					case 1: {
						List<String> players = new ArrayList<>();
						
						for (Player p : Bukkit.getOnlinePlayers()) {
							players.add(p.getName());
						}
						
						return players;
					}
					case 2: {
						List<String> actions = new ArrayList<>();
						
						actions.add("action");
						actions.add("property");
						
						return actions;
					}
					case 3: {
						if (args[1].equalsIgnoreCase("property")) {
							return getPlayerDataProperties();
						} else if (args[1].equalsIgnoreCase("action")) {
							return getPlayerDataActions();
						}
					}
				}
			}
			case "pathfinders": {
				switch (args.length) {
					case 1:
						List<String> actions = new ArrayList<>();
						
						actions.add("add");
						actions.add("remove");
						actions.add("list");
						actions.add("clear");
						actions.add("add_target");
						actions.add("controller");
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