package me.gamercoder215.quantumpen.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import me.gamercoder215.quantumpen.QuantumPen;

public class DisabledCommandCatch implements Listener {
	
	protected QuantumPen plugin;
	
	public DisabledCommandCatch(QuantumPen plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		if (QuantumPen.isCommandDisabled(e.getMessage().replaceAll("/", ""))) {
			e.setCancelled(true);
			QuantumPen.sendCommandDisabled(e.getPlayer());
			return;
		}
	}
	
	@EventHandler
	public void serverCommand(ServerCommandEvent e) {
		if (QuantumPen.isCommandDisabled(e.getCommand().replaceAll("/", ""))) {
			e.setCancelled(true);
			QuantumPen.sendCommandDisabled(e.getSender());
			return;
		}
	}
	
}
