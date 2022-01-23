package io.github.darealturtywurty.ancientology.core.worldgen;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.data.worldgen.placement.OrePlacements.commonOrePlacement;

@Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGeneration {
    public static final List<PlacedFeature> OVERWORLD_ORES = new ArrayList<>();

    //This feels like jank... but IDK what's jank about it + it works
    public static void registerOres() {
        //TODO Possible config for change of ore generation
        ConfiguredFeature<OreConfiguration, ?> tinOre = FeatureUtils.register("tin_ore",
                Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(
                        OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.DEEPSLATE_TIN_ORE.get().defaultBlockState())), 12)));

        PlacedFeature tinPlacement = tinOre.placed(commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(-2))));
        OVERWORLD_ORES.add(tinPlacement);
    }

    @SubscribeEvent
    public static void biomeLoading(BiomeLoadingEvent event) {
        List<Supplier<PlacedFeature>> features = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        if (event.getCategory() != Biome.BiomeCategory.THEEND || event.getCategory() != Biome.BiomeCategory.NETHER) {
            OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
        }
    }
}
