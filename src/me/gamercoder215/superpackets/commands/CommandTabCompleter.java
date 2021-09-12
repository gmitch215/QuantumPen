package me.gamercoder215.superpackets.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandTabCompleter implements TabCompleter {
	
	public static List<String> getPacketList() {
		List<String> packetList = new ArrayList<>();
		
		packetList.add("spawn_entity");
		packetList.add("spawn_experience_orb");
		packetList.add("spawn_painting");
		packetList.add("block_break_animation");
		
		List<String> actualPacketList = new ArrayList<>();
		
		for (String s : packetList) {
			actualPacketList.add("minecraft:" + s);
		}
		
		String[] newPacketList = (String[]) actualPacketList.toArray();
		Arrays.sort(newPacketList);
		
		return (Arrays.asList(newPacketList));
 	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clientpacket")) {
			if (args.length < 1) {
				List<String> playersOnline = new ArrayList<>();
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					playersOnline.add(p.getName());
				}
				
				return playersOnline;
			} else if (args.length == 1) {
				return getPacketList();
			} else if (args.length >= 2) {
				String packet = args[1];
				
				switch (packet.replaceAll("minecraft:", "")) {
					case "spawn_entity":
						if (args.length == 2) {
							List<String> entities = new ArrayList<>();
							
							for (EntityType t : EntityType.values()) {
								entities.add("minecraft:" + t.name().toLowerCase());
							}
						}
						
						break;
				}
			}
		}
		
		return null;
	}

}
