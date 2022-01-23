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
        defaultBlock(BlockInit.DEEPSLATE_TIN_ORE.getId(), (BlockItem) BlockInit.DEEPSLATE_TIN_ORE.get().asItem());
        defaultItem(ItemInit.RAW_TIN.getId(), ItemInit.RAW_TIN.get());
        defaultItem(ItemInit.TIN_INGOT.getId(), ItemInit.TIN_INGOT.get());
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
