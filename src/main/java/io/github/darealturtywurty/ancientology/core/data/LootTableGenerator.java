package io.github.darealturtywurty.ancientology.core.data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.ForgeRegistries;

public class LootTableGenerator extends LootTableProvider {

    public LootTableGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(Pair.of(Blocks::new, LootContextParamSets.BLOCK),
                Pair.of(Entities::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {
        map.forEach(
                (resourceLocation, lootTable) -> LootTables.validate(validationTracker, resourceLocation, lootTable));
    }

    public static class Blocks extends BlockLoot {
        @Override
        protected void addTables() {
            
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(block -> Ancientology.MODID.equals(block.getRegistryName().getNamespace())).toList();
        }
    }

    public static class Entities extends EntityLoot {
        @Override
        protected void addTables() {
            
        }

        @Override
        protected @NotNull Iterable<EntityType<?>> getKnownEntities() {
            return ForgeRegistries.ENTITIES.getValues().stream()
                    .filter(entityType -> Ancientology.MODID.equals(entityType.getRegistryName().getNamespace()))
                    .toList();
        }
    }
}
