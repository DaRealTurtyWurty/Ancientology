package io.github.darealturtywurty.ancientology.common.blocks;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import io.github.darealturtywurty.ancientology.common.blockentities.TickableBlockEntity;

public abstract class AncientologyBaseEntityBlock extends BaseEntityBlock {

    protected AncientologyBaseEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState pState,
            BlockEntityType<T> pBlockEntityType) {
        return level.isClientSide ? ($, $1, $2, be) -> ((TickableBlockEntity) be).clientTick()
                : ($, $1, $2, be) -> ((TickableBlockEntity) be).serverTick();
    }

}
