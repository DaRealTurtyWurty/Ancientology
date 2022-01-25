package io.github.darealturtywurty.ancientology.core.data;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import io.github.darealturtywurty.ancientology.common.recipes.ItemStackChance;
import io.github.darealturtywurty.ancientology.core.data.builder.JumprasherRecipeBuilder;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;

public class RecipeGenerator extends RecipeProvider {

    private static final java.util.List<ItemLike> TIN_SMELTABLES = List.of(BlockInit.DEEPSLATE_TIN_ORE,
            ItemInit.RAW_TIN);

    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finished) {
        oreSmelting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT, 0.7F, 200, "tin_ingot");
        oreBlasting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT, 0.7F, 100, "tin_ingot");

        JumprasherRecipeBuilder.recipe(Ingredient.of(BlockInit.DEEPSLATE_TIN_ORE),
                new ItemStackChance(ItemInit.RAW_TIN.getItemStack()),
                new ItemStackChance(ItemInit.RAW_TIN.getItemStack(), 20)).save(finished);
    }
}
