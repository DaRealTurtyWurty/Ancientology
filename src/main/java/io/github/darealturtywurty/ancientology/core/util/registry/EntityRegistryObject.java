package io.github.darealturtywurty.ancientology.core.util.registry;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import io.github.darealturtywurty.ancientology.core.util.interfaces.EntityTypeProvider;
import io.github.darealturtywurty.ancientology.core.util.interfaces.ItemProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistryObject<E extends Entity> extends WrappedRegistryObject<EntityType<E>>
        implements EntityTypeProvider, ItemProvider {

    @Nullable
    private final RegistryObject<ForgeSpawnEggItem> spawnEgg;

    EntityRegistryObject(final RegistryObject<EntityType<E>> registryObject,
            @Nullable final RegistryObject<ForgeSpawnEggItem> spawnEgg) {
        super(registryObject);
        this.spawnEgg = spawnEgg;
    }

    @Override
    public Item asItem() {
        if (spawnEgg == null) { return null; }
        return spawnEgg.get();
    }

    @Override
    public EntityType<?> asEntityType() {
        return registryObject.get();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return EntityTypeProvider.super.getRegistryName();
    }

    @Override
    public String getTranslationKey() {
        return EntityTypeProvider.super.getTranslationKey();
    }

}
