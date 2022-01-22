package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.EntityDeferredRegister;

public final class EntityInit {

    public static final EntityDeferredRegister ENTITIES = EntityDeferredRegister.create(Ancientology.MODID,
            ItemInit.ITEMS);

    /*
     * public static final EntityRegistryObject<Chicken> TEST_CHICKEN =
     * ENTITIES.register("test", Chicken::new) .spawnEgg(i ->
     * i.lang("Chicken spawn egg").shapelessRecipe(1, r -> r.requires(Items.WHEAT,
     * 2))) .category(MobCategory.MONSTER).addTag(EntityTypeTags.IMPACT_PROJECTILES)
     * .withLootTable(LootTable.lootTable().withPool(LootPool.lootPool().setRolls(
     * ConstantValue.exactly(1.0F)) .add(LootItem.lootTableItem(Items.FEATHER)
     * .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
     * .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.
     * 0F, 1.0F)))))) .modifyBuilder(b ->
     * b.fireImmune().setShouldReceiveVelocityUpdates(true)).build();
     */

    private EntityInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
