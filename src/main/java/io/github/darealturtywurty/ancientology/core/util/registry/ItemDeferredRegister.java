package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;

import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * A wrapper around {@link DeferredRegister} for registering items using
 * {@link ItemBuilder}s
 * 
 * @author matyrobbrt
 */
public class ItemDeferredRegister extends DeferredRegisterWrapper<Item> {

    final EnumMap<MinecraftLocale, Map<Supplier<Item>, String>> langEntries = new EnumMap<>(MinecraftLocale.class);
    final Map<Named<Item>, List<Supplier<Item>>> tags = new HashMap<>();
    final List<ItemBuilder<?>> builders = new ArrayList<>();

    @Nullable
    private CreativeModeTab defaultItemTab;

    private ItemDeferredRegister(IForgeRegistry<Item> registry, String modId) {
        super(registry, modId);
    }

    /**
     * Sets the default {@link CreativeModeTab} for any {@link ItemBuilder}s created
     * through this register.
     * 
     * @param  defaultItemTab the default {@link CreativeModeTab}
     * @return                the register instance
     */
    public ItemDeferredRegister setDefaultItemTab(@Nullable final CreativeModeTab defaultItemTab) {
        this.defaultItemTab = defaultItemTab;
        return this;
    }

    public static ItemDeferredRegister create(String modid) {
        return new ItemDeferredRegister(ForgeRegistries.ITEMS, modid);
    }

    /**
     * Prepares an item to be registered. The registering will happen when
     * {@link ItemBuilder#build()} is called, which will also return the
     * {@link ItemRegistryObject} containing that item. The registry object will be
     * safe to call after {@link FMLCommonSetupEvent}.
     * 
     * @param  <I>     the class of the item
     * @param  name    the registry name of the item
     * @param  factory a factory which takes in {@link Properties} and returns the
     *                 item. For normal items, use {@code Item::new}
     * @return         an {@link ItemBuilder} which can be configured using
     *                 method-chaining, and whose item will be registered when
     *                 {@link ItemBuilder#build()} is be called
     */
    public <I extends Item> ItemBuilder<I> register(final String name, final Factory<Properties, I> factory) {
        return new ItemBuilder<>(factory, this, name);
    }

    public CreativeModeTab getDefaultItemTab() {
        return defaultItemTab;
    }

    public Map<Supplier<Item>, String> getLangEntries(final MinecraftLocale locale) {
        return Map.copyOf(langEntries.computeIfAbsent(locale, k -> new HashMap<>()));
    }

    @Override
    public void addDatagen(GatherDataEvent event) {
        final var gen = event.getGenerator();
        final var existingFileHelper = event.getExistingFileHelper();
        if (event.includeServer()) {
            gen.addProvider(new ItemTags(gen, existingFileHelper));
            gen.addProvider(new RecipeProvider(gen));
        }
    }

    private final class ItemTags extends ItemTagsProvider {

        public ItemTags(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, new BlockTags(pGenerator, existingFileHelper), ItemDeferredRegister.this.getModID(),
                    existingFileHelper);
        }

        @Override
        protected void addTags() {
            tags.forEach((tag, items) -> tag(tag).add(items.stream().map(Supplier::get).toArray(Item[]::new)));
        }

    }

    private final class BlockTags extends BlockTagsProvider {

        public BlockTags(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, ItemDeferredRegister.this.getModID(), existingFileHelper);
        }

    }

    private final class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

        public RecipeProvider(DataGenerator pGenerator) {
            super(pGenerator);
        }

        @Override
        protected void buildCraftingRecipes(Consumer<FinishedRecipe> finsishedConsumer) {
            builders.forEach(builder -> {
                final var item = builder.get();
                final var registryName = item.getRegistryName();
                for (int i = 0; i < builder.shapedRecipes.size(); i++) {
                    final var shapedPair = builder.shapedRecipes.get(i);
                    final var recipeBuilder = ShapedRecipeBuilder.shaped(item, shapedPair.getKey())
                            .unlockedBy("has_item", has(Items.AIR));
                    shapedPair.getValue().accept(recipeBuilder);
                    recipeBuilder.save(finsishedConsumer, new ResourceLocation(registryName.getNamespace(),
                            "registry_generated/" + registryName.getPath() + "_shaped_" + i));
                }
                for (int i = 0; i < builder.shapelessRecipes.size(); i++) {
                    final var shapedPair = builder.shapelessRecipes.get(i);
                    final var recipeBuilder = ShapelessRecipeBuilder.shapeless(item, shapedPair.getKey())
                            .unlockedBy("has_item", has(Items.AIR));
                    shapedPair.getValue().accept(recipeBuilder);
                    recipeBuilder.save(finsishedConsumer, new ResourceLocation(registryName.getNamespace(),
                            "registry_generated/" + registryName.getPath() + "_shapeless_" + i));
                }
            });
        }

    }
}
