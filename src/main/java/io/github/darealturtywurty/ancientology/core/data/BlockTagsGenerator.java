package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // tag(blah).addTags(tag1, tag2, etc);
        // tag(blah).add(block1, block2, etc);
    }
}
