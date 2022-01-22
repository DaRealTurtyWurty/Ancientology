package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.EntityDeferredRegister;

public final class EntityInit {

    public static final EntityDeferredRegister ENTITIES = EntityDeferredRegister.create(Ancientology.MODID,
            ItemInit.ITEMS);

    private EntityInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
