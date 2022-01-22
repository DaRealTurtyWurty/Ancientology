package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityDeferredRegister extends DeferredRegisterWrapper<EntityType<?>> {

    final List<EntityBuilder<?>> builders = new ArrayList<>();

    final ItemDeferredRegister itemRegister;
    private final boolean isItemRegisterManual;

    private EntityDeferredRegister(String modId) {
        super(ForgeRegistries.ENTITIES, modId);
        this.itemRegister = ItemDeferredRegister.create(modId);
        this.isItemRegisterManual = false;
    }

    private EntityDeferredRegister(String modid, ItemDeferredRegister itemDeferredRegister) {
        super(ForgeRegistries.ENTITIES, modid);
        this.itemRegister = itemDeferredRegister;
        this.isItemRegisterManual = true;
    }

    public static EntityDeferredRegister create(String modid) {
        return new EntityDeferredRegister(modid);
    }

    public static EntityDeferredRegister create(String modid, ItemDeferredRegister itemDeferredRegister) {
        return new EntityDeferredRegister(modid, itemDeferredRegister);
    }

    public <E extends Entity> EntityBuilder<E> register(final String name, final EntityFactory<E> factory) {
        return new EntityBuilder<>(name, factory, this);
    }

    @Override
    public void addDatagen(GatherDataEvent event) {
        if (!isItemRegisterManual) {
            itemRegister.addDatagen(event);
        }
    }

    @Override
    public void register(IEventBus modBus) {
        super.register(modBus);
        if (!isItemRegisterManual) {
            itemRegister.register(modBus);
        }
    }


}
