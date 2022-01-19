package io.github.darealturtywurty.ancientology.core.util;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.data.BlockTagsGenerator;
import io.github.darealturtywurty.ancientology.core.data.BlockstateGenerator;
import io.github.darealturtywurty.ancientology.core.data.ItemModelGenerator;
import io.github.darealturtywurty.ancientology.core.data.LanguageGenerator;
import io.github.darealturtywurty.ancientology.core.data.LootTableGenerator;
import io.github.darealturtywurty.ancientology.core.data.RecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public final class CommonEvents {
    private CommonEvents() {
        throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
    }

    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.FORGE)
    public static final class ForgeEvents {
        private ForgeEvents() {
            throw new IllegalAccessError("Illegal access to hidden event bus subscriber class!");
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

            });
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            final DataGenerator generator = event.getGenerator();
            final ExistingFileHelper fileHelper = event.getExistingFileHelper();
            
            if (event.includeServer()) {
                generator.addProvider(new BlockTagsGenerator(generator, fileHelper));
                generator.addProvider(new RecipeGenerator(generator));
                generator.addProvider(new LootTableGenerator(generator));
            }
            
            if (event.includeClient()) {
                generator.addProvider(new ItemModelGenerator(generator, fileHelper));
                generator.addProvider(new BlockstateGenerator(generator, fileHelper));
                generator.addProvider(new LanguageGenerator(generator, "en_us"));
            }
        }
    }
}