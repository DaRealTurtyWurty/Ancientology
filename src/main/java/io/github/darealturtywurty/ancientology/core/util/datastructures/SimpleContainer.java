package io.github.darealturtywurty.ancientology.core.util.datastructures;

import com.google.common.collect.Lists;
import io.github.darealturtywurty.ancientology.core.util.interfaces.OnChangedListener;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleContainer implements Container, IItemHandler, WorldlyContainer, INBTSerializable<CompoundTag> {
    protected final ItemStack[] contents;
    private final int stackLimit;
    private final List<OnChangedListener> onChangedListeners = Lists.newLinkedList();
    
    /**
     * This value is incremented every time the {@link #setChanged()} is called
     */
    private int hash;
    private final InvWrapper wrapper;
    
    public SimpleContainer(int size, int stackLimit) {
        this.contents = new ItemStack[size];
        Arrays.fill(this.contents, ItemStack.EMPTY);
        this.stackLimit = stackLimit;
        this.wrapper = new InvWrapper(this);
    }
    
    public synchronized void addOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListeners.add(onChangedListener);
    }
    
    @Override
    public boolean canPlaceItem(int i, ItemStack itemstack) {
        return i < getContainerSize() && i >= 0;
    }
    
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return true;
    }
    
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
    
    @Override
    public void clearContent() {
        for (int i = 0; i < getContainerSize(); i++) {
            this.contents[i] = ItemStack.EMPTY;
        }
    }
    
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final var items = nbt.contains("Items", net.minecraft.nbt.Tag.TAG_COMPOUND) ? nbt.getCompound("Items")
                : new CompoundTag();
        
        for (int index = 0; index < getContainerSize(); index++) {
            this.contents[index] = ItemStack.EMPTY;
            final var indexStr = String.valueOf(index);
            final CompoundTag slotNBT = items.contains(indexStr, Tag.TAG_COMPOUND) ? items.getCompound(indexStr)
                    : new CompoundTag();
            if (!slotNBT.isEmpty()) {
                final var newStack = ItemStack.of(slotNBT);
                if (slotNBT.contains("ActualCount", Tag.TAG_INT)) {
                    newStack.setCount(slotNBT.getInt("ActualCount"));
                }
                this.contents[index] = newStack;
            }
        }
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.wrapper.extractItem(slot, amount, simulate);
    }
    
    @Override
    public int getContainerSize() {
        return this.contents.length;
    }
    
    @Override
    public ItemStack getItem(int pIndex) {
        return this.contents[pIndex];
    }
    
    public IItemHandler getItemHandler() {
        return new InvWrapper(this);
    }
    
    public IItemHandler getItemHandlerSided(Direction side) {
        return new SidedInvWrapper(this, side);
    }
    
    /*
     * Get the array of {@link net.minecraft.item.ItemStack} inside this inventory.
     *
     * @return The items in this inventory.
     */

    public ItemStack[] getItemStacks() {
        return this.contents;
    }
    
    @Override
    public int getMaxStackSize() {
        return this.stackLimit;
    }
    
    @Override
    public int getSlotLimit(int slot) {
        return this.wrapper.getSlotLimit(slot);
    }
    
    @Override
    public int getSlots() {
        return getContainerSize();
    }
    
    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, getContainerSize()).toArray();
    }
    
    @Override
    public ItemStack getStackInSlot(int slotId) {
        return this.contents[slotId];
    }
    
    public int getStackLimit() {
        return this.stackLimit;
    }
    
    /**
     * @return The inventory state.
     */
    public int getState() {
        return this.hash;
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return this.wrapper.insertItem(slot, stack, simulate);
    }
    
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            if (!getItem(i).isEmpty())
                return false;
        }
        return true;
    }
    
    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.wrapper.isItemValid(slot, stack);
    }
    
    @Override
    public ItemStack removeItem(int slotId, int count) {
        final ItemStack stack = getItem(slotId);
        if (slotId < getContainerSize() && !stack.isEmpty()) {
            if (stack.getCount() > count) {
                final ItemStack slotContents = stack.copy();
                final ItemStack result = slotContents.split(count);
                setItem(slotId, slotContents);
                return result;
            }
            setItem(slotId, ItemStack.EMPTY);
            setChanged();
            return stack;
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public ItemStack removeItemNoUpdate(int slotId) {
        final ItemStack stackToTake = getItem(slotId);
        if (stackToTake.isEmpty())
            return ItemStack.EMPTY;
        
        setItem(slotId, ItemStack.EMPTY);
        return stackToTake;
    }
    
    @Override
    public CompoundTag serializeNBT() {
        final var nbt = new CompoundTag();
        final CompoundTag items = new CompoundTag();
        for (int index = 0; index < getContainerSize(); index++) {
            final ItemStack itemStack = getItem(index);
            if (!itemStack.isEmpty() && itemStack.getCount() > 0) {
                final CompoundTag slotNBT = new CompoundTag();
                itemStack.save(slotNBT);
                if (itemStack.getCount() > itemStack.getMaxStackSize()) {
                    slotNBT.putInt("ActualCount", itemStack.getCount());
                }
                items.put(String.valueOf(index), slotNBT);
            }
        }
        nbt.put("Items", items);
        return nbt;
    }
    
    @Override
    public void setChanged() {
        this.hash++;
        List<OnChangedListener> dirtyMarkListeners;
        synchronized (this) {
            dirtyMarkListeners = Lists.newLinkedList(this.onChangedListeners);
        }
        for (final OnChangedListener dirtyMarkListener : dirtyMarkListeners) {
            dirtyMarkListener.setChanged();
        }
    }
    
    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.contents[pIndex] = pStack;
    }
    
    @Override
    public void startOpen(Player playerIn) {
        
    }
    
    @Override
    public boolean stillValid(Player entityplayer) {
        return true;
    }
    
    @Override
    public void stopOpen(Player playerIn) {
        
    }
}
