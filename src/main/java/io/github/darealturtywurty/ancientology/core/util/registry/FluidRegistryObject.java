package io.github.darealturtywurty.ancientology.core.util.registry;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;

import io.github.darealturtywurty.ancientology.core.util.interfaces.BlockProvider;
import io.github.darealturtywurty.ancientology.core.util.interfaces.FluidProvider;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

public class FluidRegistryObject<STILL extends ForgeFlowingFluid.Source, FLOWING extends ForgeFlowingFluid.Flowing>
        extends WrappedRegistryObject<STILL> implements FluidProvider, BlockProvider {

    private final RegistryObject<FLOWING> flowingFluid;
    @Nullable
    private final RegistryObject<LiquidBlock> fluidBlock;
    @Nullable
    private final RegistryObject<BucketItem> bucket;

    protected FluidRegistryObject(RegistryObject<STILL> stillFluid, RegistryObject<FLOWING> flowingFluid,
            @Nullable RegistryObject<LiquidBlock> fluidBlock, @Nullable RegistryObject<BucketItem> bucket) {
        super(stillFluid);
        this.fluidBlock = fluidBlock;
        this.flowingFluid = flowingFluid;
        this.bucket = bucket;
    }

    public FLOWING getFlowing() {
        return flowingFluid.get();
    }

    public STILL getStill() {
        return registryObject.get();
    }

    @Override
    public Fluid asFluid() {
        return registryObject.get();
    }

    /**
     * Gets the bucket of this fluid, if it exists
     */
    @Override
    public Item asItem() {
        if (bucket == null) { return null; }
        return bucket.get();
    }

    @Override
    public Block asBlock() {
        if (fluidBlock == null) { return null; }
        return fluidBlock.get();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return FluidProvider.super.getRegistryName();
    }

    @Override
    public String getTranslationKey() {
        return FluidProvider.super.getTranslationKey();
    }

}
