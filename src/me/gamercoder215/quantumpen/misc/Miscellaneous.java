public class Miscellaneous implements CommandExecutor {
	protected Main plugin;

	public Miscellaneous(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("misc").setExecutor(this);
		plugin.getCommand("misc").setTabCompleter(new CommandTabCompleter());
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

				String equation = String.join("\\s", arguments);

				if (QuantumPen.solveEquation(equation) == null) {
					Main.sendPluginMessage(sender, ChatColor.RED + "There was an error evaluating the equation.");
					return false;
				}

				double answer = QuantumPen.solveEquation(equation).doubleValue();

				Main.sendPluginMessage(sender, "= " + Double.toString(answer));
				break;
			}
		}
	}
}