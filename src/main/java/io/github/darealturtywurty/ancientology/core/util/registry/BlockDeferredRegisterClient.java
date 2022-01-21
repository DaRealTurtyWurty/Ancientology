package io.github.darealturtywurty.ancientology.core.util.registry;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import io.github.darealturtywurty.ancientology.core.util.registry.BlockBuilder.RenderLayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

record BlockDeferredRegisterClient(BlockDeferredRegister register) {

    @SubscribeEvent
    public void onClientSetup(final FMLClientSetupEvent event) {
        register.builders.forEach(builder -> {
            if (builder.renderLayer != null) {
                ItemBlockRenderTypes.setRenderLayer(builder.get(), getRenderType(builder.renderLayer));
            }
        });
    }

    public static RenderType getRenderType(final RenderLayer renderLayer) {
        return switch (renderLayer) {
        case CUTOUT -> RenderType.cutout();
        case CUTOUT_MIPPED -> RenderType.cutoutMipped();
        default -> null;
        };
    }

}
