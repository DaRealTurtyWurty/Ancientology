package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockRegistryObject;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        defaultBlock(BlockInit.LIFE_LOG.getRegistryName(), (BlockItem) BlockInit.LIFE_LOG.get().asItem());
        defaultBlock(BlockInit.LIFE_LEAVES.getRegistryName(), (BlockItem) BlockInit.LIFE_LEAVES.get().asItem());
        defaultBlock(BlockInit.LIFE_PLANKS.getRegistryName(), (BlockItem) BlockInit.LIFE_PLANKS.get().asItem());
        defaultItem(BlockInit.LIFE_SAPLING.getRegistryName(), BlockInit.LIFE_SAPLING.get().asItem());
        defaultItem(ItemInit.FORBIDDEN_FRUIT.getRegistryName(), ItemInit.FORBIDDEN_FRUIT.get());
        defaultBlock(BlockInit.DEEPSLATE_TIN_ORE);
        defaultBlock(BlockInit.TIN_BLOCK);
        defaultItem(ItemInit.RAW_TIN);
        defaultItem(ItemInit.TIN_INGOT);
    }

    private void defaultBlock(BlockRegistryObject<?> reg) {
        getBuilder(reg.getId().getPath()).parent(
                new ModelFile.UncheckedModelFile(new ResourceLocation(reg.getId().getNamespace(), "block/" + reg.getId().getPath())));
    }

    private void defaultItem(ItemRegistryObject<?> reg) {
        withExistingParent(reg.getId().getPath(), "item/generated").texture("layer0",
                new ResourceLocation(reg.getId().getNamespace(), "item/" + reg.getId().getPath()));
    }
}
