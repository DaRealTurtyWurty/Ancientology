package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, Ancientology.MODID);

    private BlockEntityInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
