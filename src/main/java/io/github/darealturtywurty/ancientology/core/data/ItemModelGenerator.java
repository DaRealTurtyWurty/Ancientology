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

    	// Resource Blocks
        defaultBlock(BlockInit.DEEPSLATE_TIN_ORE);
        defaultBlock(BlockInit.TIN_BLOCK);
        defaultBlock(BlockInit.BRONZE_BLOCK);
        
        // Resource Items
        defaultItem(ItemInit.RAW_TIN);
        defaultItem(ItemInit.TIN_INGOT);
        defaultItem(ItemInit.TIN_NUGGET);
        defaultItem(ItemInit.COPPER_NUGGET);
        defaultItem(ItemInit.BRONZE_INGOT);
        defaultItem(ItemInit.BRONZE_NUGGET);
        
        // Flint Tools
        handheldItem(ItemInit.FLINT_AXE);
        handheldItem(ItemInit.FLINT_DAGGER);
        handheldItem(ItemInit.FLINT_HOE);
        handheldItem(ItemInit.FLINT_PICKAXE);
        handheldItem(ItemInit.FLINT_SHOVEL);
        
        // Bone Tools
        handheldItem(ItemInit.BONE_AXE);
        handheldItem(ItemInit.BONE_DAGGER);
        handheldItem(ItemInit.BONE_HOE);
        handheldItem(ItemInit.BONE_PICKAXE);
        handheldItem(ItemInit.BONE_SHOVEL);
        
        // Bronze Tools
        handheldItem(ItemInit.BRONZE_AXE);
        handheldItem(ItemInit.BRONZE_SWORD);
        handheldItem(ItemInit.BRONZE_HOE);
        handheldItem(ItemInit.BRONZE_PICKAXE);
        handheldItem(ItemInit.BRONZE_SHOVEL);
        
        // Bronze Armor
        defaultItem(ItemInit.BRONZE_HELMET);
        defaultItem(ItemInit.BRONZE_CHESTPLATE);
        defaultItem(ItemInit.BRONZE_LEGGINGS);
        defaultItem(ItemInit.BRONZE_BOOTS);
        
        // Bone Armor
        dyeableItem(ItemInit.BONE_HELMET);
        dyeableItem(ItemInit.BONE_CHESTPLATE);
        dyeableItem(ItemInit.BONE_LEGGINGS);
        dyeableItem(ItemInit.BONE_BOOTS);
        

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
    
    private void dyeableItem(ItemProvider item) {
        withExistingParent(item.getRegistryName().getPath(), "item/generated").texture("layer0",
                new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath()))
                .texture("layer1", new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath() + "_overlay"));
    }
    
    private void handheldItem(ItemProvider item) {
        withExistingParent(item.getRegistryName().getPath(), "item/handheld").texture("layer0",
                new ResourceLocation(item.getRegistryName().getNamespace(), "item/" + item.getRegistryName().getPath()));
    }
}
