package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.items.ForbiddenFruitItem;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.Tags;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID)
            .setDefaultItemTab(Ancientology.ANCIENTOLOGY_ITEM_TAB);

    public static final ItemRegistryObject<ForbiddenFruitItem> FORBIDDEN_FRUIT = ITEMS.register("forbidden_fruit", ForbiddenFruitItem::new)
            .tab(Ancientology.ANCIENTOLOGY_ITEM_TAB)
            .lang("Forbidden Fruit")
            .lang(MinecraftLocale.EL_GR, "\u0391\u03c0\u03b1\u03b3\u03bf\u03c1\u03b5\u03c5\u03bc\u03ad\u03bd\u03bf\u03c2 \u039a\u03b1\u03c1\u03c0\u03cc\u03c2")
            .food(ForbiddenFruitItem.FOOD)
            .fireResistant()
            .rarity(Rarity.RARE)
            .build();
  
    public static final ItemRegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", Item::new).lang("Raw Tin")
            .addTag(Tags.Items.RAW_MATERIALS, Tags.Items.ORES_IN_GROUND_DEEPSLATE).build();
    public static final ItemRegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", Item::new).lang("Tin Ingot")
            .addTag(Tags.Items.INGOTS).build();

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
