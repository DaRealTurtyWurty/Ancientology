package io.github.darealturtywurty.ancientology.core.materials;

import java.util.function.Supplier;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ToolMaterials implements Tier {
   FLINT(0, 59, 2.0F, 0.0F, 15, () -> {
	   return Ingredient.of(Items.FLINT);
   }),
   BONE(0, 59, 2.0F, 0.0F, 15, () -> {
	   return Ingredient.of(Items.BONE);
	   }),
   BRONZE(1, 131, 4.0F, 1.0F, 5, () -> {
      return Ingredient.of(ItemInit.BRONZE_INGOT);
   });

   private final int level;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final Supplier<Ingredient> repairIngredient;

   private ToolMaterials(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
      this.level = level;
      this.uses = uses;
      this.speed = speed;
      this.damage = damage;
      this.enchantmentValue = enchantmentValue;
      this.repairIngredient = repairIngredient;
   }

   public int getUses() {
      return this.uses;
   }

   public float getSpeed() {
      return this.speed;
   }

   public float getAttackDamageBonus() {
      return this.damage;
   }

   public int getLevel() {
      return this.level;
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }

}
