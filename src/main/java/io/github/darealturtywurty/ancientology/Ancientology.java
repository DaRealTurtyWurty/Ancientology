package io.github.darealturtywurty.ancientology;

import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
import io.github.darealturtywurty.ancientology.core.init.FluidInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(Ancientology.MODID)
public class Ancientology {

    public static final String MODID = "ancientology";

    public static final CreativeModeTab ANCIENTOLOGY_ITEM_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            // TODO: add an icon
            return ItemStack.EMPTY;
        }
    };

    public Ancientology() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        
        GeckoLib.initialize();

        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        FluidInit.FLUIDS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITIES.register(bus);
    }
}
