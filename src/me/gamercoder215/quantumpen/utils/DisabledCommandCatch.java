package me.gamercoder215.quantumpen.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import me.gamercoder215.quantumpen.Main;

public class DisabledCommandCatch implements Listener {
	
	protected Main plugin;
	
	public DisabledCommandCatch(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		if (Main.isCommandDisabled(plugin, e.getMessage().replaceAll("/", ""))) {
			e.setCancelled(true);
			Main.sendCommandDisabled(e.getPlayer());
			return;
		}
	}
	
	@EventHandler
	public void serverCommand(ServerCommandEvent e) {
		if (Main.isCommandDisabled(plugin, e.getCommand().replaceAll("/", ""))) {
			e.setCancelled(true);
			Main.sendCommandDisabled(e.getSender());
			return;
		}
	}
	
}
