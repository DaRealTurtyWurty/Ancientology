package io.github.darealturtywurty.ancientology.client.util;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.client.render.renderers.JumprasherBER;
import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientEvents {
    private ClientEvents() {
        throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
    }

    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
    public static final class ForgeEvents {
        private ForgeEvents() {
            throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
        }
    }

    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.MOD, value = Dist.CLIENT)
    public static final class ModEvents {
        private ModEvents() {
            throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.LIFE_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.LIFE_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.JUMPRASHER.get(), RenderType.cutoutMipped());
        }

        @SubscribeEvent
        public static void registerRenderers(final RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityInit.JUMPRASHER.get(), JumprasherBER::new);
        }
    }
}
