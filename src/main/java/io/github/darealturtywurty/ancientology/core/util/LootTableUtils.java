package io.github.darealturtywurty.ancientology.core.util;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class LootTableUtils extends BlockLoot {

    private LootTableUtils() {
        throw new IllegalAccessError("Illegal access to hidden utility class!");
    }

    public static LootTable.Builder createSingleItemTable(ItemLike item) {
        return LootTable.lootTable().withPool(applyExplosionCondition(item,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item))));
    }

}
