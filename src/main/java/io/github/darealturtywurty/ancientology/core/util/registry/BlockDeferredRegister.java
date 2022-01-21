package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootTable;

import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestLevel;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockDeferredRegister extends DeferredRegisterWrapper<Block> {

    final EnumMap<HarvestLevel, List<Supplier<Block>>> harvestLevels = new EnumMap<>(HarvestLevel.class);
    final EnumMap<HarvestTool, List<Supplier<Block>>> harvestTools = new EnumMap<>(HarvestTool.class);
    final Map<Supplier<Block>, Function<Block, LootTable.Builder>> lootTables = new HashMap<>();

    final ItemDeferredRegister itemRegister;

    private final boolean isItemRegisterManual;

    private BlockDeferredRegister(String modid) {
        super(ForgeRegistries.BLOCKS, modid);
        this.itemRegister = ItemDeferredRegister.create(modid);
        this.isItemRegisterManual = false;
    }

    private BlockDeferredRegister(String modid, ItemDeferredRegister itemDeferredRegister) {
        super(ForgeRegistries.BLOCKS, modid);
        this.itemRegister = itemDeferredRegister;
        this.isItemRegisterManual = true;
    }

    public static BlockDeferredRegister create(String modid) {
        return new BlockDeferredRegister(modid);
    }

    public static BlockDeferredRegister create(String modid, ItemDeferredRegister itemDeferredRegister) {
        return new BlockDeferredRegister(modid, itemDeferredRegister);
    }

    public <B extends Block> BlockBuilder<B> register(final String name, final Factory<Properties, B> factory) {
        return new BlockBuilder<>(name, factory, this);
    }

    <B extends Block, I extends BlockItem> RegistryObject<B> registerWithItem(final String name,
            final Supplier<B> blockSup, final Optional<Supplier<I>> itemSup) {
        final var block = register.register(name, blockSup);
        itemSup.ifPresent(sup -> itemRegister.register.register(name, sup));
        return block;
    }

    public Map<HarvestLevel, List<Supplier<Block>>> getHarvestLevels() {
        return Maps.immutableEnumMap(harvestLevels);
    }

    public Map<HarvestTool, List<Supplier<Block>>> getHarvestTools() {
        return Maps.immutableEnumMap(harvestTools);
    }

    public Map<Supplier<Block>, Function<Block, LootTable.Builder>> getLootTables() {
        return Map.copyOf(lootTables);
    }

    @Override
    public void register(IEventBus bus) {
        super.register(bus);
        if (!isItemRegisterManual) {
            itemRegister.register(bus);
        }
    }

}
