package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID)
            .setDefaultItemTab(Ancientology.ANCIENTOLOGY_ITEM_TAB);

    public static final ItemRegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", Item::new).lang("Raw Tin")
            .addTag(Tags.Items.RAW_MATERIALS, Tags.Items.ORES_IN_GROUND_DEEPSLATE).build();
    public static final ItemRegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", Item::new).lang("Tin Ingot")
            .addTag(Tags.Items.INGOTS).build();

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
