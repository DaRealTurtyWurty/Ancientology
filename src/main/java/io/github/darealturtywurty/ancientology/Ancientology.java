package io.github.darealturtywurty.ancientology;

import io.github.darealturtywurty.ancientology.core.init.*;
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
        try { initGecko(); } catch (ClassNotFoundException ignored) { /*Should catch in server as Gecko is client only, mods.toml handles the rest*/ }

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        FluidInit.FLUIDS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITIES.register(bus);
        MobEffectInit.MOB_EFFECTS.register(bus);
    }

    private void initGecko() throws ClassNotFoundException {
        GeckoLib.initialize();
    }
}
