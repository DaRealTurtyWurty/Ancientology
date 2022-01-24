package io.github.darealturtywurty.ancientology.core.materials;

import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ToolMaterials implements Tier 
{
	//Tool Materials
	flint(0, 59, 2.0F, 0.0F, 0, null),
	bone(0, 59, 2.0F, 0.0F, 0, null),
	bronze(1, 131, 4.0F, 1.0F, 5, ItemInit.BRONZE_INGOT.get()),
	iron(2, 250, 6.0F, 2.0F, 14, Items.IRON_INGOT ),
	iron_golden(2, 250, 12.0F, 2.0F, 22, Items.GOLD_INGOT),
	iron_diamond(3, 1561, 8.0F, 3.0F, 10, Items.DIAMOND),	
	iron_netherite(4, 2031, 9.0F, 4.0F, 15, Items.NETHERITE_INGOT)
	;
	
	//Variables
	private float speed, damage;
	private int level, uses, enchantmentValue;
	private Item repairMaterial;
	
	private ToolMaterials(int level, int uses, float speed, float damage, int enchantmentValue, Item repairMaterial)
	{
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairMaterial = repairMaterial;
	}


	public Ingredient getRepairMaterial() {
		return Ingredient.of(this.repairMaterial);// fromItems(this.repairMaterial);
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.damage;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// TODO Auto-generated method stub
		return null;
	}

}

