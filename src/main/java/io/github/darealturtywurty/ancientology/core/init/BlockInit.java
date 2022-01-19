package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Ancientology.MODID);
    
    private BlockInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
