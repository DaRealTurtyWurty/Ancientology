package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public interface Builder<T extends IForgeRegistryEntry<? super T>> {

    /**
     * Builds the object with the properties that were given to it in the builder.
     * 
     * @return A {@link RegistryObject} containing the built object
     */
    RegistryObject<T> build();

}
