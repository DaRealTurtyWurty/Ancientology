package io.github.darealturtywurty.ancientology.core.data;

import java.util.function.Supplier;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Block;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {

    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        BlockInit.BLOCKS.getHarvestTools().forEach((tool, blocks) -> tag(tool.getTag())
                .add(blocks.stream().map(Supplier::get).toArray(Block[]::new)));
        BlockInit.BLOCKS.getHarvestLevels().forEach((level, blocks) -> tag(level.getTag())
                .add(blocks.stream().map(Supplier::get).toArray(Block[]::new)));
    }
}
