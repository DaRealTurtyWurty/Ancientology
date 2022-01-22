package io.github.darealturtywurty.ancientology.core.util.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public interface EntityTypeProvider extends Provider {

    /**
     * Gets the entity type this provider represents.
     */
    @Nonnull
    EntityType<?> asEntityType();

    @Override
    default ResourceLocation getRegistryName() {
        return asEntityType().getRegistryName();
    }

    @Override
    default Component getTextComponent() {
        return asEntityType().getDescription();
    }

    @Override
    default String getTranslationKey() {
        return asEntityType().getDescriptionId();
    }

}
