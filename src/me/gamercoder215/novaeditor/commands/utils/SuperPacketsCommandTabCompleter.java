package me.gamercoder215.novaeditor.commands.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SuperPacketsCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			List<String> baseCommandList = new ArrayList<>();
	
			baseCommandList.add("info");
			baseCommandList.add("help");
	
			return baseCommandList;
		} else return null;
	}

}
