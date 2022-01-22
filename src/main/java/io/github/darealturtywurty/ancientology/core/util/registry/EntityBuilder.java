package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.function.Supplier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item.Properties;

import net.minecraftforge.common.ForgeSpawnEggItem;

@SuppressWarnings("unchecked")
public class EntityBuilder<E extends Entity> implements Builder<EntityType<E>> {

    protected final EntityType.EntityFactory<E> factory;
    protected final EntityDeferredRegister register;
    protected final String name;
    protected EntityRegistryObject<E> registryObject;

    private final EntityType.Builder<E> typeBuilder;
    private SpawnEggBuilder<?> spawnEggBuilder;

    EntityBuilder(String name, EntityFactory<E> factory, EntityDeferredRegister register) {
        this.factory = factory;
        this.register = register;
        this.name = name;
        this.typeBuilder = EntityType.Builder.of(factory, MobCategory.AMBIENT);
        this.spawnEggBuilder = new SpawnEggBuilder<>((type, bg, highlight,
                props) -> new ForgeSpawnEggItem(() -> (EntityType<Mob>) type.get(), bg, highlight, props));
    }

    @Override
    public EntityType<E> get() {
        return registryObject.get();
    }

    @Override
    public EntityRegistryObject<E> build() {
        if (registryObject != null) { return registryObject; }
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
