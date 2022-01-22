package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A wrapper around {@link DeferredRegister} for registering blocks (and their
 * items) using {@link BlockBuilder}s
 * 
 * @author matyrobbrt
 */
public class BlockDeferredRegister extends DeferredRegisterWrapper<Block> {

    final Map<Named<Block>, List<Supplier<Block>>> tags = new HashMap<>();
    final Map<Supplier<Block>, Function<Block, LootTable.Builder>> lootTables = new HashMap<>();
    final Set<BlockBuilder<?>> builders = new HashSet<>();

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
     * {@link BlockRegistryObject} containing that block. The registry object will
     * be safe to call after {@link FMLCommonSetupEvent}.
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

    @Override
    public void register(IEventBus bus) {
        super.register(bus);
        if (!isItemRegisterManual) {
            itemRegister.register(bus);
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.register(new BlockDeferredRegisterClient(this)));
    }

    @Override
    public void addDatagen(GatherDataEvent event) {
        final var gen = event.getGenerator();
        final var existingFileHelper = event.getExistingFileHelper();
        if (event.includeServer()) {
            gen.addProvider(new TagsProvider(gen, existingFileHelper));
            gen.addProvider(new LootTableProvider(gen));
        }
        if (!isItemRegisterManual) {
            itemRegister.addDatagen(event);
        }
    }

    private final class TagsProvider extends BlockTagsProvider {

        public TagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, BlockDeferredRegister.this.modId, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tags.forEach((tag, blocks) -> tag(tag).add(blocks.stream().map(Supplier::get).toArray(Block[]::new)));
        }

    }

    private final class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

        public LootTableProvider(DataGenerator pGenerator) {
            super(pGenerator);
        }

        @Override
        protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {
            map.forEach((resourceLocation, lootTable) -> LootTables.validate(validationTracker, resourceLocation,
                    lootTable));
        }

        @Override
        protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
            return List.of(Pair.of(Blocks::new, LootContextParamSets.BLOCK));
        }

        public final class Blocks extends BlockLoot {

            @Override
            protected Iterable<Block> getKnownBlocks() {
                return lootTables.keySet().stream().map(Supplier::get).toList();
            }

            @Override
            protected void addTables() {
                lootTables.forEach((block, loot) -> this.add(block.get(), loot));
            }

        }

    }

}
