package me.gamercoder215.quantumpen.misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gamercoder215.quantumpen.Main;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;
import me.gamercoder215.quantumpen.utils.QuantumUtils;

public class Pen implements CommandExecutor {
	protected Main plugin;

	public Pen(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("pen").setExecutor(this);
		plugin.getCommand("pen").setTabCompleter(new CommandTabCompleter());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid misc action.");
			return false;
		}

		switch (args[0].toLowerCase()) {
			case "calculate": {
				if (args.length < 2) {
					Main.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid equation to evaluate.");
					return false;
				}

				List<String> arguments = new ArrayList<>();

				for (int i = 1; i < args.length; i++) {
					arguments.add(args[i]);
				}

				String equation = String.join(" ", arguments);
				DecimalFormat df = new DecimalFormat("###.#####");
				try {
					double answer = QuantumUtils.solveEquation(equation);
					
					if ((answer == Math.floor(answer)) && !Double.isInfinite(answer)) {
					    int answerInt = (int) answer;
					    Main.sendPluginMessage(sender, "= " + Integer.toString(answerInt));
					} else Main.sendPluginMessage(sender, "= " + df.format(answer));
				} catch (RuntimeException e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "There was an error executing the equation:\n" + e.getLocalizedMessage());
					return false;
				} catch (Exception e) {
					Main.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
					return false;
				}
				break;
			}
		}
		return true;
	}
}