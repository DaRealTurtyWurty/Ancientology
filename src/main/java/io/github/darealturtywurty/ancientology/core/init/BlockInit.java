package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.LootTableUtils;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockRegistryObject;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.common.Tags;

public final class BlockInit {
    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

    public static final BlockRegistryObject<OreBlock> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore",
                    input -> new OreBlock(input, UniformInt.of(0, 2))).copyPropertiesFrom(Blocks.COPPER_ORE)
            .harvestLevel(BlockBuilder.HarvestLevel.STONE).harvestTool(BlockBuilder.HarvestTool.PICKAXE)
            .addTag(Tags.Blocks.ORES, Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Blocks.ORE_RATES_SPARSE)
            .withLootTable(block -> LootTableUtils.createOreDrops(block, ItemInit.RAW_TIN.get(), 2, 3))
            .blockItem(item -> item.lang("Deepslate Tin Ore")).build();

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

    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
