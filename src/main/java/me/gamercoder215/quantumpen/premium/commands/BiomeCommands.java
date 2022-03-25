package me.gamercoder215.quantumpen.premium.commands;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.premium.api.QuantumBiome;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter.ArgumentType;
import me.gamercoder215.quantumpen.utils.QuantumKey;
import me.gamercoder215.quantumpen.utils.QuantumUtils;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.Biome;

public class BiomeCommands implements CommandExecutor {
    
    protected QuantumPen plugin;

    public BiomeCommands(QuantumPen plugin) {
        this.plugin = plugin;
        changeRegistryLock(false);
    }
    
	public static void changeRegistryLock(boolean isLocked) {
		DedicatedServer server = QuantumUtils.getNMSServer();
        MappedRegistry<Biome> materials = (MappedRegistry<Biome>) server.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY);
        try {
	        Field isFrozen = materials.getClass().getDeclaredField("bL");
	        isFrozen.setAccessible(true);
	        isFrozen.set(materials, isLocked);  
        } catch (Exception e) {
        	JavaPlugin.getPlugin(QuantumPen.class).getLogger().info("Error changing biome lock to " + isLocked);
        	e.printStackTrace();
        }
	}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "createbiome": {
                if (args.length < 1) {
                    QuantumPen.sendError(sender, "Please provide a valid key (Ex: " + sender.getName().toLowerCase() + "/mybiome)");
                    return false;
                }

                if (!(args[0].contains("/"))) {
                    QuantumPen.sendError(sender, "Please provide a valid key (Ex: " + sender.getName().toLowerCase() + "/mybiome)");
                    return false;
                }

                QuantumKey key = new QuantumKey(args[0].split("/")[0], args[0].split("/")[1]);

                if (args.length < 2) {
                    QuantumPen.sendValidType(sender, ArgumentType.BOOLEAN);
                    return false;
                }

                boolean snow = Boolean.parseBoolean(args[1]);

                if (args.length < 3) {
                    QuantumPen.sendError(sender, "Please provide a valid grass color (Hex Code)");
                    return false;
                }

                String grassColor = args[2].replace("#", "");

                String waterColor = (args.length < 4 ? null : args[3].replace("#", ""));
                String foliageColor = (args.length < 5 ? null : args[4].replace("#", ""));
                String skyColor = (args.length < 6 ? null : args[5].replace("#", ""));
                String fogColor = (args.length < 7 ? null : args[6].replace("#", ""));

                QuantumBiome biome = new QuantumBiome(key, snow, waterColor, fogColor, skyColor, grassColor, foliageColor);
                
                try {
                    biome.register();
                    sender.sendMessage(ChatColor.GREEN + "Successfully created biome " + ChatColor.GOLD + args[0].toLowerCase());
                    return true;
                } catch (IllegalArgumentException e) {
                    QuantumPen.sendInvalidArgs(sender);
                    return false;
                } catch (UnsupportedOperationException e) {
                    QuantumPen.sendError(sender, "This biome already exists.");
                    return false;
                }
            }
        }
        
        return true;
    }

    

}
