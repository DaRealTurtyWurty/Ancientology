package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockstateGenerator extends BlockStateProvider {
    
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ancientology.MODID, exFileHelper);
    }
    
    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockInit.DEEPSLATE_TIN_ORE.get());
        simpleBlock(BlockInit.TIN_BLOCK.get());
        simpleBlock(BlockInit.BRONZE_BLOCK.get());
    }
}
