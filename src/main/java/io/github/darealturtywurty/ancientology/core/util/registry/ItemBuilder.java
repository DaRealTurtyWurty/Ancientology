package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import net.minecraftforge.registries.RegistryObject;

/**
 * A builder for easily creating items using {@link ItemDeferredRegister}.
 * 
 * @author     matyrobbrt
 *
 * @param  <I> the base class of the item
 */
public class ItemBuilder<I extends Item> implements Builder<I> {

    protected final Factory<Properties, I> factory;
    protected final ItemDeferredRegister register;
    protected final String name;
    protected ItemRegistryObject<I> registryObject;

    protected final Properties properties = new Properties();
    private final EnumMap<MinecraftLocale, String> lang = new EnumMap<>(MinecraftLocale.class);
    private final List<Tag.Named<Item>> tags = new ArrayList<>();
    final List<Pair<Integer, Consumer<ShapedRecipeBuilder>>> shapedRecipes = new ArrayList<>();
    final List<Pair<Integer, Consumer<ShapelessRecipeBuilder>>> shapelessRecipes = new ArrayList<>();

    protected ItemBuilder(Factory<Properties, I> factory, ItemDeferredRegister register, String name) {
        this.factory = factory;
        this.register = register;
        this.name = name;

        if (register.getDefaultItemTab() != null) {
            properties.tab(register.getDefaultItemTab());
        }
    }

    /**
     * Sets the max stack size of the item
     * 
     * @param  maxStackSize
     * @return              the builder instance
     */
    public ItemBuilder<I> stacksTo(final int maxStackSize) {
        properties.stacksTo(maxStackSize);
        return this;
    }

    /**
     * Sets the default durability of the item
     * 
     * @param  maxDamage
     * @return           the builder instance
     */
    public ItemBuilder<I> defaultDurability(final int maxDamage) {
        properties.defaultDurability(maxDamage);
        return this;
    }

    /**
     * Sets the crafting remainder of the item
     * 
     * @param  craftingRemainingItem
     * @return                       the builder instance
     */
    public ItemBuilder<I> craftRemainder(@Nullable final Item craftingRemainingItem) {
        properties.craftRemainder(craftingRemainingItem);
        return this;
    }

    /**
     * Sets the creative tab of the item. <br>
     * The default item tab will be the one set in
     * {@link ItemDeferredRegister#setDefaultItemTab(CreativeModeTab)}
     * 
     * @param  itemTab
     * @return         the builder instance
     */
    public ItemBuilder<I> tab(@Nullable final CreativeModeTab itemTab) {
        properties.tab(itemTab);
        return this;
    }

    /**
     * Sets the rarity of the item
     * 
     * @param  rarity
     * @return        the builder instance
     */
    public ItemBuilder<I> rarity(@Nonnull final Rarity rarity) {
        properties.rarity(rarity);
        return this;
    }

    /**
     * Sets the food properties of the item
     * 
     * @param  food
     * @return      the builder instance
     */
    public ItemBuilder<I> food(@Nullable final FoodProperties food) {
        properties.food(food);
        return this;
    }

    /**
     * Call this in order to make the item fire resistant
     * 
     * @return the builder instance
     */
    public ItemBuilder<I> fireResistant() {
        properties.fireResistant();
        return this;
    }

    /**
     * Call this in order to make the item not reparable
     * 
     * @return the builder instance
     */
    public ItemBuilder<I> setNoRepair() {
        properties.setNoRepair();
        return this;
    }

    /**
     * Sets the lang entry of the item for the locale English - United States. <br>
     * In order for the lang entry to be added, {@code runData} needs to be run.
     * 
     * @param  lang the en_us lang entry of this item
     * @return      the builder instance
     */
    public ItemBuilder<I> lang(@Nonnull final String lang) {
        return lang(MinecraftLocale.EN_US, lang);
    }

    /**
     * Sets the lang entry of the item for the specified {@code locale}. <br>
     * In order for the lang entry to be added, {@code runData} needs to be run.
     * 
     * @param  locale the locale
     * @param  lang   the lang entry
     * @return        the builder instance
     */
    public ItemBuilder<I> lang(@Nonnull final MinecraftLocale locale, @Nonnull final String lang) {
        this.lang.put(locale, lang);
        return this;
    }

    /**
     * Adds a tag to the item. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tag(s) to add
     * @return      the builder instance
     */
    @SafeVarargs
    public final ItemBuilder<I> addTag(Named<Item>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Adds tags to the item. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tags to add
     * @return      the builder instance
     */
    public ItemBuilder<I> addTag(List<Named<Item>> tags) {
        this.tags.addAll(tags);
        return this;
    }

    /**
     * Creates a shaped recipe for the item. <br>
     * In order for the recipe to be generated, {@code runData} needs to be run.
     * 
     * @param  count    the recipe result count
     * @param  consumer a consumer which will define the recipe
     * @return          the builder instance
     */
    public ItemBuilder<I> shapedRecipe(final int count, final Consumer<ShapedRecipeBuilder> consumer) {
        this.shapedRecipes.add(Pair.of(count, consumer));
        return this;
    }

    /**
     * Creates a shapeless recipe for the item. <br>
     * In order for the recipe to be generated, {@code runData} needs to be run.
     * 
     * @param  count    the recipe result count
     * @param  consumer a consumer which will define the recipe
     * @return          the builder instance
     */
    public ItemBuilder<I> shapelessRecipe(final int count, final Consumer<ShapelessRecipeBuilder> consumer) {
        this.shapelessRecipes.add(Pair.of(count, consumer));
        return this;
    }

    @Override
    public ItemRegistryObject<I> build() {
        if (registryObject != null) { return registryObject; }
        final var object = register.getRegister().register(name, () -> factory.build(properties));
        this.registryObject = createRegistryObject(object);
        addBuilderToRegister();
        addDatagenStuff(object);
        return registryObject;
    }

    protected static <I extends Item> ItemRegistryObject<I> createRegistryObject(final RegistryObject<I> obj) {
        return new ItemRegistryObject<>(obj);
    }

    protected void addBuilderToRegister() {
        register.builders.add(this);
    }

    @Override
    public I get() {
        return registryObject.get();
    }

    protected void addDatagenStuff(final RegistryObject<I> registryObject) {
        lang.forEach((locale, l) -> register.langEntries.computeIfAbsent(locale, k -> new HashMap<>())
                .put(registryObject::get, l));
        tags.forEach(tag -> register.tags.computeIfAbsent(tag, k -> Lists.newArrayList()).add(registryObject::get));
    }

}
