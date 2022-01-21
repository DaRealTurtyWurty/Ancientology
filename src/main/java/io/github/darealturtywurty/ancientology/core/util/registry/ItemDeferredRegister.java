package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import io.github.darealturtywurty.ancientology.core.util.LangLocale;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemDeferredRegister extends DeferredRegisterWrapper<Item> {

    final EnumMap<LangLocale, Map<Supplier<Item>, String>> langEntries = new EnumMap<>(LangLocale.class);

    private ItemDeferredRegister(IForgeRegistry<Item> registry, String modId) {
        super(registry, modId);
    }

    public static ItemDeferredRegister create(String modid) {
        return new ItemDeferredRegister(ForgeRegistries.ITEMS, modid);
    }

    public <I extends Item> ItemBuilder<I> register(final String name, final Factory<Properties, I> factory) {
        return new ItemBuilder<>(factory, this, name);
    }

    public Map<Supplier<Item>, String> getLangEntries(final LangLocale locale) {
        return Map.copyOf(langEntries.computeIfAbsent(locale, k -> new HashMap<>()));
    }

}
