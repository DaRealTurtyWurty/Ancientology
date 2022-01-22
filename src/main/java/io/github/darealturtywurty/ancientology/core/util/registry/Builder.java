package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface Builder<T extends IForgeRegistryEntry<? super T>> extends Supplier<T> {

    /**
     * Builds the object with the properties that were given to it in the builder.
     * 
     * @return A {@link WrappedRegistryObject} containing the built object
     */
    WrappedRegistryObject<T> build();

}
