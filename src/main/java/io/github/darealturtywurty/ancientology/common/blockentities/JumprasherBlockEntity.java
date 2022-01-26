package io.github.darealturtywurty.ancientology.common.blockentities;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import io.github.darealturtywurty.ancientology.common.blocks.JumprasherBlock;
import io.github.darealturtywurty.ancientology.common.recipes.JumprasherRecipe;
import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.RecipeInit;
import io.github.darealturtywurty.ancientology.core.util.datastructures.SimpleContainer;
import io.github.darealturtywurty.ancientology.core.util.datastructures.SingleCache;
import io.github.darealturtywurty.ancientology.core.util.datastructures.SingleCache.ICacheUpdater;
import io.github.darealturtywurty.ancientology.core.util.helper.CraftingHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class JumprasherBlockEntity extends BlockEntity implements TickableBlockEntity {

    private int itemHeight = 1;
    private final SingleCache<ItemStack, Optional<JumprasherRecipe>> recipeCache;
    private final SimpleContainer inventory = new SimpleContainer(1, 1) {

        @Override
        public boolean canPlaceItem(int slot, ItemStack itemStack) {
            return getLevel().getBlockState(getBlockPos()).getValue(JumprasherBlock.HEIGHT) == 7 && getItem(0).isEmpty()
                    && super.canPlaceItem(slot, itemStack);
        }

        @Override
        public void setItem(int pIndex, ItemStack pStack) {
            super.setItem(pIndex, pStack);
            itemHeight = 1;
            sendUpdate();
        };
    };
    private final LazyOptional<IItemHandler> invOptional = LazyOptional.of(() -> inventory);

    public JumprasherBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.JUMPRASHER.get(), pWorldPosition, pBlockState);

        recipeCache = new SingleCache<>(new ICacheUpdater<ItemStack, Optional<JumprasherRecipe>>() {

            @Override
            public Optional<JumprasherRecipe> getNewValue(ItemStack key) {
                return CraftingHelper.findRecipe(RecipeInit.JUMPRASHER.asRecipeType(), level,
                        r -> r.input().test(inventory.getItem(0)));
            }

            @Override
            public boolean isKeyEqual(ItemStack cacheKey, ItemStack newKey) {
                return ItemStack.matches(cacheKey, newKey);
            }

        });
    }

    public Optional<JumprasherRecipe> getCurrentRecipe() {
        return recipeCache.get(inventory.getStackInSlot(0).copy());
    }

    @Override
    public void serverTick() {
        final var height = getBlockState().getValue(JumprasherBlock.HEIGHT);
        if (height == 1) {
            final var currentRecipe = getCurrentRecipe();
            if (currentRecipe.isPresent()) {
                final var recipe = currentRecipe.get();
                inventory.setItem(0, ItemStack.EMPTY);
                for (final var itemStackChance : recipe.outputs()) {
                    if (itemStackChance.chance() == 100f || itemStackChance.chance() < level.random.nextFloat(100)) {
                        ItemStack resultStack = itemStackChance.itemStack().copy();
                        for (Direction side : Direction.values()) {
                            if (!resultStack.isEmpty() && side != Direction.UP) {
                                final var be = level.getBlockEntity(worldPosition.relative(side));
                                IItemHandler itemHandler = null;
                                if (be != null) {
                                    itemHandler = be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                            .orElse(null);
                                    if (itemHandler != null) {
                                        for (int i = 0; i < itemHandler.getSlots(); i++) {
                                            if (!resultStack.isEmpty()) {
                                                resultStack = itemHandler.insertItem(i, resultStack, false);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!resultStack.isEmpty()) {
                            level.addFreshEntity(new ItemEntity(level, worldPosition.getX(), worldPosition.getY(),
                                    worldPosition.getZ(), resultStack));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inventory.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) { return invOptional.cast(); }
        return super.getCapability(cap, side);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
        getInventory().setChanged();
        sendUpdate();
    }

    public void sendUpdate() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("itemHeight", itemHeight);
        tag.put("inventory", inventory.serializeNBT());
        return ClientboundBlockEntityDataPacket.create(this, e -> tag);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(connection, pkt);
        CompoundTag tag = pkt.getTag();
        this.itemHeight = tag.getInt("itemHeight");
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        itemHeight = tag.getInt("itemHeight");
    }

    @Override
    public CompoundTag getUpdateTag() {
        final var tag = super.getUpdateTag();
        tag.putInt("itemHeight", itemHeight);
        tag.put("inventory", inventory.serializeNBT());
        return tag;
    }
}
