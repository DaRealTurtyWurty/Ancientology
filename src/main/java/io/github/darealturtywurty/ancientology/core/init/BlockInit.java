package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.LootTableUtils;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockRegistryObject;
import io.github.darealturtywurty.ancientology.core.worldgen.FeatureGen;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraftforge.common.Tags;

public final class BlockInit {
    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

    public static final BlockRegistryObject<OreBlock> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore",
                    input -> new OreBlock(input, UniformInt.of(0, 2))).copyPropertiesFrom(Blocks.DEEPSLATE_COPPER_ORE)
            .harvestLevel(BlockBuilder.HarvestLevel.STONE).harvestTool(BlockBuilder.HarvestTool.PICKAXE)
            .addTag(Tags.Blocks.ORES, Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Blocks.ORE_RATES_SPARSE)
            .withLootTable(block -> LootTableUtils.createOreDrops(block, ItemInit.RAW_TIN.get(), 2, 3))
            .blockItem(item -> item.lang("Deepslate Tin Ore")).build();

    public static final BlockRegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", Block::new)
            .copyPropertiesFrom(Blocks.IRON_BLOCK).harvestLevel(BlockBuilder.HarvestLevel.STONE).harvestTool(BlockBuilder.HarvestTool.PICKAXE)
            .dropSelf().shapelessRecipe(1, r -> r.requires(ItemInit.TIN_INGOT, 9)).blockItem(item -> item.lang("Block of Tin")).build();

    /*
     * public static final BlockRegistryObject<OreBlock> TEST = BLOCKS
     * .register("test", p -> new OreBlock(p, UniformInt.of(12,
     * 18))).copyPropertiesFrom(Blocks.IRON_ORE)
     * .renderLayer(RenderLayer.CUTOUT).harvestLevel(HarvestLevel.DIAMOND).
     * harvestTool(HarvestTool.PICKAXE) .dropSelf() .blockItem(item ->
     * item.tab(CreativeModeTab.TAB_BREWING) .shapelessRecipe(6, r ->
     * r.requires(Items.ACACIA_WOOD, 5)).shapedRecipe(12, r -> r.pattern("J ")
     * .pattern("ZZ").define('Z', Items.ACACIA_BOAT).define('J',
     * Items.ACACIA_FENCE)) .lang(" test item yes")) .build();
     */

    public static final BlockRegistryObject<RotatedPillarBlock> LIFE_LOG = BLOCKS.register("life_log", RotatedPillarBlock::new)
            .copyPropertiesFrom(Blocks.OAK_LOG)
            .harvestTool(BlockBuilder.HarvestTool.AXE)
            .dropSelf()
            .addTag(BlockTags.LOGS)
            .blockItem(p -> p
                    .lang("Log of Life")
                    .lang(MinecraftLocale.EL_GR, "\u039a\u03bf\u03c1\u03bc\u03cc\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();
    public static final BlockRegistryObject<LeavesBlock> LIFE_LEAVES = BLOCKS.register("life_leaves", LeavesBlock::new)
            .copyPropertiesFrom(Blocks.OAK_LEAVES)
            .harvestTool(BlockBuilder.HarvestTool.HOE)
            .withLootTable(block -> LootTableUtils.createCustomLeavesDrops(block, Items.APPLE))
            .addTag(BlockTags.LEAVES)
            .blockItem(p -> p
                    .lang("Leaves of Life")
                    .lang(MinecraftLocale.EL_GR, "\u03a6\u03cd\u03bb\u03bb\u03b1 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();
    public static final BlockRegistryObject<Block> LIFE_PLANKS = BLOCKS.register("life_planks", Block::new)
            .copyPropertiesFrom(Blocks.OAK_PLANKS)
            .harvestTool(BlockBuilder.HarvestTool.AXE)
            .dropSelf()
            .addTag(BlockTags.PLANKS)
            .blockItem(p -> p
                    .lang("Planks of Life")
                    .lang(MinecraftLocale.EL_GR, "\u03a3\u03b1\u03bd\u03af\u03b4\u03b5\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();

    public static final BlockRegistryObject<SaplingBlock> LIFE_SAPLING = BLOCKS.register("life_sapling", input -> new SaplingBlock(new AbstractTreeGrower() {
                @Nullable
                @Override
                protected ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
                    return FeatureGen.LIFE_TREE;
                }
            }, input))
            .copyPropertiesFrom(Blocks.OAK_SAPLING)
            .harvestTool(BlockBuilder.HarvestTool.AXE)
            .dropSelf()
            .addTag(BlockTags.SAPLINGS)
            .renderLayer(BlockBuilder.RenderLayer.CUTOUT)
            .blockItem(p -> p
                    .lang("Sapling of Life")
                    //.lang(MinecraftLocale.EL_GR, "\u03a3\u03b1\u03bd\u03af\u03b4\u03b5\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2")
            )
            .build();

    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
