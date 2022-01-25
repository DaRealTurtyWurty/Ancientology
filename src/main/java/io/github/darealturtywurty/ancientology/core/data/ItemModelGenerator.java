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

@SuppressWarnings("unused")
public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ancientology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
        
    }

    private void defaultBlock(BlockRegistryObject<?> reg) {
        getBuilder(reg.getId().getPath()).parent(
                new ModelFile.UncheckedModelFile(new ResourceLocation(reg.getId().getNamespace(), "block/" + reg.getId().getPath())));
    }

    private void defaultItem(ItemRegistryObject<?> reg) {
        withExistingParent(reg.getId().getPath(), "item/generated").texture("layer0",
                new ResourceLocation(reg.getId().getNamespace(), "item/" + reg.getId().getPath()));
    }
    
    private void dyeableItem(ItemRegistryObject<?> reg) {
        withExistingParent(reg.getId().getPath(), "item/generated").texture("layer0",
                new ResourceLocation(reg.getId().getNamespace(), "item/" + reg.getId().getPath()))
                .texture("layer1", new ResourceLocation(reg.getId().getNamespace(), "item/" + reg.getId().getPath() + "_overlay"));
    }
    
    private void handheldItem(ItemRegistryObject<?> reg) {
        withExistingParent(reg.getId().getPath(), "item/handheld").texture("layer0",
                new ResourceLocation(reg.getId().getNamespace(), "item/" + reg.getId().getPath()));
    }
}
