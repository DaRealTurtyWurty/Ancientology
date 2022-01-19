package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockstateGenerator extends BlockStateProvider {
    
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ancientology.MODID, exFileHelper);
    }
    
    @Override
    protected void registerStatesAndModels() {
        // simpleBlock(block);
    }
}
