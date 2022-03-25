package me.gamercoder215.quantumpen.premium;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.premium.commands.BiomeCommands;

public class PremiumFeatures {
    
	private static PluginCommand createCommand(String name) {
		try {
			Constructor<PluginCommand> p = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			p.setAccessible(true);
			
			return p.newInstance(name, JavaPlugin.getPlugin(QuantumPen.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void registerCommands() {
		try {
			QuantumPen plugin = JavaPlugin.getPlugin(QuantumPen.class);
			Field bukkitmap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitmap.setAccessible(true);

			CommandMap map = (CommandMap) bukkitmap.get(Bukkit.getServer());
			
			PluginCommand setbiome = createCommand("setbiome");
			setbiome.setExecutor(new BiomeCommands(plugin));
			setbiome.setAliases(Arrays.asList("setb", "sbiome", "sb"));

			map.register("setbiome", setbiome);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
