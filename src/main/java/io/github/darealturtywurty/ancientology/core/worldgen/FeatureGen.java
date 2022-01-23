package io.github.darealturtywurty.ancientology.core.worldgen;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FeatureGen {
    public static ConfiguredFeature<TreeConfiguration, ?> LIFE_TREE;
    public static PlacedFeature LIFE_TREE_PLACEMENT;


    public static void registerFeatures() {
        LIFE_TREE = registerTree(Ancientology.MODID, "life_tree", new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.LIFE_LOG.get().defaultBlockState()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(BlockInit.LIFE_LEAVES.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        LIFE_TREE_PLACEMENT = registerTreePlacement(Ancientology.MODID, "life_tree", LIFE_TREE, BlockInit.LIFE_SAPLING.get(), 1000);
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent e) {
        if (e.getName() != null)
            if (e.getName().equals(Biomes.SWAMP.location())) {
                e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> LIFE_TREE_PLACEMENT);
            }
    }

    /**
     * Used to register a configured tree feature to the {@link BuiltinRegistries}
     *
     * @param modid   Your mod's id
     * @param name    The id of your feature
     * @param feature The Tree Feature
     */
    public static ConfiguredFeature<TreeConfiguration, ?> registerTree(String modid, String name, TreeConfiguration feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), Feature.TREE.configured(feature));
    }

    /**
     * Registers a placement for the given tree feature.
     *
     * @param modid   Your mod's id.
     * @param name    The id of your placement.
     * @param feature The tree configured feature.
     * @param sapling The sapling block of your tree.
     * @param chance  The chance that this tree has to spawn.
     */
    public static PlacedFeature registerTreePlacement(String modid, String name, ConfiguredFeature<TreeConfiguration, ?> feature, Block sapling, int chance) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(modid, name),
                feature.placed(
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(sapling.defaultBlockState(), BlockPos.ZERO)),
                        CountPlacement.of(1),
                        RarityFilter.onAverageOnceEvery(chance)));
    }
}
