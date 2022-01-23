package io.github.darealturtywurty.ancientology.core.util.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

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

    default BlockState defaultBlockState() {
        return asBlock().defaultBlockState();
    }

}
