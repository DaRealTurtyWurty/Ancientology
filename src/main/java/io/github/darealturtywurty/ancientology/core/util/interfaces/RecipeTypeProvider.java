package io.github.darealturtywurty.ancientology.core.util.interfaces;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public interface RecipeTypeProvider extends Provider {

    RecipeType<?> asRecipeType();

    RecipeSerializer<?> asRecipeSerializer();

    @Override
    default String getTranslationKey() {
        final var registryName = getRegistryName();
        return "recipe." + registryName.getNamespace() + "." + registryName.getPath();
    }

}
