package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class WrappedRegistryObject<T extends IForgeRegistryEntry<? super T>> implements Supplier<T> {

    protected RegistryObject<T> registryObject;

    protected WrappedRegistryObject(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Nonnull
    @Override
    public T get() {
        return registryObject.get();
    }

    public ResourceLocation getId() {
        return registryObject.getId();
    }

}
