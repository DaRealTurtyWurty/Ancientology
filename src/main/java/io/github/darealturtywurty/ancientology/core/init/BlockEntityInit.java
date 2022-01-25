package io.github.darealturtywurty.ancientology.core.init;

import net.minecraft.world.level.block.entity.BlockEntityType;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.blockentities.JumprasherBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, Ancientology.MODID);

    public static final RegistryObject<BlockEntityType<JumprasherBlockEntity>> JUMPRASHER = BLOCK_ENTITIES
            .register(BlockInit.JUMPRASHER.getId().getNamespace(), () -> BlockEntityType.Builder
                    .of(JumprasherBlockEntity::new, BlockInit.JUMPRASHER.get()).build(null));

    private BlockEntityInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
