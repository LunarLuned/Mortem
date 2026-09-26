package net.lunarluned.mortem.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.lunarluned.mortem.world.features.NetherWartPitFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Predicate;

public class MortemFeatures {

    public static final Feature<NoneFeatureConfiguration> NETHER_WART_PIT = Registry.register(
            BuiltInRegistries.FEATURE,
            Identifier.fromNamespaceAndPath("mortem", "nether_wart_pit"),
            new NetherWartPitFeature(NoneFeatureConfiguration.CODEC));

    public static void registerFeatures() {
        addFeature("nether_wart_pit",
                BiomeSelectors.includeByKey(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS);
    }

    private static void addFeature(String featureName, Predicate<BiomeSelectionContext> selector,
                                   GenerationStep.Decoration step) {
        BiomeModifications.addFeature(
                selector,
                step,
                ResourceKey.create(
                        Registries.PLACED_FEATURE,
                        Identifier.fromNamespaceAndPath("mortem", featureName)
                )
        );
    }
}
