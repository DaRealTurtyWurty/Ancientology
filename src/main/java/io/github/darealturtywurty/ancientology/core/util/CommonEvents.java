package io.github.darealturtywurty.ancientology.core.util;

import java.util.Objects;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.MobEffectInit;
import io.github.darealturtywurty.ancientology.core.worldgen.FeatureGen;
import io.github.darealturtywurty.ancientology.core.worldgen.OreGeneration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CommonEvents {
    private CommonEvents() {
        throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
    }
    
    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.FORGE)
    public static final class ForgeEvents {
        private ForgeEvents() {
            throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
        }
        
        @SubscribeEvent
        public static void removeFlightEffect(PotionEvent.PotionRemoveEvent e) {
            if (Objects.requireNonNull(e.getPotionEffect()).getEffect().equals(MobEffectInit.FLIGHT.get())
                    && e.getEntityLiving() instanceof final Player player) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.getAbilities().setFlyingSpeed(0.05F);
            }
        }
    }
    
    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.MOD)
    public static final class ModEvents {
        private ModEvents() {
            throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
        }
        
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                FeatureGen.registerFeatures();
                OreGeneration.registerOres();
            });
        }
    }
}