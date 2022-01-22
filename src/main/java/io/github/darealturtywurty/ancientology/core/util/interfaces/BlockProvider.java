package io.github.darealturtywurty.ancientology.core.util.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public interface BlockProvider extends ItemProvider {

    @Nonnull
    Block asBlock();

    @Override
    default ResourceLocation getRegistryName() {
        return asBlock().getRegistryName();
    }

    @Override
    default String getTranslationKey() {
        return asBlock().getDescriptionId();
    }

}
