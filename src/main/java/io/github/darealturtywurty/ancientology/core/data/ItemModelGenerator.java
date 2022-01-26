package io.github.darealturtywurty.ancientology.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.util.interfaces.BlockProvider;
import io.github.darealturtywurty.ancientology.core.util.interfaces.ItemProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        defaultBlock(BlockInit.LIFE_LOG);
        defaultBlock(BlockInit.LIFE_LEAVES);
        defaultBlock(BlockInit.LIFE_PLANKS);
        blockItemWithBlockModel(BlockInit.LIFE_SAPLING);
        defaultItem(ItemInit.FORBIDDEN_FRUIT);

        defaultBlock(BlockInit.DEEPSLATE_TIN_ORE);
        defaultBlock(BlockInit.TIN_BLOCK);
        defaultItem(ItemInit.RAW_TIN);
        defaultItem(ItemInit.TIN_INGOT);

        defaultBlockItem(BlockInit.JUMPRASHER);
    }

    /**
     * For blocks such as saplings and torches.
     */
    private void blockItemWithBlockModel(final BlockProvider block) {
        final var regName = block.getRegistryName();
        withExistingParent(regName.getPath(), "item/generated").texture("layer0",
                new ResourceLocation(regName.getNamespace(), "block/" + regName.getPath()));
    }

    private void defaultBlockItem(BlockProvider block) {
        final var regName = block.getRegistryName();
        withExistingParent(regName.getPath(), regName.getNamespace() + ":block/" + regName.getPath());
    }

    private void defaultBlock(BlockProvider block) {
        getBuilder(block.getRegistryName().getPath())
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(block.getRegistryName().getNamespace(),
                        "block/" + block.getRegistryName().getPath())));
    }

    private void defaultItem(ItemProvider item) {
        withExistingParent(item.getRegistryName().getPath(), "item/generated").texture("layer0", new ResourceLocation(
                item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath()));
    }
}
