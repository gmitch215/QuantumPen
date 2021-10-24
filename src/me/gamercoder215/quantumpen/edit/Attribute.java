package me.gamercoder215.quantumpen.edit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;

public class Attribute implements CommandExecutor {

	protected Main plugin;

	public Attribute(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("attribute").setExecutor(this);
		plugin.getCommand("attribute").setTabCompleter(new CommandTabCompleter());
	}

	@Nullable
	public static AttributeBase matchAttribute(String oldname) {
		String name = oldname.toLowerCase().replaceAll("minecraft:", "");

		if (name.equalsIgnoreCase("generic.max_health")) return GenericAttributes.a;
		else if (name.equalsIgnoreCase("generic.follow_range")) return GenericAttributes.b;
		else if (name.equalsIgnoreCase("generic.knockback_resistance")) return GenericAttributes.c;
		else if (name.equalsIgnoreCase("generic.movement_speed")) return GenericAttributes.d;
		else if (name.equalsIgnoreCase("generic.flying_speed")) return GenericAttributes.e;
		else if (name.equalsIgnoreCase("generic.attack_damage")) return GenericAttributes.f;
		else if (name.equalsIgnoreCase("generic.attack_knockback")) return GenericAttributes.g;
		else if (name.equalsIgnoreCase("generic.attack_speed")) return GenericAttributes.h;
		else if (name.equalsIgnoreCase("generic.armor")) return GenericAttributes.i;
		else if (name.equalsIgnoreCase("generic.armor_toughness")) return GenericAttributes.j;
		else if (name.equalsIgnoreCase("generic.luck")) return GenericAttributes.k;
		else if (name.equalsIgnoreCase("zombie.spawn_reinforcements")) return GenericAttributes.l;
		else if (name.equalsIgnoreCase("horse.jump_strength")) return GenericAttributes.m;
		else return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute action.");
			return false;
		}

		try {
			if (args.length < 2) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
				return false;
			}

			UUID uid = UUID.fromString(args[1]);

			if (Bukkit.getEntity(uid) == null) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
				return false;
			}

			if (!(Bukkit.getEntity(uid) instanceof LivingEntity)) {
				Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid UUID.");
				return false;
			}

			LivingEntity bukkittarget = (LivingEntity) Bukkit.getEntity(uid);
			EntityCreature target = (EntityCreature) ((CraftEntity) bukkittarget).getHandle();

			switch (args[0].toLowerCase()) {
				case "add": {
					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}

					if (matchAttribute(args[2]) == null) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}
					target.getAttributeMap().b(matchAttribute(args[2]));
					if (args.length < 4) {
						target.getAttributeInstance(matchAttribute(args[2])).setValue(0);
						sender.sendMessage(ChatColor.GREEN + "Successfuly registered attribute with base value of 0.");
					} else {
						target.getAttributeInstance(matchAttribute(args[2])).setValue(Double.parseDouble(args[3]));
						sender.sendMessage(ChatColor.GREEN + "Successfuly registered attribute!");
					}
					break;
				}
				case "set": {
					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}

					if (matchAttribute(args[2]) == null) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}

					if (args.length < 4) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid value.");
						return false;
					}

					double newValue = Double.parseDouble(args[3]);

					if (newValue < 0 || newValue > Integer.MAX_VALUE) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid value above 0 and below the integer limit.");
						return false;
					}

					target.getAttributeMap().a(matchAttribute(args[2])).setValue(newValue);
					sender.sendMessage(ChatColor.GREEN + "Successfully set new value to \"" + args[3] + "\".");
					break;
				}
				case "has": {
					String msg = ChatColor.RED + " does not ";

					if (args.length < 3) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}

					if (matchAttribute(args[2]) == null) {
						Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid attribute type.");
						return false;
					}

					if (target.getAttributeMap().getAttributes().contains(target.getAttributeMap().a(matchAttribute(args[2])))) msg = ChatColor.GREEN + " does ";

					sender.sendMessage(ChatColor.GOLD + "This entity" + msg + ChatColor.GOLD + "contain this attribute.");
					break;
				}
				case "list": {
					List<String> attributes = new ArrayList<>();
					DecimalFormat df = new DecimalFormat("###.###");
					for (AttributeModifiable a : target.getAttributeMap().getAttributes()) {
						attributes.add(ChatColor.BLUE + "Name: " + ChatColor.GOLD + a.getAttribute().getName() + ChatColor.BLUE + "\nBase Value: " + ChatColor.GOLD + df.format(a.getBaseValue()) + ChatColor.BLUE + "\nValue: " + ChatColor.GOLD + df.format(a.getValue()));
					}

					sender.sendMessage(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Entity Attributes" + String.join(ChatColor.AQUA + "----------", attributes));
					break;
				}
				default: {
					Main.sendPluginMessage(sender, ChatColor.RED + "This action does not exist.");
					return false;
				}
			}
			return true;
		} catch (IllegalArgumentException e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
			return false;
		}
	}
}