package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import io.github.darealturtywurty.ancientology.core.util.interfaces.ItemProvider;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistryObject<I extends Item> extends WrappedRegistryObject<I> implements ItemProvider {

    ItemRegistryObject(RegistryObject<I> registryObject) {
        super(registryObject);
    }

    @Override
    public Item asItem() {
        return get();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return getId();
    }
}
