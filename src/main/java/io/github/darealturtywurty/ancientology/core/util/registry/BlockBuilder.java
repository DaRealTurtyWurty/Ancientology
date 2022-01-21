package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

import io.github.darealturtywurty.ancientology.core.util.LootTableUtils;
import io.github.darealturtywurty.ancientology.core.util.interfaces.LootTableFunction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

/**
 * A builder for easily creating blocks using {@link BlockDeferredRegister}.
 * 
 * @author     matyrobbrt
 *
 * @param  <B> the base class of the block
 */
@SuppressWarnings("unchecked")
public class BlockBuilder<B extends Block> implements Builder<B> {

    protected final Factory<BlockBehaviour.Properties, B> factory;
    protected final BlockDeferredRegister register;
    protected final String name;
    private BlockItemBuilder<BlockItem> blockItemBuilder;
    private RegistryObject<B> registryObject;

    private HarvestLevel harvestLevel;
    private HarvestTool harvestTool;
    private BlockBehaviour.Properties properties = Properties.of(Material.HEAVY_METAL);
    private final List<Tag.Named<Block>> tags = new ArrayList<>();

    private LootTableFunction lootTable;

    BlockBuilder(final String name, Factory<Properties, B> factory, BlockDeferredRegister register) {
        this.factory = factory;
        this.register = register;
        this.name = name;

        this.blockItemBuilder = new BlockItemBuilder<>(BlockItem::new);
    }

    /**
     * Sets the material of the block
     * 
     * @param  material
     * @return          the builder instance
     */
    public BlockBuilder<B> material(@Nonnull Material material) {
        properties.material = material;
        return this;
    }

    /**
     * Sets the harvest level of the block. <br>
     * In order for the tags to be generated, {@code runData} needs to be run.
     * 
     * @param  harvestLevel the harvest level
     * @return              the builder instance
     */
    public BlockBuilder<B> harvestLevel(@Nonnull HarvestLevel harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    /**
     * Sets the harvest tool of the block. <br>
     * In order for the tags to be generated, {@code runData} needs to be run.
     * 
     * @param  harvestTool the harvest tool
     * @return             the builder instance
     */
    public BlockBuilder<B> harvestTool(@Nonnull HarvestTool harvestTool) {
        this.harvestTool = harvestTool;
        return this;
    }

    /**
     * Makes the block require the correct tool for it to drop.
     * 
     * @return the builder instance
     */
    public BlockBuilder<B> requiresCorrectToolForDrops() {
        properties.requiresCorrectToolForDrops();
        return this;
    }

    /**
     * This method should be called when something from the block's
     * {@link Properties} needs to be modified, but a method that does so doesn't
     * exist in {@link BlockBuilder}. <br>
     * <br>
     * It is preferred to chain method calls inside the consumer, instead of calling
     * {@link #modifyProperties(Consumer)} multiple times.
     * 
     * @param  consumer the consumer which will accept the current
     *                  {@link Properties} and modify them.
     * @return          the builder instance
     */
    public BlockBuilder<B> modifyProperties(@Nonnull final Consumer<Properties> consumer) {
        consumer.accept(properties);
        return this;
    }

    /**
     * Sets the loot table of the block. <br>
     * In order for the loot table to be generated, {@code runData} needs to be run.
     * 
     * @param  lootTable a function which takes in a {@link Block} and returns the
     *                   {@link LootTable.Builder}
     * @return           the builder instance
     */
    public BlockBuilder<B> withLootTable(@Nullable final LootTableFunction lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    /**
     * Sets the loot table of the block. <br>
     * In order for the loot table to be generated, {@code runData} needs to be run.
     * 
     * @param  lootTable the loot table of the block
     * @return           the builder instance
     */
    public BlockBuilder<B> withLootTable(@Nullable final LootTable.Builder lootTable) {
        this.lootTable = b -> lootTable;
        return this;
    }

    /**
     * Makes the block drop itself. <br>
     * In order for the loot table to be generated, {@code runData} needs to be run.
     * 
     * @return the builder instance
     */
    public BlockBuilder<B> dropSelf() {
        this.lootTable = LootTableUtils::createSingleItemTable;
        return this;
    }

    /**
     * Configures the block's block item, using a normal {@link BlockItem}
     * 
     * @param  consumer a consumer that modifies the {@link BlockItemBuilder} in
     *                  order to fit your needs
     * @return          the builder instance
     */
    public BlockBuilder<B> blockItem(@Nonnull final Consumer<BlockItemBuilder<BlockItem>> consumer) {
        consumer.accept(blockItemBuilder);
        return this;
    }

    /**
     * Configures the block's block item, using the specified {@code factory} in
     * order to instantiate it
     * 
     * @param  <I>      the class of the block item
     * @param  factory  a factory which takes in a block and {@code Item.Properties}
     *                  returning a block item of the type {@code I}
     * @param  consumer a consumer which will modify the created
     *                  {@link BlockItemBuilder} in order to fit your needs
     * @return          the builder instance
     */
    public <I extends BlockItem> BlockBuilder<B> blockItem(final BlockItemFactory<B, I> factory,
            @Nonnull final Consumer<BlockItemBuilder<I>> consumer) {
        final var builder = new BlockItemBuilder<>(factory);
        consumer.accept(builder);
        this.blockItemBuilder = (BlockItemBuilder<BlockItem>) builder;
        return this;
    }

    /**
     * This method will make the builder not generate a block item.
     * 
     * @return the builder instance
     */
    public BlockBuilder<B> noBlockItem() {
        this.blockItemBuilder = null;
        return this;
    }

    /**
     * Adds a tag to the block. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tag(s) to add
     * @return      the builder instance
     */
    @SafeVarargs
    public final BlockBuilder<B> addTag(Named<Block>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Adds tags to the block. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tags to add
     * @return      the builder instance
     */
    public BlockBuilder<B> addTag(List<Named<Block>> tags) {
        this.tags.addAll(tags);
        return this;
    }

    @Override
    public RegistryObject<B> build() {
        if (registryObject != null) { return registryObject; }
        final var object = register.getRegister().register(name, () -> factory.build(properties));
        this.registryObject = object;

        tags.forEach(tag -> register.tags.computeIfAbsent(tag, k -> Lists.newArrayList()).add(object::get));

        if (harvestLevel != null) {
            register.harvestLevels.computeIfAbsent(harvestLevel, k -> Lists.newArrayList()).add(object::get);
        }
        if (harvestTool != null) {
            register.harvestTools.computeIfAbsent(harvestTool, k -> Lists.newArrayList()).add(object::get);
        }
        if (lootTable != null) {
            register.lootTables.computeIfAbsent(object::get, k -> lootTable::makeLootTable);
        }

        if (blockItemBuilder != null) {
            blockItemBuilder.build();
        }

        return object;
    }

    public class BlockItemBuilder<I extends BlockItem> extends ItemBuilder<I> {

        private final BlockItemFactory<B, I> blockItemFactory;

        BlockItemBuilder(BlockItemFactory<B, I> factory) {
            super(null, BlockBuilder.this.register.itemRegister, BlockBuilder.this.name);
            this.blockItemFactory = factory;
        }

        @Override
        public RegistryObject<I> build() {
            final var obj = register.getRegister().register(name,
                    () -> blockItemFactory.build(registryObject.get(), this.properties));
            addDatagenStuff(obj);
            return obj;
        }

    }

    @FunctionalInterface
    public static interface BlockItemFactory<B extends Block, I extends BlockItem> {

        I build(B block, Item.Properties properties);
    }

    public enum HarvestLevel {

        WOOD(() -> Tags.Blocks.NEEDS_WOOD_TOOL), STONE(() -> BlockTags.NEEDS_STONE_TOOL),
        GOLD(() -> Tags.Blocks.NEEDS_GOLD_TOOL), IRON(() -> BlockTags.NEEDS_IRON_TOOL),
        DIAMOND(() -> BlockTags.NEEDS_DIAMOND_TOOL), NETHERITE(() -> Tags.Blocks.NEEDS_NETHERITE_TOOL);

        private final Supplier<Named<Block>> tagSupplier;

        private HarvestLevel(Supplier<Named<Block>> tagSupplier) {
            this.tagSupplier = tagSupplier;
        }

        public Named<Block> getTag() {
            return tagSupplier.get();
        }
    }

    public enum HarvestTool {

        PICKAXE(() -> BlockTags.MINEABLE_WITH_PICKAXE), AXE(() -> BlockTags.MINEABLE_WITH_AXE),
        SHOVEL(() -> BlockTags.MINEABLE_WITH_SHOVEL), HOE(() -> BlockTags.MINEABLE_WITH_HOE);

        private final Supplier<Named<Block>> tagSupplier;

        private HarvestTool(Supplier<Named<Block>> tagSupplier) {
            this.tagSupplier = tagSupplier;
        }

        public Named<Block> getTag() {
            return tagSupplier.get();
        }
    }

}
