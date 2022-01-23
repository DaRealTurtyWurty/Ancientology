package io.github.darealturtywurty.ancientology.core.data;

import java.util.function.Consumer;

import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finished) {
        ShapelessRecipeBuilder.shapeless(BlockInit.LIFE_PLANKS.get(), 4).requires(BlockInit.LIFE_LOG.get()).group("planks").unlockedBy("has_logs", has(BlockInit.LIFE_LOG.get())).save(finished);
    }
}
