package io.github.darealturtywurty.ancientology.core.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import com.google.common.collect.ImmutableList;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.world.level.ItemLike;

public class RecipeGenerator extends RecipeProvider {

    private static final ImmutableList<ItemLike> TIN_SMELTABLES = ImmutableList.of(BlockInit.DEEPSLATE_TIN_ORE, ItemInit.RAW_TIN);

    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finished) {
        ShapelessRecipeBuilder.shapeless(BlockInit.LIFE_PLANKS.get(), 4).requires(BlockInit.LIFE_LOG.get()).group("planks").unlockedBy("has_logs", has(BlockInit.LIFE_LOG.get())).save(finished);
        oreSmelting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT, 0.7F, 200, "tin_ingot");
        oreBlasting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT, 0.7F, 100, "tin_ingot");
    }
}
