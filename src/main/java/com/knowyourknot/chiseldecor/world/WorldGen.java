package com.knowyourknot.chiseldecor.world;

import com.knowyourknot.chiseldecor.Ref;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class WorldGen {
    private WorldGen() {}

    public static void addStoneWorldGen(Block stoneBlock) {
        String blockName = Registry.BLOCK.getId(stoneBlock).getPath();
        ConfiguredFeature<?, ?> featureOreOverworld = Feature.ORE
            .configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, stoneBlock.getDefaultState(), 15))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 64)))
            .spreadHorizontally()
            .repeat(15);

        RegistryKey<ConfiguredFeature<?, ?>> oreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(Ref.MOD_ID, "ore_" + blockName + "_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreOverworld.getValue(), featureOreOverworld);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreOverworld);
    }
}
