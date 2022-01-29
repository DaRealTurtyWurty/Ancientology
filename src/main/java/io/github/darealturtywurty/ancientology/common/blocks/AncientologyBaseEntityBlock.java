package io.github.darealturtywurty.ancientology.common.blocks;

import io.github.darealturtywurty.ancientology.common.blockentities.TickableBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AncientologyBaseEntityBlock extends BaseEntityBlock {
    protected AncientologyBaseEntityBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState pState,
            BlockEntityType<T> pBlockEntityType) {
        return level.isClientSide ? ($, $1, $2, be) -> ((TickableBlockEntity) be).clientTick()
                : ($, $1, $2, be) -> ((TickableBlockEntity) be).serverTick();
    }
}
