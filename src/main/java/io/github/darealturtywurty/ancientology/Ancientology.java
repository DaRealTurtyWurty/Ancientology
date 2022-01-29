package io.github.darealturtywurty.ancientology;

import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.init.MobEffectInit;
import io.github.darealturtywurty.ancientology.core.init.RecipeInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

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
        GeckoLib.initialize();
        
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
}
