package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockstateGenerator extends BlockStateProvider {
    
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ancientology.MODID, exFileHelper);
    }
    
    @Override
    protected void registerStatesAndModels() {
        // simpleBlock(block);
        logBlock(BlockInit.LIFE_LOG.get());
        simpleBlock(BlockInit.LIFE_LEAVES.get());
        simpleBlock(BlockInit.LIFE_PLANKS.get());
        crossBlock(BlockInit.LIFE_SAPLING.get());

        simpleBlock(BlockInit.DEEPSLATE_TIN_ORE.get());
        simpleBlock(BlockInit.TIN_BLOCK.get());
        simpleBlock(BlockInit.BRONZE_BLOCK.get());
    }

    public void crossBlock(Block block) {
        simpleBlock(block, models().cross(block.getRegistryName().getPath(), new ResourceLocation(Ancientology.MODID, ModelProvider.BLOCK_FOLDER + "/" + block.getRegistryName().getPath())));
    }
}
