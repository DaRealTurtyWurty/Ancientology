package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            Ancientology.MODID);

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
