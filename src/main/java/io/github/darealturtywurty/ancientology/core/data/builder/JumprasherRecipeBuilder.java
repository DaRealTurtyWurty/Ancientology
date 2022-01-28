package io.github.darealturtywurty.ancientology.core.data.builder;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import io.github.darealturtywurty.ancientology.common.recipes.ItemStackChance;
import io.github.darealturtywurty.ancientology.core.init.RecipeInit;

public record JumprasherRecipeBuilder(Ingredient input, List<ItemStackChance> outputs) {

    public static JumprasherRecipeBuilder recipe(final Ingredient input, ItemStackChance... outputs) {
        return new JumprasherRecipeBuilder(input, Lists.newArrayList(outputs));
    }

    public JumprasherRecipeBuilder addOutput(final ItemStackChance output) {
        outputs.add(output);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        String name = this.outputs.get(0).itemStack().getItem().getRegistryName().getPath();
        save(consumer,
                new ResourceLocation(RecipeInit.JUMPRASHER.get().getRegistryName() + "/" + name));
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        consumer.accept(new Result(recipeId));
    }

    public final class Result implements FinishedRecipe {

        private final ResourceLocation recipeId;

        public Result(final ResourceLocation recipeId) {
            this.recipeId = recipeId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", input.toJson());
            final JsonArray results = new JsonArray();
            outputs.forEach(output -> results.add(output.toJson()));
            pJson.add("result", results);
        }

        @Override
        public ResourceLocation getId() {
            return recipeId;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeInit.JUMPRASHER.get();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }

}
