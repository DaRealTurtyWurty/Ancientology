package io.github.darealturtywurty.ancientology.core.data;

import java.util.function.Supplier;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagsGenerator extends ItemTagsProvider {

    public ItemTagsGenerator(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider,
            ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ItemInit.ITEMS.getTags()
                .forEach((tag, items) -> tag(tag).add(items.stream().map(Supplier::get).toArray(Item[]::new)));
    }

}
