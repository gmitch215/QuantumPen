package me.gamercoder215.superpackets.commands;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.bukkit.command.CommandExecutor;

public class SuperPackets implements CommandExecutor {
	
	public SuperPackets(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("superpackets").setExecutor(this);
		plugin.getCommand("superpackets").setTabCompleter(new CommandTabCompleter());
	}

	private static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

  private static String sendGETRequest(String url) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .GET()
          .uri(URI.create(url))
          .setHeader("User-Agent", "Java 11 HttpClient Bot")
          .build();
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      
      if (response.statusCode() == 200) {
        return response.body();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
      Main.sendInvalidArgs(sender);
      return false;
    }

    switch (args[0].toLowerCase()) {
      case "info":
        sender.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.DARK_GREEN + "SuperPackets by GamerCoder215\n\n" + ChatColor.GOLD + "Version v" + plugin.getDescription().getVersion());
				break;
			
    }

    return true;
	}
}
