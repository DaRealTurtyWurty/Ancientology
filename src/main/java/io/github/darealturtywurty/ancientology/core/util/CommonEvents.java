package io.github.darealturtywurty.ancientology.core.util;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.data.*;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
import io.github.darealturtywurty.ancientology.core.init.FluidInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.worldgen.OreGeneration;
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
                OreGeneration.registerOres();
            });
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            final DataGenerator generator = event.getGenerator();
            final ExistingFileHelper fileHelper = event.getExistingFileHelper();
            
            BlockInit.BLOCKS.addDatagen(event);
            ItemInit.ITEMS.addDatagen(event);
            FluidInit.FLUIDS.addDatagen(event);
            EntityInit.ENTITIES.addDatagen(event);

            if (event.includeClient()) {
                generator.addProvider(new ItemModelGenerator(generator, fileHelper));
                generator.addProvider(new BlockstateGenerator(generator, fileHelper));

                generator.addProvider(new LanguageGenerator(generator)); //EN_US
                for (final var locale : MinecraftLocale.values()) { //Other Languages
                    if (locale == MinecraftLocale.EN_US) continue;
                    generator.addProvider(new LanguageGenerator.BuilderAddedKeys(generator, locale.getLocaleName()));
                }
            }

            if (event.includeServer()) {
                final var blockTags = new BlockTagsGenerator(generator, fileHelper);
                generator.addProvider(blockTags);
                generator.addProvider(new ItemTagsGenerator(generator, blockTags, fileHelper));
                generator.addProvider(new RecipeGenerator(generator));
                generator.addProvider(new LootTableGenerator(generator));
            }
        }
    }
}