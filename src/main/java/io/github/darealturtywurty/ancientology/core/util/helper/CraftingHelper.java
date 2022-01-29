package io.github.darealturtywurty.ancientology.core.util.helper;

import java.util.Optional;
import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CraftingHelper {
    
    public static <C extends Container, T extends Recipe<C>> Optional<T> findRecipe(final RecipeType<T> recipeType,
            final Level level, Predicate<T> tester) {
        return level.getRecipeManager().getAllRecipesFor(recipeType).stream().filter(tester).findFirst();
    }
    
    public static JsonObject itemToJson(final ItemStack stack) {
        final var obj = new JsonObject();
        obj.addProperty("item", stack.getItem().getRegistryName().toString());
        if (stack.getCount() > 1) {
            obj.addProperty("count", stack.getCount());
        }
        if (stack.hasTag()) {
            obj.addProperty("nbt", stack.getTag().getAsString());
        }
        return obj;
    }
    
}
