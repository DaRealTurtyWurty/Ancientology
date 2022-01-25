package io.github.darealturtywurty.ancientology.common.blocks;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import io.github.darealturtywurty.ancientology.common.blockentities.JumprasherBlockEntity;
import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import net.minecraftforge.items.CapabilityItemHandler;

public final class JumprasherBlock extends AncientologyBaseEntityBlock {

    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 1, 7); // 1 is lowest, 7 is highest; 7
                                                                                         // is no progress, 1 is
                                                                                         // progress complete

    private static VoxelShape makeShape(final VoxelShape topPartShape) {
        return Stream.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(0, 0, 0, 1, 16, 1), Block.box(0, 0, 15, 1, 16, 16),
                Block.box(15, 0, 0, 16, 16, 1), Block.box(15, 0, 1, 16, 16, 2), Block.box(0, 0, 14, 1, 16, 15),
                Block.box(14, 0, 0, 15, 16, 1), Block.box(1, 0, 15, 2, 16, 16), Block.box(14, 0, 15, 15, 16, 16),
                Block.box(15, 0, 14, 16, 16, 15), Block.box(15, 0, 15, 16, 16, 16), Block.box(0, 0, 1, 1, 16, 2),
                Block.box(1, 0, 0, 2, 16, 1), Block.box(0, 1, 2, 1, 16, 14), Block.box(15, 1, 2, 16, 16, 14),
                Block.box(2, 1, 0, 14, 16, 1), Block.box(2, 1, 15, 14, 16, 16), topPartShape)
                .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    public static final VoxelShape[] SHAPES = new VoxelShape[] {
            makeShape(Block.box(1, 1, 1, 15, 2, 15)), // 1
            makeShape(Block.box(1, 3, 1, 15, 4, 15)), // 2
            makeShape(Block.box(1, 5, 1, 15, 6, 15)), // 3
            makeShape(Block.box(1, 7, 1, 15, 8, 15)), // 4
            makeShape(Block.box(1, 9, 1, 15, 10, 15)), // 5
            makeShape(Block.box(1, 11, 1, 15, 12, 15)), // 6
            makeShape(Block.box(1, 15, 1, 15, 16, 15)) // 7
    };

    public JumprasherBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(HEIGHT, 7));
    }

    @Override
    protected void createBlockStateDefinition(
            net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HEIGHT);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[pState.getValue(HEIGHT) - 1];
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPES[pState.getValue(HEIGHT) - 1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
            CollisionContext pContext) {
        return SHAPES[pState.getValue(HEIGHT) - 1];
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JumprasherBlockEntity(pPos, pState);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        double motionY = entity.getDeltaMovement().y;
        super.updateEntityAfterFallOn(level, entity);
        if (!entity.getLevel().isClientSide() && motionY <= -0.37D && entity instanceof LivingEntity) {
            // Same way of deriving blockPos as is done in Entity#moveEntity
            int i = Mth.floor(entity.getX());
            int j = Mth.floor(entity.getY() - 0.2D);
            int k = Mth.floor(entity.getZ());
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);

            // The faster the entity is falling, the more steps to advance by
            int steps = 1 + Mth.floor((-motionY - 0.37D) * 5);

            final int newHeight = blockState.getValue(HEIGHT) - steps < 1 ? 1 : blockState.getValue(HEIGHT) - steps;
            entity.getLevel().setBlockAndUpdate(blockPos, blockState.setValue(HEIGHT, newHeight));
            level.getBlockEntity(blockPos, BlockEntityInit.JUMPRASHER.get()).ifPresent(jumprasher -> {
                jumprasher.setItemHeight(Math.max(jumprasher.getItemHeight(), 8 - newHeight));
            });
        }
    }

    public static float getRelativePositionTop(BlockState blockState) {
        return (9 - blockState.getValue(HEIGHT)) * 0.125F;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos,
            boolean isMoving) {
        super.neighborChanged(state, level, pos, neighborBlock, fromPos, isMoving);
        if (!level.isClientSide) {
            for (Direction direction : Direction.values()) {
                if (level.hasSignal(pos.relative(direction), direction)) {
                    level.setBlockAndUpdate(pos, state.setValue(HEIGHT, 7));
                    for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(pos, pos.offset(1, 1, 1)))) {
                        entity.getDeltaMovement().add(0, 0.25F, 0);
                        entity.setDeltaMovement(0, 1, 0);
                    }
                    level.getBlockEntity(pos, BlockEntityInit.JUMPRASHER.get()).ifPresent(j -> j.setItemHeight(1));
                    return;
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState pState, Builder pBuilder) {
        final var drops = super.getDrops(pState, pBuilder);
        final var be = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be != null) {
            be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
                for (int i = 0; i < cap.getSlots(); i++) {
                    drops.add(cap.getStackInSlot(i));
                }
            });
        }
        return drops;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand,
            BlockHitResult pHit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (state.getValue(HEIGHT) == 7) {
            return level.getBlockEntity(pos, BlockEntityInit.JUMPRASHER.get()).map(jumprasher -> {
                ItemStack itemStack = player.getInventory().getSelected();
                ItemStack tileStack = jumprasher.getInventory().getStackInSlot(0);

                if (itemStack.isEmpty() && !tileStack.isEmpty()) {
                    player.getInventory().setItem(player.getInventory().selected, tileStack);
                    jumprasher.getInventory().setItem(0, ItemStack.EMPTY);
                    jumprasher.setChanged();
                    return InteractionResult.SUCCESS;
                } else if (player.getInventory().add(tileStack)) {
                    jumprasher.getInventory().setItem(0, ItemStack.EMPTY);
                    jumprasher.setChanged();
                    return InteractionResult.SUCCESS;
                } else if (!itemStack.isEmpty() && jumprasher.getInventory().getItem(0).isEmpty()) {
                    jumprasher.getInventory().setItem(0, itemStack.split(1));
                    if (itemStack.getCount() <= 0)
                        player.getInventory().setItem(player.getInventory().selected, ItemStack.EMPTY);
                    jumprasher.setChanged();
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }).orElse(InteractionResult.PASS);
        }
        return InteractionResult.PASS;
    }

}
