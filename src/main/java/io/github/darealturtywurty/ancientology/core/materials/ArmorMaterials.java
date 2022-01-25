package io.github.darealturtywurty.ancientology.core.materials;

import java.util.function.Supplier;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum ArmorMaterials implements ArmorMaterial {
   BONE(Ancientology.MODID + ":bone", 10, new int[]{1, 2, 3, 1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
      return Ingredient.of(Items.BONE);
   }),
   BRONZE(Ancientology.MODID + ":bronze", 12, new int[]{1, 4, 5, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
      return Ingredient.of(ItemInit.BRONZE_INGOT);
   });

   private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
   private final String name;
   private final int durabilityMultiplier;
   private final int[] slotProtections;
   private final int enchantmentValue;
   private final SoundEvent sound;
   private final float toughness;
   private final float knockbackResistance;
   private final Supplier<Ingredient> repairIngredient;

   private ArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
      this.name = name;
      this.durabilityMultiplier = durabilityMultiplier;
      this.slotProtections = slotProtections;
      this.enchantmentValue = enchantmentValue;
      this.sound = sound;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.repairIngredient = repairIngredient;
   }

   public int getDurabilityForSlot(EquipmentSlot pSlot) {
      return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
   }

   public int getDefenseForSlot(EquipmentSlot pSlot) {
      return this.slotProtections[pSlot.getIndex()];
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   public SoundEvent getEquipSound() {
      return this.sound;
   }

   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }

   public String getName() {
      return this.name;
   }

   public float getToughness() {
      return this.toughness;
   }

   /**
    * Gets the percentage of knockback resistance provided by armor of the material.
    */
   public float getKnockbackResistance() {
      return this.knockbackResistance;
   }
}