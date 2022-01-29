package io.github.darealturtywurty.ancientology.core.init;

import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.recipes.JumprasherRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class RecipeInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister
            .create(ForgeRegistries.RECIPE_SERIALIZERS, Ancientology.MODID);

    public static final Pair<RegistryObject<RecipeSerializer<JumprasherRecipe>>, RecipeType<JumprasherRecipe>> JUMPRASHER = register(
            "jumprasher", JumprasherRecipe.Serializer::new);

    public static <T extends Recipe<?>> Pair<RegistryObject<RecipeSerializer<T>>, RecipeType<T>> register(String name,
            Supplier<RecipeSerializer<T>> recipe) {
        final ResourceLocation resource = Ancientology.rl(name);
        final RecipeType<T> rec = Registry.register(Registry.RECIPE_TYPE, resource, new RecipeType<T>() {
            @Override
            public String toString() {
                return resource.toString();
            }
        });
        
        return Pair.of(RECIPES.register(name, recipe), rec);
    }
}
