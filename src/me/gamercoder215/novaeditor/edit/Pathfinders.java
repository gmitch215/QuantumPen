package me.gamercoder215.novaeditor.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.novaeditor.Main;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityInsentient;
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
import net.minecraft.world.entity.ai.goal.PathfinderGoalFleeSun;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowOwner;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
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
import net.minecraft.world.entity.ai.goal.PathfinderGoalSwell;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWaterJump;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWrapped;
import net.minecraft.world.entity.monster.IRangedEntity;

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
	  if (t == EntityType.AREA_EFFECT_CLOUD) return EntityAreaEffectCloud.class;
	  else return null;
    
  }
  	public static String matchGoal(PathfinderGoalWrapped p) {
	    if (p.j() instanceof PathfinderGoalArrowAttack) return "attack_arrow";
	    else if (p.j() instanceof PathfinderGoalAvoidTarget) return "target_avoid";
	    else if (p.j() instanceof PathfinderGoalBeg) return "core_beg";
	    else if (p.j() instanceof PathfinderGoalBowShoot) return "attack_range_bow";
	    else if (p.j() instanceof PathfinderGoalBreakDoor) return "attack_breakdoor";
	    else if (p.j() instanceof PathfinderGoalCatSitOnBed) return "cat_sit_bed";
	    else if (p.j() instanceof PathfinderGoalCrossbowAttack) return "attack_range_crossbow";
	    else if (p.j() instanceof PathfinderGoalEatTile) return "ambient_eat_tile";
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
	    else if (p.j() instanceof PathfinderGoalLookAtPlayer) return "core_lookatplayer";
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
    else return "";
  }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 1) {
			Main.sendInvalidArgs(sender);
			return false;
		}
    if (args.length < 2) {
      Main.sendPluginMessage(sender, ChatColor.RED + "Please providea valid UUID.");
      return false;
    }
    try {

      UUID uid = UUID.fromString(args[1]);

      if (Bukkit.getEntity(uid) == null) {
        Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an alive entity's UUID.");
        return false;
      }

      if (Bukkit.getEntity(uid) instanceof LivingEntity) {
        Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an alive entity's UUID.");
        return false;
      }

      LivingEntity bukkittarget = (LivingEntity) Bukkit.getEntity(uid);

      EntityInsentient target = ((EntityInsentient) ((CraftEntity) bukkittarget).getHandle());

      switch (args[0].toLowerCase()) {
        case "clear":
          for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
            target.bP.a(p.j());
          }
          break;
        case "add":
          List<Integer> priorities = new ArrayList<>();
          for (PathfinderGoalWrapped p : getPathfinders(bukkittarget)) {
            priorities.add(p.h());
          }



          int maxPriority = priorities.get(0);

          for (int i = 1; i < priorities.toArray().length; i++) if (priorities.get(i) > maxPriority) maxPriority = priorities.get(i);

          int newP = maxPriority + 1;
          try {
            switch (args[2].toLowerCase()) {
              case "attack_arrow":
                if (args.length < 4) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid speed modifier.");
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
              case "target_avoid":
                if (args.length < 4) {
                  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the entity type to avoid.");
                  return false;
                }
                break;
              default:
                Main.sendPluginMessage(sender, ChatColor.RED + "This pathfinder does not exist or is not supported yet.");
                return false;
            }
          } catch (ClassCastException e) {
            Main.sendPluginMessage(sender, ChatColor.RED + "This pathfinder is not supported for this entity.");
            return false;
          } catch (NumberFormatException e) {
            Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing the numbers.");
            return false;
          }

          break;
        case "remove":
          break;
        case "list":
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