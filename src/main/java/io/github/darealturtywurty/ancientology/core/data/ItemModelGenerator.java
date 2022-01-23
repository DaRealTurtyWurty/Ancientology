package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("unused")
public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // defaultBlock(name, blockitem);
        // defaultItem(name, item);
        defaultBlock(BlockInit.LIFE_LOG.getRegistryName(), (BlockItem) BlockInit.LIFE_LOG.get().asItem());
        defaultBlock(BlockInit.LIFE_LEAVES.getRegistryName(), (BlockItem) BlockInit.LIFE_LEAVES.get().asItem());
        defaultBlock(BlockInit.LIFE_PLANKS.getRegistryName(), (BlockItem) BlockInit.LIFE_PLANKS.get().asItem());
        defaultItem(BlockInit.LIFE_SAPLING.getRegistryName(), BlockInit.LIFE_SAPLING.get().asItem());
        defaultItem(ItemInit.FORBIDDEN_FRUIT.getRegistryName(), ItemInit.FORBIDDEN_FRUIT.get());
    }

    private void defaultBlock(ResourceLocation id, BlockItem item) {
        getBuilder(id.getPath()).parent(
                new ModelFile.UncheckedModelFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath())));
    }

    private void defaultItem(ResourceLocation id, Item item) {
        withExistingParent(id.getPath(), "item/generated").texture("layer0",
                new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
    }
}
