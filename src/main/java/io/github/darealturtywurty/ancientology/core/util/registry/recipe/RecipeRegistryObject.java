package io.github.darealturtywurty.ancientology.core.util.registry.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import io.github.darealturtywurty.ancientology.core.util.interfaces.RecipeTypeProvider;
import io.github.darealturtywurty.ancientology.core.util.registry.WrappedRegistryObject;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistryObject<R extends Recipe<?>> extends WrappedRegistryObject<RecipeSerializer<R>>
        implements RecipeTypeProvider {

    private final RecipeType<R> recipeType;

    RecipeRegistryObject(RegistryObject<RecipeSerializer<R>> registryObject, final RecipeType<R> recipeType) {
        super(registryObject);
        this.recipeType = recipeType;
    }

    @Override
    public RecipeType<R> asRecipeType() {
        return recipeType;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return getId();
    }

    @Override
    public RecipeSerializer<R> asRecipeSerializer() {
        return registryObject.get();
    }

}
