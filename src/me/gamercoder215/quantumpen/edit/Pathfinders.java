package me.gamercoder215.quantumpen.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.packets.ClientPacket;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBeg;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBowShoot;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreakDoor;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreath;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalCatSitOnBed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalCrossbowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalDoorOpen;
import net.minecraft.world.entity.ai.goal.PathfinderGoalEatTile;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFishSchool;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFleeSun;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowBoat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowOwner;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalJumpOnBlock;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLlamaFollow;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveThroughVillage;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalNearestVillage;
import net.minecraft.world.entity.ai.goal.PathfinderGoalOcelotAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalOfferFlower;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPerch;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRaid;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomFly;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomSwim;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRestrictSun;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSit;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSwell;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTradeWithPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWater;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWaterJump;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWrapped;
import net.minecraft.world.entity.ai.goal.PathfinderGoalZombieAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalDefendVillage;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.entity.animal.EntityFishSchool;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityPerchable;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.raid.EntityRaider;

public class Pathfinders implements CommandExecutor {
	protected Main plugin;

	public Pathfinders(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("pathfinders").setExecutor(this);
	}

  public static Set<PathfinderGoalWrapped> getPathfinders(LivingEntity en) {
    EntityInsentient e = (EntityInsentient) ((CraftEntity) en).getHandle();
    
    return e.bP.c();
  }

	  public static Class<?> matchClass(EntityType t) {
		  return ClientPacket.matchEntityType(t.name()).a();
	  }
  	public static String matchGoal(PathfinderGoalWrapped p) {
	    if (p.j() instanceof PathfinderGoalArrowAttack) return "attack_arrow";
	    else if (p.j() instanceof PathfinderGoalAvoidTarget) return "target_avoid";
	    else if (p.j() instanceof PathfinderGoalBeg) return "wolf_beg";
	    else if (p.j() instanceof PathfinderGoalBowShoot) return "attack_range_bow";
	    else if (p.j() instanceof PathfinderGoalBreakDoor) return "attack_breakdoor";
	    else if (p.j() instanceof PathfinderGoalCatSitOnBed) return "cat_sit_bed";
	    else if (p.j() instanceof PathfinderGoalCrossbowAttack) return "attack_range_crossbow";
	    else if (p.j() instanceof PathfinderGoalEatTile) return "ambient_eattile";
	    else if (p.j() instanceof PathfinderGoalDoorOpen) return "core_interact_opendoor";
	    else if (p.j() instanceof PathfinderGoalFleeSun) return "movement_fleesun";
	    else if (p.j() instanceof PathfinderGoalFollowEntity) return "movement_follow_entity";
	    else if (p.j() instanceof PathfinderGoalFollowOwner) return "movement_follow_owner";
	    else if (p.j() instanceof PathfinderGoalFollowParent) return "movement_follow_parent";
	    else if (p.j() instanceof PathfinderGoalRaid) return "illager_raid";
	    else if (p.j() instanceof PathfinderGoalMeleeAttack) return "attack_melee";
	    else if (p.j() instanceof PathfinderGoalOcelotAttack) return "ocelot_attack";
	    else if (p.j() instanceof PathfinderGoalOfferFlower) return "golem_offer_flower";
	    else if (p.j() instanceof PathfinderGoalPanic) return "core_panic";
	    else if (p.j() instanceof PathfinderGoalLookAtPlayer) return "core_lookatentity";
	    else if (p.j() instanceof PathfinderGoalBreath) return "core_waterbreathe";
	    else if (p.j() instanceof PathfinderGoalBreed) return "animal_breed";
	    else if (p.j() instanceof PathfinderGoalMoveThroughVillage) return "movement_throughvillage";
	    else if (p.j() instanceof PathfinderGoalMoveTowardsRestriction) return "movement_towards_restriction";
	    else if (p.j() instanceof PathfinderGoalMoveTowardsTarget) return "movement_towards_target";
	    else if (p.j() instanceof PathfinderGoalNearestVillage) return "movement_nearest_village";
	    else if (p.j() instanceof PathfinderGoalPerch) return "dragon_perch";
	    else if (p.j() instanceof PathfinderGoalRandomFly) return "random_fly";
	    else if (p.j() instanceof PathfinderGoalRandomLookaround) return "random_lookaround";
	    else if (p.j() instanceof PathfinderGoalRandomStroll) return "random_move";
	    else if (p.j() instanceof PathfinderGoalRandomStrollLand) return "random_move_land";
	    else if (p.j() instanceof PathfinderGoalRandomSwim) return "random_swim";
	    else if (p.j() instanceof PathfinderGoalRestrictSun) return "movement_restrictsun";
	    else if (p.j() instanceof PathfinderGoalSwell) return "creeper_swell";
	    else if (p.j() instanceof PathfinderGoalWaterJump) return "dolphin_waterjump";
	    else if (p.j() instanceof PathfinderGoalTradeWithPlayer) return "villager_tradeplayer";
	    else if (p.j() instanceof PathfinderGoalWater) return "movement_findwater";
	    else if (p.j() instanceof PathfinderGoalZombieAttack) return "zombie_attack";
	    else if (p.j() instanceof PathfinderGoalNearestAttackableTarget) return "attack_nearest_target";
	    else if (p.j() instanceof PathfinderGoalHurtByTarget) return "attack_defensive";
	    else if (p.j() instanceof PathfinderGoalDefendVillage) return "attack_defendvillage";
	    else if (p.j() instanceof PathfinderGoalFloat) return "core_float";
	    else if (p.j() instanceof PathfinderGoalFishSchool) return "fish_school";
	    else if (p.j() instanceof PathfinderGoalFollowBoat) return "movement_follow_boat";
	    else if (p.j() instanceof PathfinderGoalJumpOnBlock) return "cat_sit_block";
	    else if (p.j() instanceof PathfinderGoalLlamaFollow) return "llama_follow";
	    else if (p.j() instanceof PathfinderGoalSit) return "tameable_sit";
	    else return "UNSUPPORTED";
  }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
	if (args.length < 1) {
		Main.sendInvalidArgs(sender);
		return false;
	}
    if (args.length < 2) {
      Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
      return false;
    }
    try {

      UUID uid = UUID.fromString(args[1]);

      if (Bukkit.getEntity(uid) == null) {
        Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an alive entity's UUID.");
        return false;
      }

      if (!(Bukkit.getEntity(uid) instanceof LivingEntity)) {
        Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an alive entity's UUID.");
        return false;
      }
      
      

      LivingEntity bukkittarget = (LivingEntity) Bukkit.getEntity(uid);
      
      if (!(((CraftEntity) bukkittarget).getHandle() instanceof EntityInsentient)) {
    	  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an entity that supports pathfinders.");
    	  return false;
      }

      EntityInsentient target = ((EntityInsentient) ((CraftEntity) bukkittarget).getHandle());

      switch (args[0].toLowerCase()) {
        case "clear":
        	if (!(sender.hasPermission("quantumpen.pathfinder.clear"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
            target.bP.a(p.j());
          }
          break;
        case "add":
        	if (!(sender.hasPermission("quantumpen.pathfinder.add"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          List<Integer> priorities = new ArrayList<>();
          for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
            priorities.add(p.h());
          }
					
					if (args.length < 3) {
						Main.sendInvalidArgs(sender);
						return false;
					}


          int maxPriority = priorities.get(0);

          for (int i = 1; i < priorities.toArray().length; i++) if (priorities.get(i) > maxPriority) maxPriority = priorities.get(i);

          int newP = maxPriority + 1;
          try {
            switch (args[2].toLowerCase()) {
              case "attack_arrow": {
                if (args.length < 4) {
                	Main.sendValidSpeedModifier(sender);
                  return false;
                }

                if (args.length < 5) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an attacking interval minimum.");
                  return false;
                }

                if (args.length < 6) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an attacking interval maximum.");
                  return false;
                }

                if (args.length < 7) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an attack radius.");
                  return false;
                }

                PathfinderGoalArrowAttack p = new PathfinderGoalArrowAttack((IRangedEntity) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Float.parseFloat(args[6]));

                target.bP.a(newP, p);

                break;
              }
              case "target_avoid": {
                if (args.length < 4) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the entity type to avoid.");
                  return false;
                }
                
                if (args.length < 5) {
                	Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a sensitivity distance (how far away the target must be to avoid)");
                	return false;
                }
                
                if (args.length < 6) {
                	Main.sendValidSpeedModifier(sender);
                	return false;
                }
                

				Class<EntityInsentient> entityClass = ((Class<EntityInsentient>) matchClass(EntityType.valueOf(args[3])));

				PathfinderGoalAvoidTarget<?> p = new PathfinderGoalAvoidTarget<>((EntityCreature) target, entityClass, Float.parseFloat(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[5]) * 1.25);
				
				target.bP.a(newP, p);
								
                break;
              }
							case "wolf_beg": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a looking distance.");
									return false;
								}

								PathfinderGoalBeg p = new PathfinderGoalBeg((EntityWolf) target, Float.parseFloat(args[3]));

								target.bP.a(newP, p);
							}
							case "attack_range_bow": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a minimum attack interval.");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attack radius.");
									return false;
								}

								PathfinderGoalBowShoot p = new PathfinderGoalBowShoot((EntityMonster) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Float.parseFloat(args[5]));

								target.bP.a(newP, p);
							}
							case "attack_breakdoor": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide how long it will take to break the door.");
									return false;
								}

								Predicate<EnumDifficulty> diff = (d) -> (d == EnumDifficulty.d); 

								PathfinderGoalBreakDoor p = new PathfinderGoalBreakDoor(target, Integer.parseInt(args[3]), diff);

								target.bP.a(newP, p);
							}
							case "cat_sit_bed": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid search range.");
									return false;
								}

								PathfinderGoalCatSitOnBed p = new PathfinderGoalCatSitOnBed((EntityCat) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));

								target.bP.a(p);
							}
							case "attack_range_crossbow": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attack radius.");
									return false;
								}
								
								
								PathfinderGoalCrossbowAttack p = new PathfinderGoalCrossbowAttack((EntityMonster) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.bP.a(newP, p);
								break;
							}

							case "ambient_eattile": {
								PathfinderGoalEatTile p = new PathfinderGoalEatTile(target);

								target.bP.a(newP, p);
								break;
							}
							case "core_interact_opendoor": {
								if (args.length < 4) {
									PathfinderGoalDoorOpen p = new PathfinderGoalDoorOpen(target, false);

									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalDoorOpen p = new PathfinderGoalDoorOpen(target, Boolean.parseBoolean(args[3]));

									target.bP.a(newP, p);
									break;
								}
							}
							case "movement_fleesun": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalFleeSun p = new PathfinderGoalFleeSun((EntityCreature) target, Double.parseDouble(args[3]));

								target.bP.a(newP, p);
							}
							case "movement_follow_entity": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a distance to how far away the entity is to stop this mob (stop distance).");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an area size.");
									return false;
								}

								PathfinderGoalFollowEntity p = new PathfinderGoalFollowEntity(target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
								target.bP.a(newP, p);
								break;
							}
							case "movement_follow_owner": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid start distance.");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid stop distance.");
									return false;
								}

								if (args.length < 7) {
									PathfinderGoalFollowOwner p = new PathfinderGoalFollowOwner((EntityTameableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), false);

									target.bP.a(newP, p);
								} else {
									PathfinderGoalFollowOwner p = new PathfinderGoalFollowOwner((EntityTameableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), Boolean.parseBoolean(args[6]));

									target.bP.a(newP, p);
								}
							}
							case "movement_follow_parent": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalFollowParent p = new PathfinderGoalFollowParent((EntityAnimal) target, Double.parseDouble(args[3]));

								target.bP.a(newP, p);
								break;
							}
							case "illager_raid": {
								
								PathfinderGoalRaid<EntityRaider> p = new PathfinderGoalRaid<EntityRaider>((EntityRaider) target);

								target.bP.a(newP, p);
							}
							case "attack_melee": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									PathfinderGoalMeleeAttack p = new PathfinderGoalMeleeAttack((EntityCreature) target, Double.parseDouble(args[3]), false);

									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalMeleeAttack p = new PathfinderGoalMeleeAttack((EntityCreature) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.bP.a(newP, p);
									break;
								}
							}
							case "ocelot_attack": {
								PathfinderGoalOcelotAttack p = new PathfinderGoalOcelotAttack(target);

								target.bP.a(newP, p);
								break;
							}
							case "golem_offer_flower": {
								PathfinderGoalOfferFlower p = new PathfinderGoalOfferFlower((EntityIronGolem) target);

								target.bP.a(newP, p);
							}
							case "core_panic": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalPanic p = new PathfinderGoalPanic((EntityCreature) target, Double.parseDouble(args[3]));

								target.bP.a(newP, p);
								break;
							}
							case "core_lookatentity": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type.");
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a look distance.");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid probability.");
									return false;
								}

								if (Float.parseFloat(args[5]) > 100f) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid probability.");
									return false;
								}

								if (args.length < 7) {
									PathfinderGoalLookAtPlayer p = new PathfinderGoalLookAtPlayer(target, (Class<? extends EntityLiving>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),false);

									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalLookAtPlayer p = new PathfinderGoalLookAtPlayer(target, (Class<? extends EntityLiving>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),Boolean.parseBoolean(args[6]));

									target.bP.a(newP, p);
									break;
								}
							}
							case "core_waterbreathe": {
								PathfinderGoalBreath p = new PathfinderGoalBreath((EntityCreature) target);

								target.bP.a(newP, p);
								break;
							}
							case "animal_breed": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalBreed p = new PathfinderGoalBreed((EntityAnimal) target, Double.parseDouble(args[3]));

								target.bP.a(newP, p);
								break;
							}
							case "movement_throughvillage": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid reach distance.");
									return false;
								}

								if (args.length < 6) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please prrovide whether or not the entity can deal with doors.");
									return false;
								}

								BooleanSupplier sup = () -> Boolean.parseBoolean(args[5]);

								if (args.length < 7) {
									PathfinderGoalMoveThroughVillage p = new PathfinderGoalMoveThroughVillage((EntityCreature) target, Double.parseDouble(args[3]), false, Integer.parseInt(args[4]), sup);

									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalMoveThroughVillage p = new PathfinderGoalMoveThroughVillage((EntityCreature) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[6]), Integer.parseInt(args[4]), sup);

									target.bP.a(newP, p);
									break;
								}
							}
							case "movement_towards_restriction": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalMoveTowardsRestriction p = new PathfinderGoalMoveTowardsRestriction((EntityCreature) target, Double.parseDouble(args[3]));
								target.bP.a(newP, p);
								break;
							}
							case "movement_towards_target": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid reach distance.");
									return false;
								}

								PathfinderGoalMoveTowardsTarget p = new PathfinderGoalMoveTowardsTarget((EntityCreature) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.bP.a(newP, p);
								break;
							}
							case "movement_nearest_village": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid search interval.");
									return false;
								}

								PathfinderGoalNearestVillage p = new PathfinderGoalNearestVillage((EntityCreature) target, Integer.parseInt(args[3]));

								target.bP.a(newP, p);
								break;
							}
							case "dragon_perch": {
								PathfinderGoalPerch p = new PathfinderGoalPerch((EntityPerchable) target);
								target.bP.a(newP, p);
								break;
							}
							case "random_fly": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PathfinderGoalRandomFly p = new PathfinderGoalRandomFly((EntityCreature) target, Double.parseDouble(args[3]));

								target.bP.a(newP, p);
								break;
							}
							case "random_lookaround": {
								PathfinderGoalRandomLookaround p = new PathfinderGoalRandomLookaround(target);

								target.bP.a(newP, p);
								break;
							}
							case "random_move": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a walking interval.");
									return false;
								}
								
								if (args.length < 6) {
									PathfinderGoalRandomStroll p = new PathfinderGoalRandomStroll((EntityCreature) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), false);
									
									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalRandomStroll p = new PathfinderGoalRandomStroll((EntityCreature) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Boolean.parseBoolean(args[5]));
									
									target.bP.a(newP, p);
									break;
								}
							}
							case "random_move_land": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a probability.");
									return false;
								}
								
								PathfinderGoalRandomStrollLand p = new PathfinderGoalRandomStrollLand((EntityCreature) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));
								target.bP.a(newP, p);
								break;
							}
							case "random_swim": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a walking interval.");
									return false;
								}
								
								PathfinderGoalRandomSwim p = new PathfinderGoalRandomSwim((EntityCreature) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
								target.bP.a(newP, p);
								
								break;
							}
							case "movement_restrictsun": {
								PathfinderGoalRestrictSun p = new PathfinderGoalRestrictSun((EntityCreature) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "creeper_swell": {
								PathfinderGoalSwell p = new PathfinderGoalSwell((EntityCreeper) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "dolphin_waterjump": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid jumping interval.");
									return false;
								}
								
								PathfinderGoalWaterJump p = new PathfinderGoalWaterJump((EntityDolphin) target, Integer.parseInt(args[3]));
								target.bP.a(newP, p);
								break;
							}
							case "villager_tradeplayer": {
								PathfinderGoalTradeWithPlayer p = new PathfinderGoalTradeWithPlayer((EntityVillagerAbstract) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "movement_findwater": {
								PathfinderGoalWater p = new PathfinderGoalWater((EntityCreature) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "zombie_attack": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									PathfinderGoalZombieAttack p = new PathfinderGoalZombieAttack((EntityZombie) target, Double.parseDouble(args[3]), false);

									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalZombieAttack p = new PathfinderGoalZombieAttack((EntityZombie) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.bP.a(newP, p);
									break;
								}
							}
							case "attack_nearest_target": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type.");
									return false;
								}
								Class<EntityLiving> entityClass = (Class<EntityLiving>)matchClass(EntityType.valueOf(args[3]));
								if (args.length < 5) {
									PathfinderGoalNearestAttackableTarget<EntityLiving> p = new PathfinderGoalNearestAttackableTarget<EntityLiving>(target, entityClass, true);
									target.bP.a(newP, p);
									break;
								} else {
									PathfinderGoalNearestAttackableTarget<EntityLiving> p = new PathfinderGoalNearestAttackableTarget<EntityLiving>(target, entityClass, Boolean.parseBoolean(args[4]));
									target.bP.a(newP, p);
									break;
								}
							}
							case "attack_defensive": {
								List<Class<EntityLiving>> ignoreList = new ArrayList<>();
								
								for (int i = 3; i < args.length; i++) {
									Class<EntityLiving> entityClass = (Class<EntityLiving>) matchClass(EntityType.valueOf(args[i]));
									
									ignoreList.add(entityClass);
								}
									
								PathfinderGoalHurtByTarget p = new PathfinderGoalHurtByTarget((EntityCreature) target, ignoreList.toArray(new Class<?>[0]));
								target.bP.a(newP, p);
								break;
							}
							case "attack_defendvillage": {
								PathfinderGoalDefendVillage p = new PathfinderGoalDefendVillage((EntityIronGolem) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "core_float": {
								PathfinderGoalFloat p = new PathfinderGoalFloat(target);
								
								target.bP.a(newP, p);
								break;
							}
							case "fish_school": {
								PathfinderGoalFishSchool p = new PathfinderGoalFishSchool((EntityFishSchool) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "movement_follow_boat": {
								PathfinderGoalFollowBoat p = new PathfinderGoalFollowBoat((EntityCreature) target);
								
								target.bP.a(newP, p);
								break;
							}
							case "cat_sit_block": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								PathfinderGoalJumpOnBlock p = new PathfinderGoalJumpOnBlock((EntityCat) target, Double.parseDouble(args[3]));
								
								target.bP.a(newP, p);
								break;
							}
							case "llama_follow": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								PathfinderGoalLlamaFollow p = new PathfinderGoalLlamaFollow((EntityLlama) target, Double.parseDouble(args[3]));
								
								target.bP.a(newP, p);
								break;
							}
							case "tameable_sit": {
								PathfinderGoalSit p = new PathfinderGoalSit((EntityTameableAnimal) target);
								
								target.bP.a(newP, p);
								break;
							}
							default:
								Main.sendPluginMessage(sender, ChatColor.RED + "This pathfinder does not exist or is not supported yet.");
								return false;
            }
            sender.sendMessage(ChatColor.GREEN + "Pathfinder Successfully added!");
            return true;
          } catch (ClassCastException e) {
        	  Main.sendPluginMessage(sender, ChatColor.RED + "This pathfinder is not supported for this entity.");
        	  return false;
          } catch (NumberFormatException | NullPointerException e) {
        	  Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
        	  return false;
          }

        case "remove":
        	if (!(sender.hasPermission("quantumpen.pathfinder.remove"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
        	try {
                for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
                	if (p.h() == Integer.parseInt(args[2])) {
                		target.bP.a(p.j());
                		sender.sendMessage(ChatColor.GREEN + "Pathfinder successfully removed!");
                		break;
                	}
                }
                
        	} catch (NumberFormatException e) {
        		Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the pathfinder priority ID. If you do not know it, use the list function.");
        		return false;
        	}
        	
        	break;
        case "list":
        	if (!(sender.hasPermission("quantumpen.pathfinder.list"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          Map<Integer, String> goals = new HashMap<>();
          
          for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
        	  goals.put(p.h(), matchGoal(p));
          }
          
          List<String> messages = new ArrayList<>();
          
          goals.forEach((priority, goal) -> {
        	  messages.add(ChatColor.BLUE + "Priority: " + ChatColor.GOLD + Integer.toString(priority) + ChatColor.DARK_AQUA + " | " + ChatColor.BLUE + "Goal Name: " + ChatColor.GOLD + goal);
          });
          
          String msg = ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Entity Goals\n" + String.join("\n", messages);
          
          sender.sendMessage(msg);

          break;
        default:
          Main.sendInvalidArgs(sender);
          return false;
      }
    } catch (IllegalArgumentException e) {
      Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
      return false;
    }
	return true;
	}
}