package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.storage.loot.LootTable;

import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unchecked")
public class EntityBuilder<E extends Entity> implements Builder<EntityType<E>> {

    protected final EntityType.EntityFactory<E> factory;
    protected final EntityDeferredRegister register;
    protected final String name;
    protected EntityRegistryObject<E> registryObject;

    private final EntityType.Builder<E> typeBuilder;
    private SpawnEggBuilder<?> spawnEggBuilder;
    private final List<Tag.Named<EntityType<?>>> tags = new ArrayList<>();
    private LootTable.Builder lootTable;

    EntityBuilder(String name, EntityFactory<E> factory, EntityDeferredRegister register) {
        this.factory = factory;
        this.register = register;
        this.name = name;
        this.typeBuilder = EntityType.Builder.of(factory, MobCategory.AMBIENT);
        this.spawnEggBuilder = new SpawnEggBuilder<>((type, bg, highlight,
                props) -> new ForgeSpawnEggItem(() -> (EntityType<Mob>) type.get(), bg, highlight, props));
    }

    /**
     * Sets the category of the entity.
     * 
     * @param  category the category
     * @return          the builder instance
     */
    public EntityBuilder<E> category(@Nonnull final MobCategory category) {
        typeBuilder.category = category;
        return this;
    }

    /**
     * This method should be called when something from the entity's
     * {@link EntityType.Builder} needs to be modified, but a method that does so
     * doesn't exist in {@link EntityBuilder}. <br>
     * <br>
     * It is preferred to chain method calls inside the consumer, instead of calling
     * {@link #modifyProperties(Consumer)} multiple times.
     * 
     * @param  consumer the consumer which will accept the current
     *                  {@link EntityType.Builder} and modify it.
     * @return          the builder instance
     */
    public EntityBuilder<E> modifyBuilder(@Nonnull final Consumer<EntityType.Builder<E>> consumer) {
        consumer.accept(typeBuilder);
        return this;
    }

    /**
     * Sets the loot table of the entity. <br>
     * In order for the loot table to be generated, {@code runData} needs to be run.
     * 
     * @param  lootTable the loot table of the entity
     * @return           the builder instance
     */
    public EntityBuilder<E> withLootTable(@Nullable final LootTable.Builder lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    /**
     * Adds a tag to the entity. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tag(s) to add
     * @return      the builder instance
     */
    @SafeVarargs
    public final EntityBuilder<E> addTag(Named<EntityType<?>>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Adds tags to the entity. <br>
     * In order for the tag to be added, {@code runData} needs to be run.
     * 
     * @param  tags the tags to add
     * @return      the builder instance
     */
    public EntityBuilder<E> addTag(List<Named<EntityType<?>>> tags) {
        this.tags.addAll(tags);
        return this;
    }

    /**
     * Configures the block's spawn egg, using a normal {@link ForgeSpawnEggItem}.
     * <br>
     * <b>This will not work if you entity does not extend {@link Mob}!</b> <br>
     * In that case you either have to use a custom factory using
     * {@link EntityBuilder#spawnEgg(SpawnEggFactory, Consumer)} or you have to call
     * {@link #noSpawnEgg()}.
     * 
     * @param  consumer a consumer that modifies the {@link SpawnEggBuilder} in
     *                  order to fit your needs
     * @return          the builder instance
     */
    public EntityBuilder<E> spawnEgg(@Nonnull final Consumer<SpawnEggBuilder<ForgeSpawnEggItem>> consumer) {
        consumer.accept((SpawnEggBuilder<ForgeSpawnEggItem>) spawnEggBuilder);
        return this;
    }

    /**
     * Configures the entity's spawn egg, using the specified {@code factory} in
     * order to instantiate it
     * 
     * @param  <I>      the class of the spawn egg
     * @param  factory  a factory which constructs the spawn egg
     * @param  consumer a consumer which will modify the created
     *                  {@link SpawnEggBuilder} in order to fit your needs
     * @return          the builder instance
     */
    public <I extends ForgeSpawnEggItem> EntityBuilder<E> spawnEgg(final SpawnEggFactory<I, E> factory,
            @Nonnull final Consumer<SpawnEggBuilder<I>> consumer) {
        final var builder = new SpawnEggBuilder<>(factory::build);
        consumer.accept(builder);
        this.spawnEggBuilder = builder;
        return this;
    }

    /**
     * This method will make the builder not generate a spawn egg. <br>
     * <b>This is required to call when the entity that this builder represents does
     * not extend {@link Mob} and the default spawn egg factory is used!</b>
     * 
     * @return the builder instance
     */
    public EntityBuilder<E> noSpawnEgg() {
        this.spawnEggBuilder = null;
        return this;
    }

    @Override
    public EntityType<E> get() {
        return registryObject.get();
    }

    @Override
    public EntityRegistryObject<E> build() {
        if (registryObject != null) { return registryObject; }
        final var object = register.getRegister().register(name,
                () -> typeBuilder.build(new ResourceLocation(register.modId, name).toString()));
        ItemRegistryObject<ForgeSpawnEggItem> spawnEgg = null;
        if (spawnEggBuilder != null) {
            spawnEgg = (ItemRegistryObject<ForgeSpawnEggItem>) spawnEggBuilder.build();
        }
        registryObject = new EntityRegistryObject<>(object,
                spawnEgg == null ? null : RegistryObject.of(spawnEgg.getId(), ForgeRegistries.ITEMS));

        tags.forEach(tag -> register.tags.computeIfAbsent(tag, k -> Lists.newArrayList()).add(registryObject::get));

        if (lootTable != null) {
            register.lootTables.computeIfAbsent(registryObject::get, k -> lootTable);
        }

        register.builders.add(this);
        return registryObject;
    }

    public final class SpawnEggBuilder<I extends ForgeSpawnEggItem> extends ItemBuilder<I> {

        private final SpawnEggFactory<I, E> spawnEggFactory;
        private int backgroundColor;
        private int highlightColor;

        SpawnEggBuilder(SpawnEggFactory<I, E> factory) {
            super(null, EntityBuilder.this.register.itemRegister, EntityBuilder.this.name + "_spawn_egg");
            this.spawnEggFactory = factory;
        }

        public SpawnEggBuilder<I> backgroundColor(final int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public SpawnEggBuilder<I> highlightColor(final int highlightColor) {
            this.highlightColor = highlightColor;
            return this;
        }

        @Override
        public ItemRegistryObject<I> build() {
            if (this.registryObject != null) { return registryObject; }
            final var obj = register.getRegister().register(name,
                    // No method reference in order to prevent NPEs
                    () -> spawnEggFactory.build(() -> EntityBuilder.this.registryObject.get(), backgroundColor,
                            highlightColor, this.properties));
            this.registryObject = new ItemRegistryObject<>(obj);
            register.builders.add(this);
            addDatagenStuff(obj);
            return registryObject;
        }

    }

    @FunctionalInterface
    public static interface SpawnEggFactory<I extends ForgeSpawnEggItem, E extends Entity> {

        I build(Supplier<EntityType<E>> type, int backgroundColor, int highlightColor, Properties props);
    }

}
