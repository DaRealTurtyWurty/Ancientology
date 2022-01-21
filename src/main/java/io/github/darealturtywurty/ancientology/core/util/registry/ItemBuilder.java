package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.EnumMap;
import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

import io.github.darealturtywurty.ancientology.core.util.LangLocale;
import net.minecraftforge.registries.RegistryObject;

public class ItemBuilder<I extends Item> implements Builder<I> {

    protected final Factory<Properties, I> factory;
    protected final ItemDeferredRegister register;
    protected final String name;

    private int maxStackSize = 64;
    private int maxDamage;
    @Nullable
    private Item craftingRemainingItem;
    @Nullable
    private CreativeModeTab category;
    private Rarity rarity = Rarity.COMMON;
    @Nullable
    private FoodProperties foodProperties;
    private boolean isFireResistant;
    private boolean canRepair = true;
    protected final EnumMap<LangLocale, String> lang = new EnumMap<>(LangLocale.class);

    ItemBuilder(Factory<Properties, I> factory, ItemDeferredRegister register, String name) {
        this.factory = factory;
        this.register = register;
        this.name = name;
    }

    public ItemBuilder<I> stacksTo(final int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public ItemBuilder<I> defaultDurability(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public ItemBuilder<I> craftRemainder(@Nullable final Item craftingRemainingItem) {
        this.craftingRemainingItem = craftingRemainingItem;
        return this;
    }

    public ItemBuilder<I> tab(@Nullable final CreativeModeTab itemTab) {
        this.category = itemTab;
        return this;
    }

    public ItemBuilder<I> rarity(@Nonnull final Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ItemBuilder<I> food(@Nullable final FoodProperties food) {
        this.foodProperties = food;
        return this;
    }

    public ItemBuilder<I> fireResistant() {
        this.isFireResistant = true;
        return this;
    }

    public ItemBuilder<I> setNoRepair() {
        this.canRepair = false;
        return this;
    }

    public ItemBuilder<I> lang(@Nonnull final String lang) {
        return lang(LangLocale.EN_US, lang);
    }

    public ItemBuilder<I> lang(@Nonnull final LangLocale locale, @Nonnull final String lang) {
        this.lang.put(locale, lang);
        return this;
    }

    protected Properties createProperties() {
        final var properties = new Properties().tab(category).stacksTo(maxStackSize).defaultDurability(maxDamage)
                .craftRemainder(craftingRemainingItem).rarity(rarity).food(foodProperties);
        if (isFireResistant) {
            properties.fireResistant();
        }
        if (!canRepair) {
            properties.setNoRepair();
        }
        return properties;
    }

    @Override
    public RegistryObject<I> build() {
        final var object = register.getRegister().register(name, () -> factory.build(createProperties()));
        lang.forEach(
                (locale, l) -> register.langEntries.computeIfAbsent(locale, k -> new HashMap<>()).put(object::get, l));
        return object;
    }

}
