package io.github.darealturtywurty.ancientology;

import io.github.darealturtywurty.ancientology.core.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ancientology.MODID)
public class Ancientology {
    public static final String MODID = "ancientology";
    
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return ItemInit.FLINT_AXE.get().getDefaultInstance();
        }
    };
    
    public Ancientology() {
        try { initGecko(); } catch (NoClassDefFoundError ignored) {}
        
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        RecipeInit.RECIPES.register(bus);
        EntityInit.ENTITIES.register(bus);
        MobEffectInit.MOB_EFFECTS.register(bus);
    }
    
    public static ResourceLocation rl(final String name) {
        return new ResourceLocation(MODID, name);
    }

    //The whole point of this is to prevent servers from crashing when they don't have geckolib
    //Servers don't need geckolib as it's a client side only mod, not loading this on servers won't cause issues
    private void initGecko() throws NoClassDefFoundError {
        software.bernie.geckolib3.GeckoLib.initialize();
    }
}
