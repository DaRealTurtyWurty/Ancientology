package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;

public final class BlockInit {

    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

    /*
     * public static final RegistryObject<TestBlock> TEST = BLOCKS.register("test",
     * TestBlock::new)
     * .harvestLevel(HarvestLevel.DIAMOND).harvestTool(HarvestTool.PICKAXE).dropSelf
     * () .blockItem(item ->
     * item.tab(CreativeModeTab.TAB_BREWING).lang(" test item yes")).build();
     */

    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
