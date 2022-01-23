package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.entities.Angeoa;
import io.github.darealturtywurty.ancientology.core.util.LootTableUtils;
import io.github.darealturtywurty.ancientology.core.util.registry.EntityDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.EntityRegistryObject;
import net.minecraft.world.item.Items;

public final class EntityInit {
    public static final EntityDeferredRegister ENTITIES = EntityDeferredRegister.create(Ancientology.MODID,
            ItemInit.ITEMS);
    
    public static final EntityRegistryObject<Angeoa> ANGEOA = ENTITIES.register("angeoa", Angeoa::new)
            .spawnEgg(builder -> builder.backgroundColor(0xFF00AA).highlightColor(0xAA00FF).lang("Angeoa Spawn Egg"))
            .withLootTable(LootTableUtils.createSimpleEntityLootTable(Items.BREAD, 2, 6, 1, 3))
            .modifyBuilder(builder -> builder.sized(10f, 4f)).build();
    
    private EntityInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
