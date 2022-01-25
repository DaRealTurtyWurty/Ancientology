package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.materials.ArmorMaterials;
import io.github.darealturtywurty.ancientology.core.materials.ToolMaterials;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.Tags;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID)
            .setDefaultItemTab(Ancientology.ANCIENTOLOGY_ITEM_TAB);

    // Resources
	    // Tin
	    public static final ItemRegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", Item::new).lang("Raw Tin")
	            .addTag(Tags.Items.RAW_MATERIALS, Tags.Items.ORES_IN_GROUND_DEEPSLATE).build();
	    public static final ItemRegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", Item::new).lang("Tin Ingot")
	            .addTag(Tags.Items.INGOTS).build();
	    public static final ItemRegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", Item::new).lang("Tin Nugget")
	            .addTag(Tags.Items.NUGGETS).build();
	    
	    // Copper
	    public static final ItemRegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", Item::new).lang("Copper Nugget")
	    		.addTag(Tags.Items.NUGGETS).build();
	    
	    // Bronze
	    public static final ItemRegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", Item::new).lang("Bronze Ingot")
	    		.addTag(Tags.Items.INGOTS).build();
	    public static final ItemRegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", Item::new).lang("Bronze Nugget")
	    		.addTag(Tags.Items.NUGGETS).build();
 
    // Tools TODO Add Spears and Arrows
	    // Flint Tools
	    public static final ItemRegistryObject<SwordItem> FLINT_DAGGER = ITEMS.register("flint_dagger", properties -> new SwordItem(ToolMaterials.FLINT, 3, -2.4f, properties))
	    		.lang("Flint Dagger").build();
		public static final ItemRegistryObject<AxeItem> FLINT_AXE = ITEMS.register("flint_axe", properties ->  new AxeItem(ToolMaterials.FLINT, 6, -3.2f, properties))
				.lang("Flint Axe").build();
		public static final ItemRegistryObject<PickaxeItem> FLINT_PICKAXE = ITEMS.register("flint_pickaxe", properties ->  new PickaxeItem(ToolMaterials.FLINT, 1, -2.8f, properties))
				.lang("Flint Pickaxe").build();
		public static final ItemRegistryObject<ShovelItem> FLINT_SHOVEL = ITEMS.register("flint_shovel", properties ->  new ShovelItem(ToolMaterials.FLINT, 1.5f, -3f, properties))
				.lang("Flint Shovel").build();
		public static final ItemRegistryObject<HoeItem> FLINT_HOE = ITEMS.register("flint_hoe", properties ->  new HoeItem(ToolMaterials.FLINT, 0, -3.0f, properties))
				.lang("Flint Hoe").build();
	
	    // Bone Tools
	    public static final ItemRegistryObject<SwordItem> BONE_DAGGER = ITEMS.register("bone_dagger", properties -> new SwordItem(ToolMaterials.BONE, 3, -2.4f, properties))
	    		.lang("Bone Dagger").build();
		public static final ItemRegistryObject<AxeItem> BONE_AXE = ITEMS.register("bone_axe", properties ->  new AxeItem(ToolMaterials.BONE, 6, -3.2f, properties))
				.lang("Bone Axe").build();
		public static final ItemRegistryObject<PickaxeItem> BONE_PICKAXE = ITEMS.register("bone_pickaxe", properties ->  new PickaxeItem(ToolMaterials.BONE, 1, -2.8f, properties))
				.lang("Bone Pickaxe").build();
		public static final ItemRegistryObject<ShovelItem> BONE_SHOVEL = ITEMS.register("bone_shovel", properties ->  new ShovelItem(ToolMaterials.BONE, 1.5f, -2.8f, properties))
				.lang("Bone Shovel").build();
		public static final ItemRegistryObject<HoeItem> BONE_HOE = ITEMS.register("bone_hoe", properties ->  new HoeItem(ToolMaterials.BONE, 0, -3.0f, properties))
				.lang("Bone Hoe").build();
	    
	    // Bronze Tools
		public static final ItemRegistryObject<SwordItem> BRONZE_SWORD = ITEMS.register("bronze_sword", properties -> new SwordItem(ToolMaterials.BRONZE, 3, -2.4f, properties))
				.lang("Bronze Sword").build();
		public static final ItemRegistryObject<AxeItem> BRONZE_AXE = ITEMS.register("bronze_axe", properties -> new AxeItem(ToolMaterials.BRONZE, 7, -3.2f, properties))
				.lang("Bronze Axe").build();
		public static final ItemRegistryObject<PickaxeItem> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", properties -> new PickaxeItem(ToolMaterials.BRONZE, 1, -2.8f, properties))
				.lang("Bronze Pickaxe").build();
		public static final ItemRegistryObject<ShovelItem> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", properties -> new ShovelItem(ToolMaterials.BRONZE, 1.5f, -3.0f, properties))
				.lang("Bronze Shovel").build();
		public static final ItemRegistryObject<HoeItem> BRONZE_HOE = ITEMS.register("bronze_hoe", properties -> new HoeItem(ToolMaterials.BRONZE, -1, -2.0f, properties))
				.lang("Bronze Hoe").build();	
    
	// Armor
		// Bronze Armor
		public static final ItemRegistryObject<ArmorItem> BRONZE_HELMET = ITEMS.register("bronze_helmet", properties -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.HEAD, properties))
				.lang("Bronze Helmet").build();
		public static final ItemRegistryObject<ArmorItem> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", properties -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.CHEST, properties))
				.lang("Bronze Chestplate").build();
		public static final ItemRegistryObject<ArmorItem> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", properties -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.LEGS, properties))
				.lang("Bronze Leggings").build();
		public static final ItemRegistryObject<ArmorItem> BRONZE_BOOTS = ITEMS.register("bronze_boots", properties -> new ArmorItem(ArmorMaterials.BRONZE, EquipmentSlot.FEET, properties))
				.lang("Bronze Boots").build();
		
		// Bone Armor
		public static final ItemRegistryObject<DyeableArmorItem> BONE_HELMET = ITEMS.register("bone_helmet", properties -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.HEAD, properties))
				.lang("Bone Helmet").build();
		public static final ItemRegistryObject<DyeableArmorItem> BONE_CHESTPLATE = ITEMS.register("bone_chestplate", properties -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.CHEST, properties))
				.lang("Bone Chestplate").build();
		public static final ItemRegistryObject<DyeableArmorItem> BONE_LEGGINGS = ITEMS.register("bone_leggings", properties -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.LEGS, properties))
				.lang("Bone Leggings").build();
		public static final ItemRegistryObject<DyeableArmorItem> BONE_BOOTS = ITEMS.register("bone_boots", properties -> new DyeableArmorItem(ArmorMaterials.BONE, EquipmentSlot.FEET, properties))
				.lang("Bone Boots").build();
	
	


    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
