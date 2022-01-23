package io.github.darealturtywurty.ancientology.client.util;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.client.render.renderers.AngeoaRenderer;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
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
            
        }

        @SubscribeEvent
        public static void registerRenderers(RegisterRenderers event) {
            event.registerEntityRenderer(EntityInit.ANGEOA.get(), AngeoaRenderer::new);
        }
    }
}
