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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.packets.ClientPacket;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.AssignProfessionFromJobSite;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.ai.behavior.GoToCelebrateLocation;
import net.minecraft.world.entity.ai.behavior.GoToClosestVillage;
import net.minecraft.world.entity.ai.behavior.GoToPotentialJobSite;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlace;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlaceDuringRaid;
import net.minecraft.world.entity.ai.behavior.LookAndFollowTradingPlayerSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PlayTagWithOtherKids;
import net.minecraft.world.entity.ai.behavior.PoiCompetitorScan;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.ReactToBell;
import net.minecraft.world.entity.ai.behavior.ResetProfession;
import net.minecraft.world.entity.ai.behavior.ResetRaidStatus;
import net.minecraft.world.entity.ai.behavior.RingBell;
import net.minecraft.world.entity.ai.behavior.SetClosestHomeAsWalkTarget;
import net.minecraft.world.entity.ai.behavior.SetHiddenState;
import net.minecraft.world.entity.ai.behavior.SetRaidStatus;
import net.minecraft.world.entity.ai.behavior.SleepInBed;
import net.minecraft.world.entity.ai.behavior.SocializeAtBell;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.behavior.UseBonemeal;
import net.minecraft.world.entity.ai.behavior.VillagerCalmDown;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.ai.behavior.WakeUp;
import net.minecraft.world.entity.ai.behavior.WorkAtComposter;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.ai.behavior.YieldJobSite;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.PathfindToRaidGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.crafting.Ingredient;

public class Pathfinders implements CommandExecutor {
	protected Main plugin;

	public Pathfinders(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("pathfinders").setExecutor(this);
		plugin.getCommand("pathfinders").setTabCompleter(new CommandTabCompleter());
	}

  public static Set<WrappedGoal> getPathfinders(LivingEntity en) {
    Mob e = (Mob) ((CraftEntity) en).getHandle();
    
    return e.goalSelector.getAvailableGoals();
  }
  
  public static Set<WrappedGoal> getPathfindersTarget(LivingEntity en) {
	    Mob e = (Mob) ((CraftEntity) en).getHandle();
	    
	    return e.targetSelector.getAvailableGoals();
  }
  
  

	  public static Class<? extends Entity> matchClass(EntityType t) {
		  return ClientPacket.matchEntityType(t.name()).getBaseClass();
	  }

		public static String matchBehavior(Behavior<?> b) {
			if (b instanceof MeleeAttack) return "behavior_attack";
			else if (b instanceof JumpOnBed) return "behavior_bedjump";
			else if (b instanceof SocializeAtBell) return "behavior_bell";
			else if (b instanceof ReactToBell) return "behavior_bell_alert";
			else if (b instanceof RingBell) return "behavior_bell_ring";
			else if (b instanceof PoiCompetitorScan) return "behavior_villager_betterjob";
			else if (b instanceof UseBonemeal) return "behavior_villager_bonemeal";
			else if (b instanceof AssignProfessionFromJobSite) return "behavior_villager_career";
			else if (b instanceof CelebrateVillagersSurvivedRaid) return "behavior_villager_celebrate";
			else if (b instanceof GoToCelebrateLocation) return "behavior_celebrate_tolocation";
			else if (b instanceof VillagerCalmDown) return "behavior_villager_cooldown";
			else if (b instanceof CrossbowAttack) return "behavior_illager_crossbowattack";
			else if (b instanceof HarvestFarmland) return "behavior_villager_farm";
			else if (b instanceof GoToWantedItem) return "behavior_finditem";
			else if (b instanceof StopBeingAngryIfTargetDead) return "behavior_forgetanger";
			else if (b instanceof SetHiddenState) return "behavior_hide";
			else if (b instanceof LocateHidingPlace) return "behavior_home";
			else if (b instanceof LocateHidingPlaceDuringRaid) return "behavior_homeraid";
			else if (b instanceof InteractWithDoor) return "behavior_interact_door";
			else if (b instanceof LookAndFollowTradingPlayerSink) return "behavior_interact_player";
			else if (b instanceof YieldJobSite) return "behavior_villager_leavejob";
			else if (b instanceof VillagerMakeLove) return "behavior_breed_villager";
			else if (b instanceof AnimalMakeLove) return "behavior_breed_animal";
			else if (b instanceof GoToClosestVillage) return "behavior_villager_nearestvillage";
			else if (b instanceof VillagerPanicTrigger) return "behavior_panic_villager";
			else if (b instanceof AnimalPanic) return "behavior_panic_animal";
			else if (b instanceof PlayTagWithOtherKids) return "behavior_play";
			else if (b instanceof GoToPotentialJobSite) return "behavior_villager_potentialjobsite";
			else if (b instanceof ResetProfession) return "behavior_villager_profession";
			else if (b instanceof SetRaidStatus) return "behavior_raid";
			else if (b instanceof ResetRaidStatus) return "behavior_raid_reset";
			else if (b instanceof BackUpIfTooClose) return "behavior_retreat";
			else if (b instanceof SleepInBed) return "behavior_sleep";
			else if (b instanceof Swim) return "behavior_swim";
			else if (b instanceof GiveGiftToHero) return "behavior_villager_herogift";
			else if (b instanceof WakeUp) return "behavior_wake";
			else if (b instanceof SetClosestHomeAsWalkTarget) return "behavior_walkhome";
			else if (b instanceof WorkAtPoi) return "behavior_villager_work";
			else if (b instanceof WorkAtComposter) return "behavior_villager_work_composter";
			else if (b instanceof RandomSwim) return "behavior_swim_random";
			else if (b instanceof TryFindWater) return "behavior_findwater";
			else if (b instanceof MoveToTargetSink) return "behavior_move";
			else return "UNSUPPORTED";
		}

  	public static String matchGoal(WrappedGoal p) {
	    if (p.getGoal() instanceof RangedAttackGoal) return "attack_arrow";
	    else if (p.getGoal() instanceof AvoidEntityGoal) return "target_avoid";
	    else if (p.getGoal() instanceof BegGoal) return "wolf_beg";
	    else if (p.getGoal() instanceof RangedBowAttackGoal) return "attack_range_bow";
	    else if (p.getGoal() instanceof BreakDoorGoal) return "attack_breakdoor";
	    else if (p.getGoal() instanceof CatLieOnBedGoal) return "cat_sit_bed";
	    else if (p.getGoal() instanceof RangedCrossbowAttackGoal) return "attack_range_crossbow";
	    else if (p.getGoal() instanceof EatBlockGoal) return "ambient_eattile";
	    else if (p.getGoal() instanceof OpenDoorGoal) return "core_interact_opendoor";
	    else if (p.getGoal() instanceof FleeSunGoal) return "movement_fleesun";
	    else if (p.getGoal() instanceof FollowMobGoal) return "movement_follow_entity";
	    else if (p.getGoal() instanceof FollowOwnerGoal) return "movement_follow_owner";
	    else if (p.getGoal() instanceof FollowParentGoal) return "movement_follow_parent";
	    else if (p.getGoal() instanceof PathfindToRaidGoal) return "illager_raid";
	    else if (p.getGoal() instanceof MeleeAttackGoal) return "attack_melee";
	    else if (p.getGoal() instanceof OcelotAttackGoal) return "ocelot_attack";
	    else if (p.getGoal() instanceof OfferFlowerGoal) return "golem_offer_flower";
	    else if (p.getGoal() instanceof PanicGoal) return "core_panic";
	    else if (p.getGoal() instanceof LookAtPlayerGoal) return "core_lookatentity";
	    else if (p.getGoal() instanceof BreathAirGoal) return "core_waterbreathe";
	    else if (p.getGoal() instanceof BreedGoal) return "animal_breed";
	    else if (p.getGoal() instanceof MoveThroughVillageGoal) return "movement_throughvillage";
	    else if (p.getGoal() instanceof MoveTowardsRestrictionGoal) return "movement_towards_restriction";
	    else if (p.getGoal() instanceof MoveTowardsTargetGoal) return "movement_towards_target";
	    else if (p.getGoal() instanceof StrollThroughVillageGoal) return "movement_nearest_village";
	    else if (p.getGoal() instanceof LandOnOwnersShoulderGoal) return "dragon_perch";
	    else if (p.getGoal() instanceof WaterAvoidingRandomFlyingGoal) return "random_fly";
	    else if (p.getGoal() instanceof RandomLookAroundGoal) return "random_lookaround";
	    else if (p.getGoal() instanceof RandomStrollGoal) return "random_move";
	    else if (p.getGoal() instanceof WaterAvoidingRandomStrollGoal) return "random_move_land";
	    else if (p.getGoal() instanceof RandomSwimmingGoal) return "random_swim";
	    else if (p.getGoal() instanceof RestrictSunGoal) return "movement_restrictsun";
	    else if (p.getGoal() instanceof SwellGoal) return "creeper_swell";
	    else if (p.getGoal() instanceof DolphinJumpGoal) return "dolphin_waterjump";
	    else if (p.getGoal() instanceof TradeWithPlayerGoal) return "villager_tradeplayer";
	    else if (p.getGoal() instanceof TryFindWaterGoal) return "movement_findwater";
	    else if (p.getGoal() instanceof ZombieAttackGoal) return "zombie_attack";
	    else if (p.getGoal() instanceof NearestAttackableTargetGoal) return "attack_nearest_target";
	    else if (p.getGoal() instanceof HurtByTargetGoal) return "attack_defensive";
	    else if (p.getGoal() instanceof DefendVillageTargetGoal) return "attack_defendvillage";
	    else if (p.getGoal() instanceof FloatGoal) return "core_float";
	    else if (p.getGoal() instanceof FollowFlockLeaderGoal) return "fish_school";
	    else if (p.getGoal() instanceof FollowBoatGoal) return "movement_follow_boat";
	    else if (p.getGoal() instanceof CatSitOnBlockGoal) return "cat_sit_block";
	    else if (p.getGoal() instanceof LlamaFollowCaravanGoal) return "llama_follow";
	    else if (p.getGoal() instanceof SitWhenOrderedToGoal) return "tameable_sit";
	    else if (p.getGoal() instanceof TemptGoal) return "core_tempt";
	    else return (ChatColor.RED + p.getGoal().getClass().getSimpleName());
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
      
      if (!(((CraftEntity) bukkittarget).getHandle() instanceof Mob)) {
    	  Main.sendPluginMessage(sender, ChatColor.RED + "Please provide an entity that supports pathfinders.");
    	  return false;
      }

      Mob target = ((Mob) ((CraftEntity) bukkittarget).getHandle());

      switch (args[0].toLowerCase()) {
  
		case "behavior": {
			if (args.length < 3) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid behavior type.");
				return false;
			}

			ServerLevel ws = ((CraftWorld) bukkittarget.getWorld()).getHandle();
			try {
				switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
					case "behavior_attack": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid cooldown between attacks.");
							return false;
						}

						MeleeAttack b = new MeleeAttack(Integer.parseInt(args[2]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_bedjump": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						JumpOnBed b = new JumpOnBed(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_bell": {
						SocializeAtBell b = new SocializeAtBell();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_bell_alert": {
						ReactToBell b = new ReactToBell();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_bell_ring": {
						RingBell b = new RingBell();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_betterjob": {
						PoiCompetitorScan b = new PoiCompetitorScan(((Villager) target).getVillagerData().getProfession());
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_bonemeal": {
						UseBonemeal b = new UseBonemeal();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_career": {
						AssignProfessionFromJobSite b = new AssignProfessionFromJobSite();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_celebrate": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a minimum duration.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a maximum duration.");
							return false;
						}
						
						CelebrateVillagersSurvivedRaid b = new CelebrateVillagersSurvivedRaid(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_celebrate_tolocation": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						GoToCelebrateLocation b = new GoToCelebrateLocation(Integer.parseInt(args[2]), Float.parseFloat(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_farm": {
						HarvestFarmland b = new HarvestFarmland();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_finditem": {
						if (args.length < 2) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid range.");
							return false;
						}
						
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						GoToWantedItem b = new GoToWantedItem(Float.parseFloat(args[2]), true, Integer.parseInt(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_forgetanger": {
						StopBeingAngryIfTargetDead b = new StopBeingAngryIfTargetDead();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_hide": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid hide range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid hiding duration, in SECONDS.");
							return false;
						}
						
						SetHiddenState b = new SetHiddenState(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_findhidingplace": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid hide range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid hook range (distance to hide).");
							return false;
						}
						
						if (args.length < 6) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						LocateHidingPlace b = new LocateHidingPlace(Integer.parseInt(args[2]), Float.parseFloat(args[4]), Integer.parseInt(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_findhidingplace_raid": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid hide range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						LocateHidingPlaceDuringRaid b = new LocateHidingPlaceDuringRaid(Integer.parseInt(args[2]), Float.parseFloat(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_interact_door": {
						InteractWithDoor b = new InteractWithDoor();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_interact_player": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						LookAndFollowTradingPlayerSink b = new LookAndFollowTradingPlayerSink(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
						
					}
					case "behavior_villager_leavejob": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						YieldJobSite b = new YieldJobSite(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_breed_villager": {
						VillagerMakeLove b = new VillagerMakeLove();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_breed_animal": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						AnimalMakeLove b = new AnimalMakeLove((net.minecraft.world.entity.EntityType<? extends Animal>) target.getType(), Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (Animal) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_nearestvillage": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						GoToClosestVillage b = new GoToClosestVillage(Float.parseFloat(args[3]), Integer.parseInt(args[2]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_panic_villager": {
						VillagerPanicTrigger b = new VillagerPanicTrigger();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_play": {
						PlayTagWithOtherKids b = new PlayTagWithOtherKids();
						
						if (b.tryStart(ws, (PathfinderMob) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_potentialjobsite": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						GoToPotentialJobSite b = new GoToPotentialJobSite(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_profession": {
						ResetProfession b = new ResetProfession();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_raid": {
						SetRaidStatus b = new SetRaidStatus();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_raid_reset": {
						ResetRaidStatus b = new ResetRaidStatus();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_retreat": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid distance.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						BackUpIfTooClose b = new BackUpIfTooClose(Integer.parseInt(args[2]), Float.parseFloat(args[3]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_sleep": {
						SleepInBed b = new SleepInBed();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_swim": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid chance.");
							return false;
						}
						
						float chance = Float.parseFloat(args[2]);
						
						if (chance < 0 || chance > 100) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid chance between 0 and 100.");
							return false;
						}
						
						Swim b = new Swim(chance);
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_herogift": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid gift interval.");
							return false;
						}
						
						GiveGiftToHero b = new GiveGiftToHero(Integer.parseInt(args[2]));
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_wake": {
						WakeUp b = new WakeUp();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_walkhome": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						SetClosestHomeAsWalkTarget b = new SetClosestHomeAsWalkTarget(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_work": {
						WorkAtPoi b = new WorkAtPoi();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_villager_work_composter": {
						WorkAtComposter b = new WorkAtComposter();
						
						if (b.tryStart(ws, (Villager) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_move": {
						MoveToTargetSink b = new MoveToTargetSink();
						
						if (b.tryStart(ws, target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_swim_random": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						RandomSwim b = new RandomSwim(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (PathfinderMob) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_findwater": {
						if (args.length < 4) {
							Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid range.");
							return false;
						}
						
						if (args.length < 5) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						TryFindWater b = new TryFindWater(Integer.parseInt(args[2]), Float.parseFloat(args[3]));
						
						if (b.tryStart(ws, (PathfinderMob) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					case "behavior_panic_animal": {
						if (args.length < 4) {
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						AnimalPanic b = new AnimalPanic(Float.parseFloat(args[2]));
						
						if (b.tryStart(ws, (PathfinderMob) target, 0)) {
							sender.sendMessage(ChatColor.GREEN + "Behavior addition successful!");
							break;
						} else {
							sender.sendMessage(ChatColor.RED + "Behavior addition unsuccessful.");
							break;
						}
					}
					default: {
						Main.sendPluginMessage(sender, ChatColor.RED + "This behavior does not exist.");
						return false;
					}
				}

			} catch (IllegalArgumentException e) {
				Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
				return false;
			} catch (ClassCastException e) {
				Main.sendPluginMessage(sender, ChatColor.RED + "This behavior is not supported for this entity.");
				return false;
			} catch (Exception e) {
				Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
				return false;
			}
			sender.sendMessage(ChatColor.GREEN + "Behavior successfully activated!");
			return true;
				}
				case "controller": {
		        	if (!(sender.hasPermission("quantumpen.pathfinder.controller"))) {
		        		Main.sendNoPermission(sender);
		        		return false;
		        	}

					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a controller type.");
						return false;
					}

					switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
						case "looking_lookatentity": {
							if (args.length < 4) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity UUID.");
								return false;
							}

							UUID uuid = UUID.fromString(args[3]);

							if (Bukkit.getEntity(uuid) == null) {
								Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity UUID.");
								return false;
							}

							org.bukkit.entity.Entity bukkitLookTarget = Bukkit.getEntity(uuid);
							net.minecraft.world.entity.Entity lookTarget = ((CraftEntity) bukkitLookTarget).getHandle();

							target.getLookControl().setLookAt(lookTarget);
							break;
						}
						case "looking_lookatcoordinates": {
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

							target.getLookControl().setLookAt(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
							break;	
						}
						case "looking_tick": {
							target.getLookControl().tick();
							break;
						}
						case "jumping_jump": {
							target.getJumpControl().jump();
							break;
						}
						case "jumping_tick": {
							target.getJumpControl().tick();
							break;
						}
						default: {
							Main.sendPluginMessage(sender, ChatColor.RED + "This controller type does not exist.");
							return false;
						}
					}
					sender.sendMessage(ChatColor.GREEN + "Controller Successful!");
					return true;
				}
        case "clear":
        	if (!(sender.hasPermission("quantumpen.pathfinder.clear"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
        	sender.sendMessage(ChatColor.GREEN + "Clearing...");
          List<Goal> newgoals = new ArrayList<>();
          List<Goal> newgoalsTarget = new ArrayList<>();
          
          getPathfinders(bukkittarget).forEach(p -> {
        	  newgoals.add(p.getGoal());
          });
          
          getPathfindersTarget(bukkittarget).forEach(p -> {
        	  newgoalsTarget.add(p.getGoal());
          });
          
          target.goalSelector.removeAllGoals();
          target.targetSelector.removeAllGoals();
          sender.sendMessage(ChatColor.GREEN + "Goals Cleared Successfully!");
          break;
        case "add": {
        	if (!(sender.hasPermission("quantumpen.pathfinder.add"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          List<Integer> priorities = new ArrayList<>();
          for (WrappedGoal p : getPathfinders(bukkittarget)) {
            priorities.add(p.getPriority());
          }
					
					if (args.length < 3) {
						Main.sendInvalidArgs(sender);
						return false;
					}


          int maxPriority = -1;
          
          try {
        	  maxPriority = priorities.get(0);
          } catch (IndexOutOfBoundsException e) {
        	  maxPriority = -1;
          }

          for (int i = 1; i < priorities.toArray().length; i++) if (priorities.get(i) > maxPriority) maxPriority = priorities.get(i);

          int newP = maxPriority + 1;
          try {
            switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
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

                RangedAttackGoal p = new RangedAttackGoal((RangedAttackMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Float.parseFloat(args[6]));

                target.goalSelector.addGoal(newP, p);

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
                

				Class<Mob> entityClass = ((Class<Mob>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())));

				AvoidEntityGoal<?> p = new AvoidEntityGoal<>((PathfinderMob) target, entityClass, Float.parseFloat(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[5]) * 1.25);
				
				target.goalSelector.addGoal(newP, p);	
                break;
              }
							case "wolf_beg": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a looking distance.");
									return false;
								}

								BegGoal p = new BegGoal((Wolf) target, Float.parseFloat(args[3]));

								target.goalSelector.addGoal(newP, p);
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

								RangedBowAttackGoal p = new RangedBowAttackGoal((Monster) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Float.parseFloat(args[5]));

								target.goalSelector.addGoal(newP, p);
							}
							case "attack_breakdoor": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide how long it will take to break the door.");
									return false;
								}

								Predicate<Difficulty> diff = (d) -> (d == Difficulty.HARD); 

								BreakDoorGoal p = new BreakDoorGoal(target, Integer.parseInt(args[3]), diff);

								target.goalSelector.addGoal(newP, p);
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

								CatLieOnBedGoal p = new CatLieOnBedGoal((Cat) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));

								target.goalSelector.addGoal(newP, p);
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
								
								
								RangedCrossbowAttackGoal p = new RangedCrossbowAttackGoal((Monster) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.goalSelector.addGoal(newP, p);
								break;
							}

							case "ambient_eattile": {
								EatBlockGoal p = new EatBlockGoal(target);

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "core_interact_opendoor": {
								if (args.length < 4) {
									OpenDoorGoal p = new OpenDoorGoal(target, false);

									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									OpenDoorGoal p = new OpenDoorGoal(target, Boolean.parseBoolean(args[3]));

									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "movement_fleesun": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								FleeSunGoal p = new FleeSunGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.goalSelector.addGoal(newP, p);
								break;
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

								FollowMobGoal p = new FollowMobGoal(target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
								target.goalSelector.addGoal(newP, p);
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
									FollowOwnerGoal p = new FollowOwnerGoal((TamableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), false);

									target.goalSelector.addGoal(newP, p);
								} else {
									FollowOwnerGoal p = new FollowOwnerGoal((TamableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), Boolean.parseBoolean(args[6]));

									target.goalSelector.addGoal(newP, p);
								}
							}
							case "movement_follow_parent": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								FollowParentGoal p = new FollowParentGoal((Animal) target, Double.parseDouble(args[3]));

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "illager_raid": {
								
								PathfindToRaidGoal<Raider> p = new PathfindToRaidGoal<Raider>((Raider) target);

								target.goalSelector.addGoal(newP, p);
							}
							case "attack_melee": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									MeleeAttackGoal p = new MeleeAttackGoal((PathfinderMob) target, Double.parseDouble(args[3]), false);

									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									MeleeAttackGoal p = new MeleeAttackGoal((PathfinderMob) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "ocelot_attack": {
								OcelotAttackGoal p = new OcelotAttackGoal(target);

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "golem_offer_flower": {
								OfferFlowerGoal p = new OfferFlowerGoal((IronGolem) target);

								target.goalSelector.addGoal(newP, p);
							}
							case "core_panic": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PanicGoal p = new PanicGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.goalSelector.addGoal(newP, p);
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
									LookAtPlayerGoal p = new LookAtPlayerGoal(target, (Class<? extends net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),false);

									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									LookAtPlayerGoal p = new LookAtPlayerGoal(target, (Class<? extends net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),Boolean.parseBoolean(args[6]));

									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "core_waterbreathe": {
								BreathAirGoal p = new BreathAirGoal((PathfinderMob) target);

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "animal_breed": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								BreedGoal p = new BreedGoal((Animal) target, Double.parseDouble(args[3]));

								target.goalSelector.addGoal(newP, p);
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
									MoveThroughVillageGoal p = new MoveThroughVillageGoal((PathfinderMob) target, Double.parseDouble(args[3]), false, Integer.parseInt(args[4]), sup);

									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									MoveThroughVillageGoal p = new MoveThroughVillageGoal((PathfinderMob) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[6]), Integer.parseInt(args[4]), sup);

									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "movement_towards_restriction": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								MoveTowardsRestrictionGoal p = new MoveTowardsRestrictionGoal((PathfinderMob) target, Double.parseDouble(args[3]));
								target.goalSelector.addGoal(newP, p);
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

								MoveTowardsTargetGoal p = new MoveTowardsTargetGoal((PathfinderMob) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "movement_nearest_village": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid search interval.");
									return false;
								}

								StrollThroughVillageGoal p = new StrollThroughVillageGoal((PathfinderMob) target, Integer.parseInt(args[3]));

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "dragon_perch": {
								LandOnOwnersShoulderGoal p = new LandOnOwnersShoulderGoal((ShoulderRidingEntity) target);
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "random_fly": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								WaterAvoidingRandomFlyingGoal p = new WaterAvoidingRandomFlyingGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "random_lookaround": {
								RandomLookAroundGoal p = new RandomLookAroundGoal(target);

								target.goalSelector.addGoal(newP, p);
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
									RandomStrollGoal p = new RandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), false);
									
									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									RandomStrollGoal p = new RandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Boolean.parseBoolean(args[5]));
									
									target.goalSelector.addGoal(newP, p);
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
								
								WaterAvoidingRandomStrollGoal p = new WaterAvoidingRandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));
								target.goalSelector.addGoal(newP, p);
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
								
								RandomSwimmingGoal p = new RandomSwimmingGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
								target.goalSelector.addGoal(newP, p);
								
								break;
							}
							case "movement_restrictsun": {
								RestrictSunGoal p = new RestrictSunGoal((PathfinderMob) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "creeper_swell": {
								SwellGoal p = new SwellGoal((Creeper) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "dolphin_waterjump": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid jumping interval.");
									return false;
								}
								
								DolphinJumpGoal p = new DolphinJumpGoal((Dolphin) target, Integer.parseInt(args[3]));
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "villager_tradeplayer": {
								TradeWithPlayerGoal p = new TradeWithPlayerGoal((AbstractVillager) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "movement_findwater": {
								TryFindWaterGoal p = new TryFindWaterGoal((PathfinderMob) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "zombie_attack": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									ZombieAttackGoal p = new ZombieAttackGoal((Zombie) target, Double.parseDouble(args[3]), false);

									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									ZombieAttackGoal p = new ZombieAttackGoal((Zombie) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "attack_nearest_target": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type.");
									return false;
								}
								Class<net.minecraft.world.entity.LivingEntity> entityClass = (Class<net.minecraft.world.entity.LivingEntity>)matchClass(EntityType.valueOf(args[3]));
								if (args.length < 5) {
									NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity> p = new NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity>(target, entityClass, true);
									target.goalSelector.addGoal(newP, p);
									break;
								} else {
									NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity> p = new NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity>(target, entityClass, Boolean.parseBoolean(args[4]));
									target.goalSelector.addGoal(newP, p);
									break;
								}
							}
							case "attack_defensive": {
								List<Class<net.minecraft.world.entity.LivingEntity>> ignoreList = new ArrayList<>();
								
								if (args.length >= 3) {
									for (int i = 3; i < args.length; i++) {
										Class<net.minecraft.world.entity.LivingEntity> entityClass = (Class<net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[i]));
										
										ignoreList.add(entityClass);
									}
								}
									
								HurtByTargetGoal p = new HurtByTargetGoal((PathfinderMob) target, ignoreList.toArray(new Class<?>[0]));
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "attack_defendvillage": {
								DefendVillageTargetGoal p = new DefendVillageTargetGoal((IronGolem) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "core_float": {
								FloatGoal p = new FloatGoal(target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "fish_school": {
								FollowFlockLeaderGoal p = new FollowFlockLeaderGoal((AbstractSchoolingFish) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "movement_follow_boat": {
								FollowBoatGoal p = new FollowBoatGoal((PathfinderMob) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "cat_sit_block": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								CatSitOnBlockGoal p = new CatSitOnBlockGoal((Cat) target, Double.parseDouble(args[3]));
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "llama_follow": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								LlamaFollowCaravanGoal p = new LlamaFollowCaravanGoal((Llama) target, Double.parseDouble(args[3]));
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "tameable_sit": {
								SitWhenOrderedToGoal p = new SitWhenOrderedToGoal((TamableAnimal) target);
								
								target.goalSelector.addGoal(newP, p);
								break;
							}
							case "core_tempt": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide valid items.");
									return false;
								}
								
								List<Material> items = new ArrayList<>();
								
								for (int i = 4; i < args.length; i++) {
									items.add(Material.matchMaterial(args[i].toUpperCase()));
								}
								
								List<net.minecraft.world.item.ItemStack> nmsItemStacks = new ArrayList<>();
								
								for (Material m : items) {
									org.bukkit.inventory.ItemStack bukkitstack = new org.bukkit.inventory.ItemStack(m);
									
									net.minecraft.world.item.ItemStack nmsstack = CraftItemStack.asNMSCopy(bukkitstack);
									
									nmsItemStacks.add(nmsstack);
								}
								
								Ingredient recipe = Ingredient.of(nmsItemStacks.stream());
								
								TemptGoal p = new TemptGoal((PathfinderMob) target, Double.parseDouble(args[3]), recipe, true);
								
								target.goalSelector.addGoal(newP, p);
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
        	  Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments:\n" + e.getLocalizedMessage());
        	  return false;
          }
      }
        case "add_target": {
        	if (!(sender.hasPermission("quantumpen.pathfinder.add"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          List<Integer> priorities = new ArrayList<>();
          for (WrappedGoal p : getPathfinders(bukkittarget)) {
            priorities.add(p.getPriority());
          }
					
					if (args.length < 3) {
						Main.sendInvalidArgs(sender);
						return false;
					}


          int maxPriority = -1;
          
          try {
        	  maxPriority = priorities.get(0);
          } catch (IndexOutOfBoundsException e) {
        	  maxPriority = -1;
          }

          for (int i = 1; i < priorities.toArray().length; i++) if (priorities.get(i) > maxPriority) maxPriority = priorities.get(i);

          int newP = maxPriority + 1;
          try {
            switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
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

                RangedAttackGoal p = new RangedAttackGoal((RangedAttackMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Float.parseFloat(args[6]));

                target.targetSelector.addGoal(newP, p);

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
                

				Class<Mob> entityClass = ((Class<Mob>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())));

				AvoidEntityGoal<?> p = new AvoidEntityGoal<>((PathfinderMob) target, entityClass, Float.parseFloat(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[5]) * 1.25);
				
				target.targetSelector.addGoal(newP, p);	
                break;
              }
							case "wolf_beg": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a looking distance.");
									return false;
								}

								BegGoal p = new BegGoal((Wolf) target, Float.parseFloat(args[3]));

								target.targetSelector.addGoal(newP, p);
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

								RangedBowAttackGoal p = new RangedBowAttackGoal((Monster) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Float.parseFloat(args[5]));

								target.targetSelector.addGoal(newP, p);
							}
							case "attack_breakdoor": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide how long it will take to break the door.");
									return false;
								}

								Predicate<Difficulty> diff = (d) -> (d == Difficulty.HARD);

								BreakDoorGoal p = new BreakDoorGoal(target, Integer.parseInt(args[3]), diff);

								target.targetSelector.addGoal(newP, p);
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

								CatLieOnBedGoal p = new CatLieOnBedGoal((Cat) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));

								target.targetSelector.addGoal(newP, p);
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
								
								
								RangedCrossbowAttackGoal p = new RangedCrossbowAttackGoal((Monster) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.targetSelector.addGoal(newP, p);
								break;
							}

							case "ambient_eattile": {
								EatBlockGoal p = new EatBlockGoal(target);

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "core_interact_opendoor": {
								if (args.length < 4) {
									OpenDoorGoal p = new OpenDoorGoal(target, false);

									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									OpenDoorGoal p = new OpenDoorGoal(target, Boolean.parseBoolean(args[3]));

									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "movement_fleesun": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								FleeSunGoal p = new FleeSunGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.targetSelector.addGoal(newP, p);
								break;
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

								FollowMobGoal p = new FollowMobGoal(target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
								target.targetSelector.addGoal(newP, p);
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
									FollowOwnerGoal p = new FollowOwnerGoal((TamableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), false);

									target.targetSelector.addGoal(newP, p);
								} else {
									FollowOwnerGoal p = new FollowOwnerGoal((TamableAnimal) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), Boolean.parseBoolean(args[6]));

									target.targetSelector.addGoal(newP, p);
								}
							}
							case "movement_follow_parent": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								FollowParentGoal p = new FollowParentGoal((Animal) target, Double.parseDouble(args[3]));

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "illager_raid": {
								
								PathfindToRaidGoal<Raider> p = new PathfindToRaidGoal<Raider>((Raider) target);

								target.targetSelector.addGoal(newP, p);
							}
							case "attack_melee": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									MeleeAttackGoal p = new MeleeAttackGoal((PathfinderMob) target, Double.parseDouble(args[3]), false);

									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									MeleeAttackGoal p = new MeleeAttackGoal((PathfinderMob) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "ocelot_attack": {
								OcelotAttackGoal p = new OcelotAttackGoal(target);

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "golem_offer_flower": {
								OfferFlowerGoal p = new OfferFlowerGoal((IronGolem) target);

								target.targetSelector.addGoal(newP, p);
							}
							case "core_panic": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								PanicGoal p = new PanicGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.targetSelector.addGoal(newP, p);
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
									LookAtPlayerGoal p = new LookAtPlayerGoal(target, (Class<? extends net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),false);

									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									LookAtPlayerGoal p = new LookAtPlayerGoal(target, (Class<? extends net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[3].replaceAll("minecraft:", "").toUpperCase())), Float.parseFloat(args[4]), Float.parseFloat(args[5]),Boolean.parseBoolean(args[6]));

									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "core_waterbreathe": {
								BreathAirGoal p = new BreathAirGoal((PathfinderMob) target);

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "animal_breed": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								BreedGoal p = new BreedGoal((Animal) target, Double.parseDouble(args[3]));

								target.targetSelector.addGoal(newP, p);
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
									MoveThroughVillageGoal p = new MoveThroughVillageGoal((PathfinderMob) target, Double.parseDouble(args[3]), false, Integer.parseInt(args[4]), sup);

									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									MoveThroughVillageGoal p = new MoveThroughVillageGoal((PathfinderMob) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[6]), Integer.parseInt(args[4]), sup);

									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "movement_towards_restriction": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								MoveTowardsRestrictionGoal p = new MoveTowardsRestrictionGoal((PathfinderMob) target, Double.parseDouble(args[3]));
								target.targetSelector.addGoal(newP, p);
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

								MoveTowardsTargetGoal p = new MoveTowardsTargetGoal((PathfinderMob) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "movement_nearest_village": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid search interval.");
									return false;
								}

								StrollThroughVillageGoal p = new StrollThroughVillageGoal((PathfinderMob) target, Integer.parseInt(args[3]));

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "dragon_perch": {
								LandOnOwnersShoulderGoal p = new LandOnOwnersShoulderGoal((ShoulderRidingEntity) target);
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "random_fly": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								WaterAvoidingRandomFlyingGoal p = new WaterAvoidingRandomFlyingGoal((PathfinderMob) target, Double.parseDouble(args[3]));

								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "random_lookaround": {
								RandomLookAroundGoal p = new RandomLookAroundGoal(target);

								target.targetSelector.addGoal(newP, p);
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
									RandomStrollGoal p = new RandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), false);
									
									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									RandomStrollGoal p = new RandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]), Boolean.parseBoolean(args[5]));
									
									target.targetSelector.addGoal(newP, p);
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
								
								WaterAvoidingRandomStrollGoal p = new WaterAvoidingRandomStrollGoal((PathfinderMob) target, Double.parseDouble(args[3]), Float.parseFloat(args[4]));
								target.targetSelector.addGoal(newP, p);
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
								
								RandomSwimmingGoal p = new RandomSwimmingGoal((PathfinderMob) target, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
								target.targetSelector.addGoal(newP, p);
								
								break;
							}
							case "movement_restrictsun": {
								RestrictSunGoal p = new RestrictSunGoal((PathfinderMob) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "creeper_swell": {
								SwellGoal p = new SwellGoal((Creeper) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "dolphin_waterjump": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid jumping interval.");
									return false;
								}
								
								DolphinJumpGoal p = new DolphinJumpGoal((Dolphin) target, Integer.parseInt(args[3]));
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "villager_tradeplayer": {
								TradeWithPlayerGoal p = new TradeWithPlayerGoal((AbstractVillager) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "movement_findwater": {
								TryFindWaterGoal p = new TryFindWaterGoal((PathfinderMob) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "zombie_attack": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}

								if (args.length < 5) {
									ZombieAttackGoal p = new ZombieAttackGoal((Zombie) target, Double.parseDouble(args[3]), false);

									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									ZombieAttackGoal p = new ZombieAttackGoal((Zombie) target, Double.parseDouble(args[3]), Boolean.parseBoolean(args[4]));

									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "attack_nearest_target": {
								if (args.length < 4) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid entity type.");
									return false;
								}
								Class<net.minecraft.world.entity.LivingEntity> entityClass = (Class<net.minecraft.world.entity.LivingEntity>)matchClass(EntityType.valueOf(args[3]));
								if (args.length < 5) {
									NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity> p = new NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity>(target, entityClass, true);
									target.targetSelector.addGoal(newP, p);
									break;
								} else {
									NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity> p = new NearestAttackableTargetGoal<net.minecraft.world.entity.LivingEntity>(target, entityClass, Boolean.parseBoolean(args[4]));
									target.targetSelector.addGoal(newP, p);
									break;
								}
							}
							case "attack_defensive": {
								List<Class<net.minecraft.world.entity.LivingEntity>> ignoreList = new ArrayList<>();
								
								for (int i = 3; i < args.length; i++) {
									Class<net.minecraft.world.entity.LivingEntity> entityClass = (Class<net.minecraft.world.entity.LivingEntity>) matchClass(EntityType.valueOf(args[i]));
									
									ignoreList.add(entityClass);
								}
									
								HurtByTargetGoal p = new HurtByTargetGoal((PathfinderMob) target, ignoreList.toArray(new Class<?>[0]));
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "attack_defendvillage": {
								DefendVillageTargetGoal p = new DefendVillageTargetGoal((IronGolem) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "core_float": {
								FloatGoal p = new FloatGoal(target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "fish_school": {
								FollowFlockLeaderGoal p = new FollowFlockLeaderGoal((AbstractSchoolingFish) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "movement_follow_boat": {
								FollowBoatGoal p = new FollowBoatGoal((PathfinderMob) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "cat_sit_block": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								CatSitOnBlockGoal p = new CatSitOnBlockGoal((Cat) target, Double.parseDouble(args[3]));
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "llama_follow": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								LlamaFollowCaravanGoal p = new LlamaFollowCaravanGoal((Llama) target, Double.parseDouble(args[3]));
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "tameable_sit": {
								SitWhenOrderedToGoal p = new SitWhenOrderedToGoal((TamableAnimal) target);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							case "core_tempt": {
								if (args.length < 4) {
									Main.sendValidSpeedModifier(sender);
									return false;
								}
								
								if (args.length < 5) {
									Main.sendPluginMessage(sender, ChatColor.RED + "Please provide valid items.");
									return false;
								}
								
								List<Material> items = new ArrayList<>();
								
								for (int i = 4; i < args.length; i++) {
									items.add(Material.matchMaterial(args[i].toUpperCase()));
								}
								
								List<net.minecraft.world.item.ItemStack> nmsItemStacks = new ArrayList<>();
								
								for (Material m : items) {
									org.bukkit.inventory.ItemStack bukkitstack = new org.bukkit.inventory.ItemStack(m);
									
									net.minecraft.world.item.ItemStack nmsstack = CraftItemStack.asNMSCopy(bukkitstack);
									
									nmsItemStacks.add(nmsstack);
								}
								
								Ingredient recipe = Ingredient.of(nmsItemStacks.stream());
								
								TemptGoal p = new TemptGoal((PathfinderMob) target, Double.parseDouble(args[3]), recipe, true);
								
								target.targetSelector.addGoal(newP, p);
								break;
							}
							default:
								Main.sendPluginMessage(sender, ChatColor.RED + "This pathfinder does not exist or is not supported yet.");
								return false;
            }
            sender.sendMessage(ChatColor.GREEN + "Target Pathfinder Successfully added!");
            return true;
          } catch (ClassCastException e) {
        	  Main.sendPluginMessage(sender, ChatColor.RED + "This target pathfinder is not supported for this entity.");
        	  return false;
          } catch (NumberFormatException | NullPointerException e) {
        	  Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments:\n" + e.getLocalizedMessage());
        	  return false;
          }
        }
        case "remove": {
        	if (!(sender.hasPermission("quantumpen.pathfinder.remove"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
        	try {
                for (WrappedGoal p : getPathfinders(bukkittarget)) {
                	if (p.getPriority() == Integer.parseInt(args[2])) {
                		target.goalSelector.removeGoal(p.getGoal());
                		sender.sendMessage(ChatColor.GREEN + "Pathfinder successfully removed!");
                		break;
                	}
                }
                
        	} catch (NumberFormatException e) {
        		Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the pathfinder priority ID. If you do not know it, use the list function.");
        		return false;
        	}
        	
        	break;
        }
        case "remove_target": {
        	if (!(sender.hasPermission("quantumpen.pathfinder.remove"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
        	try {
                for (WrappedGoal p : getPathfindersTarget(bukkittarget)) {
                	if (p.getPriority() == Integer.parseInt(args[2])) {
                		target.targetSelector.removeGoal(p.getGoal());
                		sender.sendMessage(ChatColor.GREEN + "Target Pathfinder successfully removed!");
                		break;
                	}
                }
                
        	} catch (NumberFormatException e) {
        		Main.sendPluginMessage(sender, ChatColor.RED + "Please provide the pathfinder priority ID. If you do not know it, use the list function.");
        		return false;
        	}
        	
        	break;
        }
        case "list": {
        	if (!(sender.hasPermission("quantumpen.pathfinder.list"))) {
        		Main.sendNoPermission(sender);
        		return false;
        	}
          Map<Integer, String> goals = new HashMap<>();
          
          for (WrappedGoal p : getPathfinders(bukkittarget)) {
        	  goals.put(p.getPriority(), matchGoal(p));
          }
          
          List<String> messages = new ArrayList<>();
          
          goals.forEach((priority, goal) -> {
        	  messages.add(ChatColor.BLUE + "Priority: " + ChatColor.GOLD + Integer.toString(priority) + ChatColor.DARK_AQUA + " | " + ChatColor.BLUE + "Goal Name: " + ChatColor.GOLD + goal);
          });
          
          Map<Integer, String> targetgoals = new HashMap<>();
          
          for (WrappedGoal p : getPathfindersTarget(bukkittarget)) {
        	  targetgoals.put(p.getPriority(), matchGoal(p));
          }
          
          List<String> messages2 = new ArrayList<>();
          
          targetgoals.forEach((priority, goal) -> {
        	  messages2.add(ChatColor.BLUE + "Priority: " + ChatColor.GOLD + Integer.toString(priority) + ChatColor.DARK_AQUA + " | " + ChatColor.BLUE + "Goal Name: " + ChatColor.GOLD + goal);
          });
          
          String msg = ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Entity Goals\n" + String.join("\n", messages) + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "\n\nEntity Target Goals\n" + String.join("\n", messages2);
          
          sender.sendMessage(msg);

          break;
			}
		case "navigation": {
			if (args.length < 3) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a navigation action.");
				return false;
			}
			
			try {
				switch (args[2].toLowerCase().replaceAll("minecraft:", "")) {
					case "movement_goto": {
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
							Main.sendValidSpeedModifier(sender);
							return false;
						}
						
						target.getNavigation().moveTo(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
						break;
					}
					case "ground_canopendoors": {
						if (args.length < 4) {
							Main.sendValidType(sender, ArgumentType.BOOLEAN);
							return false;
						}
						
						((GroundPathNavigation) target.getNavigation()).setCanOpenDoors(Boolean.parseBoolean(args[3]));
						break;
					}
					case "ground_avoidsun": {
						if (args.length < 4) {
							Main.sendValidType(sender, ArgumentType.BOOLEAN);
							return false;
						}
						
						((GroundPathNavigation) target.getNavigation()).setAvoidSun(Boolean.parseBoolean(args[3]));
						break;
					}
					default: {
						Main.sendPluginMessage(sender, ChatColor.RED + "This navigation type does not exist.");
						return false;
					}
				}
			} catch (ClassCastException e) {
				Main.sendPluginMessage(sender, ChatColor.RED + "This navigation type is not supported for this entity.");
				return false;
			}
			sender.sendMessage(ChatColor.GREEN + "Navigation successful!");
			return true;
		}
        default: {
          Main.sendInvalidArgs(sender);
          return false;
        }
      }
    } catch (IllegalArgumentException e) {
      Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments:\n" + e.getLocalizedMessage());
      return false;
    } catch (Exception e) {
		Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
		return false;
	}
	return true;
	}
}