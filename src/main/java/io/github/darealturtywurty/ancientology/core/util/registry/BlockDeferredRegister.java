package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootTable;

import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestLevel;
import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.HarvestTool;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * A wrapper around {@link DeferredRegister} for registering blocks (and their
 * items) using {@link BlockBuilder}s
 * 
 * @author matyrobbrt
 */
public class BlockDeferredRegister extends DeferredRegisterWrapper<Block> {

    final EnumMap<HarvestLevel, List<Supplier<Block>>> harvestLevels = new EnumMap<>(HarvestLevel.class);
    final EnumMap<HarvestTool, List<Supplier<Block>>> harvestTools = new EnumMap<>(HarvestTool.class);
    final Map<Supplier<Block>, Function<Block, LootTable.Builder>> lootTables = new HashMap<>();
    final Map<Named<Block>, List<Supplier<Block>>> tags = new HashMap<>();

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

    /**
     * Prepares a block to be registered. The registering will happen when
     * {@link BlockBuilder#build()} is called, which will also return the
     * {@link RegistryObject} containing that block. The registry object will be
     * safe to call after {@link FMLCommonSetupEvent}.
     * 
     * @param  <B>     the class of the block
     * @param  name    the registry name of the block
     * @param  factory a factory which takes in {@link Properties} and returns the
     *                 block. For normal blocks, use {@code Block::new}
     * @return         a {@link BlockBuilder} which can be configured using
     *                 method-chaining, and whose block (and optionally, block item)
     *                 will be registered when {@link BlockBuilder#build()} is be
     *                 called
     */
    public <B extends Block> BlockBuilder<B> register(final String name, final Factory<Properties, B> factory) {
        return new BlockBuilder<>(name, factory, this);
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

    public Map<Named<Block>, List<Supplier<Block>>> getTags() {
        return Map.copyOf(tags);
    }

    @Override
    public void register(IEventBus bus) {
        super.register(bus);
        if (!isItemRegisterManual) {
            itemRegister.register(bus);
        }
    }

}
