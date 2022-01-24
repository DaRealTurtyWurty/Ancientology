package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import io.github.darealturtywurty.ancientology.core.util.interfaces.BlockProvider;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistryObject<B extends Block> extends WrappedRegistryObject<B> implements BlockProvider {

    BlockRegistryObject(RegistryObject<B> registryObject) {
        super(registryObject);
    }

    @Override
    public Item asItem() {
        return get().asItem();
    }

    @Override
    public Block asBlock() {
        return get();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return getId();
    }
}
