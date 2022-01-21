package io.github.darealturtywurty.ancientology.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {

    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}
