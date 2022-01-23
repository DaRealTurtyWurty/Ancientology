package io.github.darealturtywurty.ancientology.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagsGenerator extends ItemTagsProvider {

    public ItemTagsGenerator(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider,
            ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }

}
