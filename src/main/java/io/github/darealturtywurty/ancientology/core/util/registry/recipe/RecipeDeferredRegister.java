package io.github.darealturtywurty.ancientology.core.util.registry.recipe;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import io.github.darealturtywurty.ancientology.core.util.registry.DeferredRegisterWrapper;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeDeferredRegister extends DeferredRegisterWrapper<RecipeSerializer<?>> {

    private RecipeDeferredRegister(IForgeRegistry<RecipeSerializer<?>> reg, String modID) {
        super(reg, modID);
    }

    public static RecipeDeferredRegister create(final String modid) {
        return new RecipeDeferredRegister(ForgeRegistries.RECIPE_SERIALIZERS, modid);
    }

    public <R extends Recipe<?>> RecipeRegistryObject<R> register(final String name,
            final NonNullSupplier<RecipeSerializer<R>> serializer) {
        final var registryName = new ResourceLocation(getModID(), name);
        final var type = Registry.register(Registry.RECIPE_TYPE, registryName, new RecipeType<R>() {

            @Override
            public String toString() {
                return registryName.toString();
            }
        });
        final var serializerObj = getRegister().register(name, serializer::get);
        return new RecipeRegistryObject<>(serializerObj, type);
    }

}
