package io.github.darealturtywurty.ancientology.core.init;

import net.minecraft.tags.FluidTags;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.FluidDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.FluidRegistryObject;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;

public class FluidInit {

    public static final FluidDeferredRegister FLUIDS = FluidDeferredRegister.create(Ancientology.MODID, ItemInit.ITEMS,
            BlockInit.BLOCKS);

    public static final FluidRegistryObject<Source, Flowing> TEST = FLUIDS.register("test", Source::new, Flowing::new)
            .bucket(b -> b.lang("Test bucket")).block(b -> b.modifyProperties(p -> p.lightLevel(state -> 15)))
            .addTag(FluidTags.WATER, FluidTags.LAVA).build();

    private FluidInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }

}
