package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public interface Builder<T extends IForgeRegistryEntry<? super T>> {

    RegistryObject<T> build();

}
