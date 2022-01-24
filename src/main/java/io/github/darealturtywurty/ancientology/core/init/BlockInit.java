package io.github.darealturtywurty.ancientology.core.init;

import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestLevel.STONE;
import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestLevel.WOOD;
import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool.AXE;
import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool.HOE;
import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool.PICKAXE;
import static io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.RenderLayer.CUTOUT;

import java.util.Random;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.LootTableUtils;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockRegistryObject;
import io.github.darealturtywurty.ancientology.core.worldgen.FeatureGen;
import net.minecraftforge.common.Tags;

//@formatter:off
public final class BlockInit {
    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

    public static final BlockRegistryObject<OreBlock> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore",
                    properties -> new OreBlock(properties, UniformInt.of(0, 2)))
            .copyPropertiesFrom(Blocks.DEEPSLATE_COPPER_ORE).requiresCorrectToolForDrops()
            .harvestLevel(STONE).harvestTool(PICKAXE)
            .addTag(Tags.Blocks.ORES, Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Blocks.ORE_RATES_SPARSE)
            .withLootTable(block -> LootTableUtils.createOreDrops(block, ItemInit.RAW_TIN.get(), 2, 3))
            .blockItem(item -> item.lang("Deepslate Tin Ore")).build();

    public static final BlockRegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", Block::new)
            .copyPropertiesFrom(Blocks.IRON_BLOCK)
            .harvestLevel(STONE).harvestTool(PICKAXE)
            .dropSelf().requiresCorrectToolForDrops()
            .shapelessRecipe(1, r -> r.requires(ItemInit.TIN_INGOT, 9))
            .blockItem(item -> item.lang("Block of Tin")).build();

    public static final BlockRegistryObject<RotatedPillarBlock> LIFE_LOG = BLOCKS.register("life_log", RotatedPillarBlock::new)
            .copyPropertiesFrom(Blocks.OAK_LOG)
            .harvestTool(AXE).harvestLevel(WOOD)
            .dropSelf()
            .addTag(BlockTags.LOGS)
            .blockItem(p -> p
                    .lang("Log of Life")
                    .lang(MinecraftLocale.EL_GR, "\u039a\u03bf\u03c1\u03bc\u03cc\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();
    
    public static final BlockRegistryObject<LeavesBlock> LIFE_LEAVES = BLOCKS.register("life_leaves", LeavesBlock::new)
            .copyPropertiesFrom(Blocks.OAK_LEAVES)
            .harvestTool(HOE)
            .withLootTable(block -> LootTableUtils.createCustomLeavesDrops(block, Items.APPLE))
            .addTag(BlockTags.LEAVES)
            .blockItem(p -> p
                    .lang("Leaves of Life")
                    .lang(MinecraftLocale.EL_GR, "\u03a6\u03cd\u03bb\u03bb\u03b1 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();
    
    public static final BlockRegistryObject<Block> LIFE_PLANKS = BLOCKS.register("life_planks", Block::new)
            .copyPropertiesFrom(Blocks.OAK_PLANKS)
            .harvestTool(AXE)
            .dropSelf().shapelessRecipe(4, r -> r.requires(LIFE_LOG))
            .addTag(BlockTags.PLANKS)
            .blockItem(p -> p
                    .lang("Planks of Life")
                    .lang(MinecraftLocale.EL_GR, "\u03a3\u03b1\u03bd\u03af\u03b4\u03b5\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"))
            .build();

    public static final BlockRegistryObject<SaplingBlock> LIFE_SAPLING = BLOCKS.register("life_sapling", properties -> new SaplingBlock(new AbstractTreeGrower() {
                @javax.annotation.Nullable
                @Override
                protected ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
                    return FeatureGen.LIFE_TREE;
                }
            }, properties))
            .copyPropertiesFrom(Blocks.OAK_SAPLING)
            .harvestTool(AXE).harvestLevel(WOOD).dropSelf()
            .addTag(BlockTags.SAPLINGS)
            .renderLayer(CUTOUT)
            .blockItem(p -> p.lang("Sapling of Life"))
            .build();

    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
