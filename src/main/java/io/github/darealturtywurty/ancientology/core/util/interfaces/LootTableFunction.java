package io.github.darealturtywurty.ancientology.core.util.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable.Builder;

@FunctionalInterface
public interface LootTableFunction {

    @Nonnull
    Builder makeLootTable(@Nonnull final Block block);

}
