package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidDeferredRegister extends DeferredRegisterWrapper<Fluid> {

    final Map<Named<Fluid>, List<Supplier<Fluid>>> tags = new HashMap<>();
    final List<FluidBuilder<?, ?>> builders = new ArrayList<>();
    
    private final boolean areOtherRegistersManual;
    final ItemDeferredRegister itemRegister;
    final BlockDeferredRegister blockRegister;

    private FluidDeferredRegister(String modid) {
        super(ForgeRegistries.FLUIDS, modid);
        this.itemRegister = ItemDeferredRegister.create(modid);
        this.blockRegister = BlockDeferredRegister.create(modid, itemRegister);
        this.areOtherRegistersManual = false;
    }

    private FluidDeferredRegister(String modid, ItemDeferredRegister itemDeferredRegister,
            BlockDeferredRegister blockDeferredRegister) {
        super(ForgeRegistries.FLUIDS, modid);
        this.itemRegister = itemDeferredRegister;
        this.blockRegister = blockDeferredRegister;
        this.areOtherRegistersManual = true;
    }

    public static FluidDeferredRegister create(String modid) {
        return new FluidDeferredRegister(modid);
    }

    public static FluidDeferredRegister create(String modid, ItemDeferredRegister itemDeferredRegister,
            BlockDeferredRegister blockDeferredRegister) {
        return new FluidDeferredRegister(modid, itemDeferredRegister, blockDeferredRegister);
    }

    /**
     * Prepares a fluid to be registered. The registering will happen when
     * {@link FluidBuilder#build()} is called, which will also return the
     * {@link FluidRegistryObject} containing that fluid, and if applicable, its
     * block and bucket. The registry object will be safe to call after
     * {@link FMLCommonSetupEvent}.
     * 
     * @param  <STILL>        the class of the still fluid
     * @param  <FLOWING>      the class of the flowing fluid
     * @param  name           the registry name of the fluid (the bucket will be
     *                        called {@code name + "_bucket"})
     * @param  stillFactory   a factory which takes in {@link Properties} and
     *                        returns the still fluid. For normal fluids, use
     *                        {@code ForgeFlowingFluid.Source::new}
     * @param  flowingFactory a factory which takes in {@link Properties} and
     *                        returns the flowing fluid. For normal fluids, use
     *                        {@code ForgeFlowingFluid.Flowing::new}
     * @return
     */
    public <STILL extends Source, FLOWING extends Flowing> FluidBuilder<STILL, FLOWING> register(final String name,
            final Factory<Properties, STILL> stillFactory, final Factory<Properties, FLOWING> flowingFactory) {
        return new FluidBuilder<>(name, stillFactory, flowingFactory, this);
    }

    @Override
    public void register(IEventBus modBus) {
        super.register(modBus);
        if (!areOtherRegistersManual) {
            blockRegister.register(modBus);
            itemRegister.register(modBus);
        }
    }

    @Override
    public void addDatagen(GatherDataEvent event) {
        final var gen = event.getGenerator();
        final var existingFileHelper = event.getExistingFileHelper();
        if (!areOtherRegistersManual) {
            itemRegister.addDatagen(event);
            blockRegister.addDatagen(event);
        }
        gen.addProvider(new TagsProvider(gen, existingFileHelper));
    }

    private final class TagsProvider extends FluidTagsProvider {

        public TagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, FluidDeferredRegister.this.modId, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tags.forEach((tag, fluid) -> tag(tag).add(fluid.stream().map(Supplier::get).toArray(Fluid[]::new)));
        }

    }

}
