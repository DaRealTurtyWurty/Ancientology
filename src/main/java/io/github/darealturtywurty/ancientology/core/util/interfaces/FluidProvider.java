package io.github.darealturtywurty.ancientology.core.util.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidStack;

public interface FluidProvider extends Provider {

    /**
     * Gets the fluid this provider represents.
     */
    @Nonnull
    Fluid asFluid();

    /**
     * Creates a fluid stack of the given size using the fluid this provider
     * represents.
     *
     * @param size Size of the stack.
     */
    @Nonnull
    default FluidStack getFluidStack(int size) {
        return new FluidStack(asFluid(), size);
    }

    @Override
    default ResourceLocation getRegistryName() {
        return asFluid().getRegistryName();
    }

    @Override
    default Component getTextComponent() {
        return asFluid().getAttributes().getDisplayName(getFluidStack(1));
    }

    @Override
    default String getTranslationKey() {
        return asFluid().getAttributes().getTranslationKey();
    }

}
