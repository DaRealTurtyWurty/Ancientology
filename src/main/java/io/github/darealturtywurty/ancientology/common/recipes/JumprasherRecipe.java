package io.github.darealturtywurty.ancientology.common.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import io.github.darealturtywurty.ancientology.core.init.RecipeInit;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public record JumprasherRecipe(ResourceLocation id, Ingredient input, NonNullList<ItemStackChance> outputs)
        implements Recipe<Container> {
    
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return this.input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        if (this.outputs.isEmpty())
            return ItemStack.EMPTY;
        return this.outputs.get(0).itemStack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        if (this.outputs.isEmpty())
            return ItemStack.EMPTY;
        return this.outputs.get(0).itemStack().copy();
    }

    @Override
    public ResourceLocation getId() {
        return id();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.JUMPRASHER.getFirst().get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.JUMPRASHER.getSecond();
    }

    public static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<JumprasherRecipe> {

        @Override
        public JumprasherRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            final var input = Ingredient.fromJson(json.get("input"));
            if (input.isEmpty())
                throw new JsonSyntaxException("An ingredient item is mandatory!");
            final var resultsJson = json.get("result");
            final NonNullList<ItemStackChance> results = NonNullList.create();
            if (resultsJson.isJsonArray()) {
                for (final var resultJson : resultsJson.getAsJsonArray()) {
                    if (!resultJson.isJsonObject())
                        throw new JsonSyntaxException("Invalid recipe result!");
                    results.add(ItemStackChance.fromJSON(resultJson.getAsJsonObject()));
                }
            } else if (resultsJson.isJsonObject()) {
                results.add(ItemStackChance.fromJSON(resultsJson.getAsJsonObject()));
            } else
                throw new JsonSyntaxException("Invalid recipe result(s)");
            return new JumprasherRecipe(pRecipeId, input, results);
        }

        @Override
        public JumprasherRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buffer) {
            final var input = Ingredient.fromNetwork(buffer);
            final var results = buffer.readCollection(NonNullList::createWithCapacity, ItemStackChance::fromNetwork);
            return new JumprasherRecipe(pRecipeId, input, results);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, JumprasherRecipe recipe) {
            recipe.input().toNetwork(buffer);
            buffer.writeCollection(recipe.outputs(), (buf, stackChance) -> stackChance.toNetwork(buf));
        }
    }
}
