package me.gamercoder215.quantumpen.premium.api;

import java.lang.reflect.Field;

import com.mojang.serialization.Lifecycle;

import org.bukkit.Color;

import me.gamercoder215.quantumpen.utils.QuantumKey;
import me.gamercoder215.quantumpen.utils.QuantumUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class QuantumBiome {
    
    private final ResourceLocation key;

    private final boolean snow;
    private final String waterColor;
    private final String fogColor;
    private final String skyColor;
    private final String grassColor;
    private final String foliageColor;

    public QuantumBiome(QuantumKey key, boolean snow, String waterColor, String fogColor, String skyColor, String grassColor, String foliageColor) {
        this.key = key.toNMSLocation();

		this.snow = snow;
		this.waterColor = (waterColor == null ? "3F76E4" : waterColor);
		this.fogColor = (fogColor == null ? "C0D8FF" : fogColor);
		this.skyColor = (skyColor == null ? "78A7FF" : skyColor);
		this.grassColor = grassColor;
		this.foliageColor = (foliageColor == null ? grassColor : foliageColor);
    }

    public void register() throws UnsupportedOperationException {
        WritableRegistry<Biome> registrywritable = (WritableRegistry<Biome>) QuantumUtils.getNMSServer().registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY);
        if (registrywritable.containsKey(this.key)) throw new UnsupportedOperationException("Biome already exists");
   
        ResourceKey<Biome> resourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, this.key);

        ResourceKey<Biome> forestkey = Biomes.FOREST;
        Biome forest = registrywritable.get(forestkey);
        
        try {
            BiomeBuilder builder = new Biome.BiomeBuilder();

            Field biomeSettingMobsField = Biome.class.getDeclaredField("k");
            biomeSettingMobsField.setAccessible(true);
            MobSpawnSettings biomeSettingMobs = (MobSpawnSettings) biomeSettingMobsField.get(forest);
            builder.mobSpawnSettings(biomeSettingMobs);	
            
            Field biomeSettingGenField = Biome.class.getDeclaredField("j");
            biomeSettingGenField.setAccessible(true);
            BiomeGenerationSettings biomeSettingGen = (BiomeGenerationSettings) biomeSettingGenField.get(forest);
            builder.generationSettings(biomeSettingGen);

            builder.downfall(0.8F);
            builder.temperature(0.7F);

			if (snow) builder.temperatureAdjustment(TemperatureModifier.FROZEN);
			else builder.temperatureAdjustment(TemperatureModifier.NONE);

			BiomeSpecialEffects.Builder effectbuilder = new BiomeSpecialEffects.Builder();
			effectbuilder.grassColorModifier(GrassColorModifier.NONE);
			effectbuilder.fogColor(Integer.parseInt(fogColor, 16));
			effectbuilder.waterFogColor(Integer.parseInt(fogColor, 16));
			effectbuilder.skyColor(Integer.parseInt(skyColor, 16));
			effectbuilder.waterColor(Integer.parseInt(waterColor, 16));
			
			effectbuilder.foliageColorOverride(Integer.parseInt(foliageColor, 16));
			effectbuilder.grassColorOverride(Integer.parseInt(grassColor, 16));
	
			builder.specialEffects(effectbuilder.build());

            Biome nmsBiome = builder.build();

            registrywritable.register(resourceKey, nmsBiome, Lifecycle.stable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static final Color fromHex(String hex) {
		return Color.fromRGB(Integer.valueOf(hex, 16));
	}

    public boolean isFrozen() {
        return this.snow;
    }

    public Color getWaterColor() {
        return fromHex(waterColor);
    }

    public Color getFogColor() {
        return fromHex(fogColor);
    }

    public Color getSkyColor() {
        return fromHex(skyColor);
    }

    public Color getFoliageColor() {
        return fromHex(foliageColor);
    }

    public Color getGrassColor() {
        return fromHex(grassColor);
    }

    public ResourceLocation getKey() {
        return this.key;
    }

}
