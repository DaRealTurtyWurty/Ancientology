package io.github.darealturtywurty.ancientology.core.util.registry.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import io.github.darealturtywurty.ancientology.core.util.registry.DeferredRegisterWrapper;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityDeferredRegister extends DeferredRegisterWrapper<EntityType<?>> {

    final Map<Named<EntityType<?>>, List<Supplier<EntityType<?>>>> tags = new HashMap<>();
    final Map<Supplier<EntityType<?>>, LootTable.Builder> lootTables = new HashMap<>();
    final List<EntityBuilder<?>> builders = new ArrayList<>();

    final ItemDeferredRegister itemRegister;
    private final boolean isItemRegisterManual;

    private EntityDeferredRegister(String modId) {
        super(ForgeRegistries.ENTITIES, modId);
        this.itemRegister = ItemDeferredRegister.create(modId);
        this.isItemRegisterManual = false;
    }

    private EntityDeferredRegister(String modid, ItemDeferredRegister itemDeferredRegister) {
        super(ForgeRegistries.ENTITIES, modid);
        this.itemRegister = itemDeferredRegister;
        this.isItemRegisterManual = true;
    }

    public static EntityDeferredRegister create(String modid) {
        return new EntityDeferredRegister(modid);
    }

    public static EntityDeferredRegister create(String modid, ItemDeferredRegister itemDeferredRegister) {
        return new EntityDeferredRegister(modid, itemDeferredRegister);
    }

    /**
     * Prepares an {@link EntityType} to be registered. The registering will happen
     * when {@link EntityBuilder#build()} is called, which will also return the
     * {@link EntityRegistryObject} containing that entity and, if applicable, its
     * spawn egg. The registry object will be safe to call after
     * {@link FMLCommonSetupEvent}.
     * 
     * @param  <I>     the class of the entity
     * @param  name    the registry name of the entity
     * @param  factory a factory which creates the entity
     * @return         an {@link EntityBuilder} which can be configured using
     *                 method-chaining, and whose entity will be registered when
     *                 {@link EntityBuilder#build()} is be called
     */
    public <E extends Entity> EntityBuilder<E> register(final String name, final EntityFactory<E> factory) {
        return new EntityBuilder<>(name, factory, this);
    }

    @Override
    public void addDatagen(GatherDataEvent event) {
        final var gen = event.getGenerator();
        final var existingFileHelper = event.getExistingFileHelper();
        if (event.includeServer()) {
            gen.addProvider(new TagsProvider(gen, existingFileHelper));
            gen.addProvider(new LootTableProvider(gen));
        }
        if (!isItemRegisterManual) {
            itemRegister.addDatagen(event);
        }
    }

    @Override
    public void register(IEventBus modBus) {
        super.register(modBus);
        modBus.addListener(this::registerAttributes);
        if (!isItemRegisterManual) {
            itemRegister.register(modBus);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerAttributes(final EntityAttributeCreationEvent event) {
        builders.forEach(builder -> {
            final var entityType = builder.get();
            if (builder.attributes != null) {
                // If the entity is not living, but it has attributes, it's your fault!
                event.put((EntityType<LivingEntity>) entityType, builder.attributes.build());
            }
        });
    }

    private final class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

        public LootTableProvider(DataGenerator pGenerator) {
            super(pGenerator);
        }

        @Override
        protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {
            map.forEach((resourceLocation, lootTable) -> LootTables.validate(validationTracker, resourceLocation,
                    lootTable));
        }

        @Override
        protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
            return List.of(Pair.of(Entities::new, LootContextParamSets.ENTITY));
        }

        public final class Entities extends EntityLoot {

            @Override
            protected Iterable<EntityType<?>> getKnownEntities() {
                final List<EntityType<?>> entities = new ArrayList<>();
                entities.addAll(lootTables.keySet().stream().map(Supplier::get).toList());
                return entities;
            }

            @Override
            protected void addTables() {
                lootTables.forEach((entity, loot) -> this.add(entity.get(), loot));
            }

        }

    }

    private final class TagsProvider extends EntityTypeTagsProvider {

        public TagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, EntityDeferredRegister.this.getModID(), existingFileHelper);
        }

        @Override
        protected void addTags() {
            tags.forEach(
                    (tag, entity) -> tag(tag).add(entity.stream().map(Supplier::get).toArray(EntityType<?>[]::new)));
        }

    }

}
