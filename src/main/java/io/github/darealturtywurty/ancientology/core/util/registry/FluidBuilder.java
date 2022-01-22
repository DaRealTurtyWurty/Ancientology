package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * A builder for easily creating fluids using {@link FluidDeferredRegister}.
 * 
 * @author           matyrobbrt
 *
 * @param  <STILL>   the base class of the still fluid
 * @param  <FLOWING> the base class of the flowing fluid
 */
@SuppressWarnings("unchecked")
public class FluidBuilder<STILL extends ForgeFlowingFluid.Source, FLOWING extends ForgeFlowingFluid.Flowing>
        implements Builder<STILL> {

    public static final ResourceLocation STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation OVERLAY_RL = new ResourceLocation("block/water_overlay");

    protected final Factory<Properties, STILL> stillFactory;
    protected final Factory<Properties, FLOWING> flowingFactory;
    protected final FluidDeferredRegister register;
    protected final String name;
    private FluidRegistryObject<STILL, FLOWING> registryObject;
    private FluidBlockBuilder<LiquidBlock> fluidBlockBuilder;
    private BucketBuilder<BucketItem> bucketBuilder;

    private final FluidAttributes.Builder attributes;
    private final List<Tag.Named<Fluid>> tags = new ArrayList<>();

    FluidBuilder(final String name, Factory<Properties, STILL> stillFactory,
            Factory<Properties, FLOWING> flowingFactory, FluidDeferredRegister register) {
        this(name, stillFactory, flowingFactory, register, STILL_RL, FLOWING_RL, OVERLAY_RL);
    }

    FluidBuilder(final String name, Factory<Properties, STILL> stillFactory,
            Factory<Properties, FLOWING> flowingFactory, FluidDeferredRegister register, ResourceLocation stillRl,
            ResourceLocation flowingRl, ResourceLocation overlayRl) {
        this.stillFactory = stillFactory;
        this.flowingFactory = flowingFactory;
        this.register = register;
        this.name = name;
        attributes = FluidAttributes.builder(stillRl, flowingRl).overlay(overlayRl);
        fluidBlockBuilder = new FluidBlockBuilder<>(LiquidBlock::new);
        bucketBuilder = new BucketBuilder<>(BucketItem::new);
    }

    /**
     * Sets the colour of the fluid.
     * 
     * @param  colour the colour
     * @return        the builder instance
     */
    public FluidBuilder<STILL, FLOWING> colour(final int colour) {
        attributes.color(colour);
        return this;
    }

    /**
     * This method should be called when something from the fluids
     * {@link FluidAttributes} needs to be modified, but a method that does so
     * doesn't exist in {@link FluidBuilder}. <br>
     * <br>
     * It is preferred to chain method calls inside the consumer, instead of calling
     * {@link #modifyProperties(Consumer)} multiple times.
     * 
     * @param  consumer the consumer which will accept the current
     *                  {@link Properties} and modify them.
     * @return          the builder instance
     */
    public FluidBuilder<STILL, FLOWING> modifyAttributes(@Nonnull final Consumer<FluidAttributes.Builder> consumer) {
        consumer.accept(attributes);
        return this;
    }

    /**
     * Configures the block's bucket, using a normal {@link BlockItem}
     * 
     * @param  consumer a consumer that modifies the {@link BucketBuilder} in order
     *                  to fit your needs
     * @return          the builder instance
     */
    public FluidBuilder<STILL, FLOWING> bucket(@Nonnull final Consumer<BucketBuilder<BucketItem>> consumer) {
        consumer.accept(bucketBuilder);
        return this;
    }

    /**
     * Configures the block's bucket, using the specified {@code factory} in order
     * to instantiate it
     * 
     * @param  <I>      the class of the bucket
     * @param  factory  a factory which takes in a fluid supplier and
     *                  {@code Item.Properties} returning a bucket of the type
     *                  {@code I}
     * @param  consumer a consumer which will modify the created
     *                  {@link BucketBuilder} in order to fit your needs
     * @return          the builder instance
     */
    public <I extends BucketItem> FluidBuilder<STILL, FLOWING> bucket(final BucketFactory<STILL, I> factory,
            @Nonnull final Consumer<BucketBuilder<I>> consumer) {
        final var builder = new BucketBuilder<I>(factory::build);
        consumer.accept(builder);
        this.bucketBuilder = (BucketBuilder<BucketItem>) builder;
        return this;
    }

    /**
     * Configures the block's fluid block, using a normal {@link LiquidBlock}
     * 
     * @param  consumer a consumer that modifies the {@link FluidBlockBuilder} in
     *                  order to fit your needs
     * @return          the builder instance
     */
    public FluidBuilder<STILL, FLOWING> block(@Nonnull final Consumer<FluidBlockBuilder<LiquidBlock>> consumer) {
        consumer.accept(fluidBlockBuilder);
        return this;
    }

    /**
     * Configures the block's fluid block, using the specified {@code factory} in
     * order to instantiate it
     * 
     * @param  <B>      the class of the fluid block
     * @param  factory  a factory which takes in a fluid supplier and
     *                  {@code BlockBehaviour.Properties} returning a fluid block of
     *                  the type {@code B}
     * @param  consumer a consumer which will modify the created
     *                  {@link FluidBlockBuilder} in order to fit your needs
     * @return          the builder instance
     */
    public <B extends LiquidBlock> FluidBuilder<STILL, FLOWING> block(final FluidBlockFactory<STILL, B> factory,
            @Nonnull final Consumer<FluidBlockBuilder<B>> consumer) {
        final var builder = new FluidBlockBuilder<>(factory::build);
        consumer.accept(builder);
        this.fluidBlockBuilder = (FluidBlockBuilder<LiquidBlock>) builder;
        return this;
    }

    /**
     * Adds a tag to the fluid. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tag(s) to add
     * @return      the builder instance
     */
    @SafeVarargs
    public final FluidBuilder<STILL, FLOWING> addTag(Named<Fluid>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Adds tags to the fluid. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tags to add
     * @return      the builder instance
     */
    public FluidBuilder<STILL, FLOWING> addTag(List<Named<Fluid>> tags) {
        this.tags.addAll(tags);
        return this;
    }

    @Override
    public STILL get() {
        return registryObject.get();
    }

    @Override
    public FluidRegistryObject<STILL, FLOWING> build() {
        if (registryObject != null) { return registryObject; }
        // Don't use a method reference, in order to prevent NPEs!
        final Properties properties = new Properties(() -> registryObject.getStill(), () -> registryObject.getFlowing(),
                attributes);
        final RegistryObject<STILL> stillFluid = register.getRegister().register(name,
                () -> stillFactory.build(properties));
        final RegistryObject<FLOWING> flowingFluid = register.getRegister().register(name + "_flowing",
                () -> flowingFactory.build(properties));
        BlockRegistryObject<LiquidBlock> fluidBlock = null;
        if (fluidBlockBuilder != null) {
            fluidBlock = fluidBlockBuilder.build();
            properties.block(fluidBlock);
        }
        ItemRegistryObject<BucketItem> bucket = null;
        if (bucketBuilder != null) {
            bucket = bucketBuilder.build();
            properties.bucket(bucket);
        }
        registryObject = new FluidRegistryObject<>(stillFluid, flowingFluid,
                fluidBlock == null ? null : RegistryObject.of(fluidBlock.getId(), ForgeRegistries.BLOCKS),
                bucket == null ? null : RegistryObject.of(bucket.getId(), ForgeRegistries.ITEMS));
        tags.forEach(tag -> {
            register.tags.computeIfAbsent(tag, k -> Lists.newArrayList()).add(registryObject::getStill);
            register.tags.computeIfAbsent(tag, k -> Lists.newArrayList()).add(registryObject::getFlowing);
        });
        register.builders.add(this);
        return registryObject;
    }

    public class FluidBlockBuilder<B extends Block> extends BlockBuilder<B> {

        private final BiFunction<Supplier<STILL>, BlockBehaviour.Properties, B> fluidBlockFactory;

        FluidBlockBuilder(final BiFunction<Supplier<STILL>, BlockBehaviour.Properties, B> fluidBlockFactory) {
            super(FluidBuilder.this.name + "_bucket", null, FluidBuilder.this.register.blockRegister);
            this.noBlockItem();
            this.fluidBlockFactory = fluidBlockFactory;
            material(Material.WATER);
        }

        @Override
        public BlockBuilder<B> blockItem(Consumer<BlockBuilder<B>.BlockItemBuilder<BlockItem>> consumer) {
            throw new UnsupportedOperationException("A fluid block cannot have an item!");
        }

        @Override
        public <I extends BlockItem> BlockBuilder<B> blockItem(BlockItemFactory<B, I> factory,
                Consumer<BlockBuilder<B>.BlockItemBuilder<I>> consumer) {
            throw new UnsupportedOperationException("A fluid block cannot have an item!");
        }

        @Override
        public BlockRegistryObject<B> build() {
            if (this.registryObject != null) { return registryObject; }
            final var obj = register.getRegister().register(name,
                    () -> fluidBlockFactory.apply(FluidBuilder.this.registryObject::getStill, properties));
            this.registryObject = new BlockRegistryObject<>(obj);
            register.builders.add(this);
            return registryObject;
        }
    }

    @FunctionalInterface
    public static interface FluidBlockFactory<STILL extends ForgeFlowingFluid.Source, B extends LiquidBlock> {

        B build(Supplier<STILL> stillFluid, BlockBehaviour.Properties properties);
    }

    public class BucketBuilder<I extends Item> extends ItemBuilder<I> {

        private final BiFunction<Supplier<STILL>, Item.Properties, I> bucketFactory;

        BucketBuilder(final BiFunction<Supplier<STILL>, Item.Properties, I> bucketFactory) {
            super(null, FluidBuilder.this.register.itemRegister, FluidBuilder.this.name + "_bucket");
            this.bucketFactory = bucketFactory;
        }

        @Override
        public ItemRegistryObject<I> build() {
            if (this.registryObject != null) { return registryObject; }
            final var obj = register.getRegister().register(name,
                    () -> bucketFactory.apply(FluidBuilder.this.registryObject::getStill, this.properties));
            this.registryObject = new ItemRegistryObject<>(obj);
            register.builders.add(this);
            addDatagenStuff(obj);
            return registryObject;
        }

    }

    @FunctionalInterface
    public static interface BucketFactory<STILL extends ForgeFlowingFluid.Source, I extends BucketItem> {

        I build(Supplier<STILL> stillFluid, Item.Properties properties);
    }

}
