package io.github.darealturtywurty.ancientology.core.init;

import java.util.Random;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.blocks.JumprasherBlock;
import io.github.darealturtywurty.ancientology.core.worldgen.FeatureGen;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Ancientology.MODID);
    
    public static final RegistryObject<OreBlock> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore",
            () -> new OreBlock(
                    BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE).requiresCorrectToolForDrops(),
                    UniformInt.of(0, 2)));
    
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));
    
    public static final RegistryObject<RotatedPillarBlock> LIFE_LOG = BLOCKS.register("life_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    
    public static final RegistryObject<LeavesBlock> LIFE_LEAVES = BLOCKS.register("life_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    
    public static final RegistryObject<Block> LIFE_PLANKS = BLOCKS.register("life_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    
    public static final RegistryObject<SaplingBlock> LIFE_SAPLING = BLOCKS.register("life_sapling",
            () -> new SaplingBlock(new AbstractTreeGrower() {
                @Override
                protected ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
                    return FeatureGen.LIFE_TREE;
                }
            }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    
    public static final RegistryObject<JumprasherBlock> JUMPRASHER = BLOCKS.register("jumprasher",
            () -> new JumprasherBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()));
    
    /*
     * .requiresCorrectToolForDrops() .harvestLevel(STONE).harvestTool(PICKAXE)
     * .addTag(Tags.Blocks.ORES, Tags.Blocks.ORES_IN_GROUND_DEEPSLATE,
     * Tags.Blocks.ORE_RATES_SPARSE) .withLootTable(block ->
     * LootTableUtils.createOreDrops(block, ItemInit.RAW_TIN.get(), 2, 3))
     * .blockItem(item -> item.lang("Deepslate Tin Ore")).build();
     */
    
    /*
     * public static final BlockRegistryObject<Block> TIN_BLOCK =
     * BLOCKS.register("tin_block", Block::new)
     * .copyPropertiesFrom(Blocks.IRON_BLOCK)
     * .harvestLevel(STONE).harvestTool(PICKAXE)
     * .dropSelf().requiresCorrectToolForDrops() .shapelessRecipe(1, r ->
     * r.requires(ItemInit.TIN_INGOT, 9)) .blockItem(item ->
     * item.lang("Block of Tin")).build();
     */
    
    /*
     * public static final BlockRegistryObject<Block> BRONZE_BLOCK =
     * BLOCKS.register("bronze_block", Block::new)
     * .copyPropertiesFrom(Blocks.IRON_BLOCK)
     * .harvestLevel(STONE).harvestTool(PICKAXE)
     * .dropSelf().requiresCorrectToolForDrops() .shapelessRecipe(1, r ->
     * r.requires(ItemInit.BRONZE_INGOT, 9)) .blockItem(item ->
     * item.lang("Block of Bronze")).build();
     */
    
    /*
     * public static final BlockRegistryObject<RotatedPillarBlock> LIFE_LOG =
     * BLOCKS.register("life_log", RotatedPillarBlock::new)
     * .copyPropertiesFrom(Blocks.OAK_LOG) .harvestTool(AXE).harvestLevel(WOOD)
     * .dropSelf() .addTag(BlockTags.LOGS) .blockItem(p -> p .lang("Log of Life")
     * .lang(MinecraftLocale.EL_GR,
     * "\u039a\u03bf\u03c1\u03bc\u03cc\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"
     * )) .build();
     */
    
    /*
     * public static final BlockRegistryObject<LeavesBlock> LIFE_LEAVES =
     * BLOCKS.register("life_leaves", LeavesBlock::new)
     * .copyPropertiesFrom(Blocks.OAK_LEAVES) .harvestTool(HOE) .withLootTable(block
     * -> LootTableUtils.createCustomLeavesDrops(block, Items.APPLE))
     * .addTag(BlockTags.LEAVES) .blockItem(p -> p .lang("Leaves of Life")
     * .lang(MinecraftLocale.EL_GR,
     * "\u03a6\u03cd\u03bb\u03bb\u03b1 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"
     * )) .build(); public static final BlockRegistryObject<Block> LIFE_PLANKS =
     * BLOCKS.register("life_planks", Block::new)
     * .copyPropertiesFrom(Blocks.OAK_PLANKS) .harvestTool(AXE)
     * .dropSelf().shapelessRecipe(4, r -> r.requires(LIFE_LOG))
     * .addTag(BlockTags.PLANKS) .blockItem(p -> p .lang("Planks of Life")
     * .lang(MinecraftLocale.EL_GR,
     * "\u03a3\u03b1\u03bd\u03af\u03b4\u03b5\u03c2 \u03c4\u03b7\u03c2 \u0396\u03c9\u03ae\u03c2"
     * )) .build();
     */
    
    /*
     * public static final BlockRegistryObject<SaplingBlock> LIFE_SAPLING =
     * BLOCKS.register("life_sapling", properties -> new SaplingBlock(new
     * AbstractTreeGrower() {
     * @javax.annotation.Nullable
     * @Override protected ConfiguredFeature<?, ?> getConfiguredFeature(Random
     * pRandom, boolean pLargeHive) { return FeatureGen.LIFE_TREE; } }, properties))
     * .copyPropertiesFrom(Blocks.OAK_SAPLING)
     * .harvestTool(AXE).harvestLevel(WOOD).dropSelf() .addTag(BlockTags.SAPLINGS)
     * .renderLayer(CUTOUT) .blockItem(p -> p.lang("Sapling of Life")) .build();
     */
    
    /*
     * public static final BlockRegistryObject<JumprasherBlock> JUMPRASHER =
     * BLOCKS.register("jumprasher", p -> new JumprasherBlock(p))
     * .harvestLevel(IRON).harvestTool(AXE).dropSelf() .blockItem(i ->
     * i.lang("Jumprasher")) .renderLayer(RenderLayer.CUTOUT_MIPPED).noOcclusion()
     * .build();
     */
    
    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
