package io.github.darealturtywurty.ancientology.common.recipes;

import com.google.gson.JsonObject;

import io.github.darealturtywurty.ancientology.core.util.helper.CraftingHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

public record ItemStackChance(ItemStack itemStack, float chance) {
    public ItemStackChance(ItemStack itemStack) {
        this(itemStack, 100);
    }

    public static ItemStackChance fromJSON(final JsonObject json) {
        final ItemStack stack = json.get("item").isJsonObject()
                ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "item"))
                : new ItemStack(
                        ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "item"))));
        final var chance = json.has("chance") ? GsonHelper.getAsFloat(json, "chance") : 100;
        return new ItemStackChance(stack, chance);
    }

    public static ItemStackChance fromNetwork(final FriendlyByteBuf buffer) {
        final var item = buffer.readItem();
        final var chance = buffer.readFloat();
        return new ItemStackChance(item, chance);
    }

    public void toNetwork(final FriendlyByteBuf buffer) {
        buffer.writeItem(itemStack());
        buffer.writeFloat(this.chance);
    }

    public JsonObject toJson() {
        final var obj = new JsonObject();
        obj.add("item", CraftingHelper.itemToJson(this.itemStack));
        obj.addProperty("chance", this.chance);
        return obj;
    }
}
