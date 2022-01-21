package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID);

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
