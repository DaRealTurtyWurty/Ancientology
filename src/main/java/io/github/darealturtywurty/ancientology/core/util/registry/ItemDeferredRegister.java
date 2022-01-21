package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/**
 * A wrapper around {@link DeferredRegister} for registering items using
 * {@link ItemBuilder}s
 * 
 * @author matyrobbrt
 */
public class ItemDeferredRegister extends DeferredRegisterWrapper<Item> {

    final EnumMap<MinecraftLocale, Map<Supplier<Item>, String>> langEntries = new EnumMap<>(MinecraftLocale.class);
    final Map<Named<Item>, List<Supplier<Item>>> tags = new HashMap<>();

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
     * {@link RegistryObject} containing that item. The registry object will be safe
     * to call after {@link FMLCommonSetupEvent}.
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

    public Map<Named<Item>, List<Supplier<Item>>> getTags() {
        return Map.copyOf(tags);
    }

}
