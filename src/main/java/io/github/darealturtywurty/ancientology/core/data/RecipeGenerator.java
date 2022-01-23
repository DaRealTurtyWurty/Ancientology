package io.github.darealturtywurty.ancientology.core.data;

import com.google.common.collect.ImmutableList;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    private static final ImmutableList<ItemLike> TIN_SMELTABLES = ImmutableList.of(BlockInit.DEEPSLATE_TIN_ORE.get(), ItemInit.RAW_TIN.get());

    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finished) {
        oreSmelting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT.get(), 0.7F, 200, "tin_ingot");
        oreBlasting(finished, TIN_SMELTABLES, ItemInit.TIN_INGOT.get(), 0.7F, 100, "tin_ingot");
    }
}
