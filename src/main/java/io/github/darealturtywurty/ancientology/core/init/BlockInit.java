package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;

public final class BlockInit {

    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

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
