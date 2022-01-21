package io.github.darealturtywurty.ancientology.core.init;

import net.minecraft.world.item.CreativeModeTab;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.blocks.TestBlock;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestLevel;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockDeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class BlockInit {

    public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS);

    public static final RegistryObject<TestBlock> TEST = BLOCKS.register("test", TestBlock::new)
            .harvestLevel(HarvestLevel.DIAMOND).harvestTool(HarvestTool.PICKAXE)
            .blockItem(item -> item.tab(CreativeModeTab.TAB_BREWING).lang(" test item yes")).build();

    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
