package io.github.darealturtywurty.ancientology;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
import io.github.darealturtywurty.ancientology.core.init.FluidInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.init.MobEffectInit;
import io.github.darealturtywurty.ancientology.core.init.RecipeInit;
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
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        RecipeInit.RECIPES.register(bus);
        FluidInit.FLUIDS.register(bus);
        EntityInit.ENTITIES.register(bus);
        MobEffectInit.MOB_EFFECTS.register(bus);
    }

    private void initGecko() throws ClassNotFoundException {
        GeckoLib.initialize();
    }

    public static ResourceLocation rl(final String name) {
        return new ResourceLocation(MODID, name);
    }
}
