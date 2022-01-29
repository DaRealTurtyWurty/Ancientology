package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.items.ForbiddenFruitItem;
import io.github.darealturtywurty.ancientology.core.materials.ArmorMaterials;
import io.github.darealturtywurty.ancientology.core.materials.ToolMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            Ancientology.MODID);

    public static final RegistryObject<ForbiddenFruitItem> FORBIDDEN_FRUIT = ITEMS.register("forbidden_fruit",
            () -> new ForbiddenFruitItem(
                    defaultProperties().fireResistant().food(ForbiddenFruitItem.FOOD).rarity(Rarity.RARE)));

    /*
     * public static final RegistryObject<ForbiddenFruitItem> FORBIDDEN_FRUIT =
     * ITEMS .register("forbidden_fruit",
     * ForbiddenFruitItem::new).lang("Forbidden Fruit") .lang(MinecraftLocale.EL_GR,
     * "\u0391\u03c0\u03b1\u03b3\u03bf\u03c1\u03b5\u03c5\u03bc\u03ad\u03bd\u03bf\u03c2 \u039a\u03b1\u03c1\u03c0\u03cc\u03c2"
     * ) .food(ForbiddenFruitItem.FOOD).fireResistant().rarity(Rarity.RARE).build();
     */

    // Resources
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(defaultProperties()));
    
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",
            () -> new Item(defaultProperties()));
    // Tools
    // TODO: Add Spears and Arrows
    public static final RegistryObject<SwordItem> FLINT_DAGGER = ITEMS.register("flint_dagger",
            () -> new SwordItem(ToolMaterials.FLINT, 3, -2.4f, defaultProperties()));

    /*
     * public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
     * Item::new).lang("Raw Tin") .addTag(Tags.Items.RAW_MATERIALS,
     * Tags.Items.ORES_IN_GROUND_DEEPSLATE).build(); public static final
     * RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
     * Item::new).lang("Tin Ingot") .addTag(Tags.Items.INGOTS).build(); public
     * static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
     * Item::new).lang("Tin Nugget") .addTag(Tags.Items.NUGGETS).build(); // Copper
     * public static final RegistryObject<Item> COPPER_NUGGET =
     * ITEMS.register("copper_nugget", Item::new)
     * .lang("Copper Nugget").addTag(Tags.Items.NUGGETS).build(); // Bronze public
     * static final RegistryObject<Item> BRONZE_INGOT =
     * ITEMS.register("bronze_ingot", Item::new)
     * .lang("Bronze Ingot").addTag(Tags.Items.INGOTS).build(); public static final
     * RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",
     * Item::new) .lang("Bronze Nugget").addTag(Tags.Items.NUGGETS).build();
     */

    public static final RegistryObject<AxeItem> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(ToolMaterials.FLINT, 6, -3.2f, defaultProperties()));
    public static final RegistryObject<PickaxeItem> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(ToolMaterials.FLINT, 1, -2.8f, defaultProperties()));
    public static final RegistryObject<ShovelItem> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ShovelItem(ToolMaterials.FLINT, 1.5f, -3f, defaultProperties()));
    public static final RegistryObject<HoeItem> FLINT_HOE = ITEMS.register("flint_hoe",
            () -> new HoeItem(ToolMaterials.FLINT, 0, -3.0f, defaultProperties()));
    public static final RegistryObject<SwordItem> BONE_DAGGER = ITEMS.register("bone_dagger",
            () -> new SwordItem(ToolMaterials.BONE, 3, -2.4f, defaultProperties()));
    
    public static final RegistryObject<AxeItem> BONE_AXE = ITEMS.register("bone_axe",
            () -> new AxeItem(ToolMaterials.BONE, 6, -3.2f, defaultProperties()));
    public static final RegistryObject<PickaxeItem> BONE_PICKAXE = ITEMS.register("bone_pickaxe",
            () -> new PickaxeItem(ToolMaterials.BONE, 1, -2.8f, defaultProperties()));
    public static final RegistryObject<ShovelItem> BONE_SHOVEL = ITEMS.register("bone_shovel",
            () -> new ShovelItem(ToolMaterials.BONE, 1.5f, -2.8f, defaultProperties()));
    public static final RegistryObject<HoeItem> BONE_HOE = ITEMS.register("bone_hoe",
            () -> new HoeItem(ToolMaterials.BONE, 0, -3.0f, defaultProperties()));
    public static final RegistryObject<SwordItem> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ToolMaterials.BRONZE, 3, -2.4f, defaultProperties()));

    public static final RegistryObject<AxeItem> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ToolMaterials.BRONZE, 7, -3.2f, defaultProperties()));
    public static final RegistryObject<PickaxeItem> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ToolMaterials.BRONZE, 1, -2.8f, defaultProperties()));
    public static final RegistryObject<ShovelItem> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ToolMaterials.BRONZE, 1.5f, -3.0f, defaultProperties()));
    public static final RegistryObject<HoeItem> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ToolMaterials.BRONZE, -1, -2.0f, defaultProperties()));
    // Armor
    public static final RegistryObject<ArmorItem> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.HEAD, defaultProperties()));
    
    public static final RegistryObject<ArmorItem> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.CHEST, defaultProperties()));
    public static final RegistryObject<ArmorItem> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.LEGS, defaultProperties()));
    public static final RegistryObject<ArmorItem> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.FEET, defaultProperties()));
    public static final RegistryObject<DyeableArmorItem> BONE_HELMET = ITEMS.register("bone_helmet",
            () -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.HEAD, defaultProperties()));
    
    public static final RegistryObject<DyeableArmorItem> BONE_CHESTPLATE = ITEMS.register("bone_chestplate",
            () -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.CHEST, defaultProperties()));
    public static final RegistryObject<DyeableArmorItem> BONE_LEGGINGS = ITEMS.register("bone_leggings",
            () -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.LEGS, defaultProperties()));
    public static final RegistryObject<DyeableArmorItem> BONE_BOOTS = ITEMS.register("bone_boots",
            () -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.FEET, defaultProperties()));
    
    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
    
    private static Item.Properties defaultProperties() {
        return new Item.Properties().tab(Ancientology.TAB);
    }
    
    @Mod.EventBusSubscriber(modid = Ancientology.MODID, bus = Bus.MOD)
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                final ResourceLocation name = block.getRegistryName();
                final var item = new BlockItem(block, defaultProperties());
                item.setRegistryName(name);
                event.getRegistry().register(item);
            });
        }
    }
}
