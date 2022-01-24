package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID)
            .setDefaultItemTab(Ancientology.ANCIENTOLOGY_ITEM_TAB);

    // Tin
    public static final ItemRegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", Item::new).lang("Raw Tin")
            .addTag(Tags.Items.RAW_MATERIALS, Tags.Items.ORES_IN_GROUND_DEEPSLATE).build();
    public static final ItemRegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", Item::new).lang("Tin Ingot")
            .addTag(Tags.Items.INGOTS).build();
    public static final ItemRegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", Item::new).lang("Tin Nugget")
            .addTag(Tags.Items.NUGGETS).build();
    
    // Bronze
    public static final ItemRegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", Item::new).lang("Bronze Ingot")
    		.addTag(Tags.Items.INGOTS).build();
    public static final ItemRegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", Item::new).lang("Bronze Nugget")
    		.addTag(Tags.Items.NUGGETS).build();
 
    // Flint Tools
    /*	
    public static final ItemRegistryObject<Item> FLINT_DAGGER = ITEMS.register("flint_dagger", () -> new SwordItem(ToolMaterials.flint, 3, -2.4f)).lang("Flint Dagger");
	public static final ItemRegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe", () -> new AxeItem(ToolMaterials.flint, 3, -2.8f)).lang("Flint Axe");
	public static final ItemRegistryObject<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe", () -> new PickaxeItem(ToolMaterials.flint, 2, -2.8f)).lang("Flint Pickaxe");
	public static final ItemRegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel", () -> new ShovelItem(ToolMaterials.flint, 2, -2.8f)).lang("Flint Shovel");
	public static final ItemRegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ToolMaterials.flint, 2, -2.8f)).lang("Flint Hoe");
    //*/

    // Bone Tools
    
    
    // Bronze Tools
    
    

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
