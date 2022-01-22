package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class DeferredRegisterWrapper<T extends IForgeRegistryEntry<T>> {

    protected final DeferredRegister<T> register;
    protected final String modId;

    protected DeferredRegisterWrapper(final IForgeRegistry<T> registry, String modId) {
        this.register = DeferredRegister.create(registry, modId);
        this.modId = modId;
    }

    public DeferredRegister<T> getRegister() {
        return register;
    }

    public void register(final IEventBus modBus) {
        register.register(modBus);
    }

}
