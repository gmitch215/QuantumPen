package me.gamercoder215.quantumpen.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import net.minecraft.resources.ResourceLocation;

public class QuantumKey {

    private final String namespace;
    private final String value;

    public QuantumKey(String namespace, String value) {
        this.namespace = namespace;
        this.value = value;
    }

    public ResourceLocation toNMSLocation() {
        return new ResourceLocation(namespace, value);
    }

    /**
     * Will
     * @throws IllegalArgumentException if Bukkit.getPlugin(namespace) == null
     * @return
     */
    public NamespacedKey toBukkitKey() throws IllegalArgumentException {
        return new NamespacedKey(Bukkit.getPluginManager().getPlugin(namespace), value);
    }

}
