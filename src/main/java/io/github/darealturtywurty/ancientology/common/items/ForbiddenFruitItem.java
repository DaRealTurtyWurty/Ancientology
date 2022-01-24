package io.github.darealturtywurty.ancientology.common.items;

import static io.github.darealturtywurty.ancientology.core.util.Constants.RAND;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraftforge.registries.ForgeRegistries;

public class ForbiddenFruitItem extends Item {
    public static final FoodProperties FOOD = new FoodProperties.Builder().alwaysEat().nutrition(10).saturationMod(0.1f).build();

    public ForbiddenFruitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide)
            useItem(pLevel, pLivingEntity);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void useItem(Level level, LivingEntity entity) {
        double d = Math.random();
        double f = Math.random();
        if (d < 0.99) {
            if (f < 0.1) {
                sendToNether(entity);
            } else if (f < 0.2) {
                lightningStorm(entity, level);
            } else if (f < 0.25) {
                removeHunger(entity);
            } else if (f < 0.4) {
                giveRandomBadEffect(entity);
            } else if (f < 0.5) {
                rainOfArrows(entity, level);
            } else if (f < 0.52) {
                secondChance(entity);
            } else if (f < 0.6) {
                //TODO
            }
        } else {
            if (f < 0.7) {
                giveRandomItem(entity);
            } else {
                giveRandomGoodEffect(entity);
            }
        }
    }

    public void secondChance(LivingEntity entity) {
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_chance"), entity.getUUID());
        if (entity instanceof Player player) {
            player.addItem(new ItemStack(ItemInit.FORBIDDEN_FRUIT.get()));
        }
    }

    public void rainOfArrows(LivingEntity entity, Level level) {
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_arrow_rain"), entity.getUUID());
        for (int j = 0; j < 30; j++) {
            Arrow bolt = new Arrow(EntityType.ARROW, level);
            bolt.setPos(entity.getX(), entity.getY() + 1, entity.getZ());
            level.addFreshEntity(bolt);
        }
    }

    public void giveRandomBadEffect(LivingEntity entity) {
        List<MobEffect> effects = new ArrayList<>(ForgeRegistries.MOB_EFFECTS.getValues());
        effects = effects.stream().filter(mobEffect -> !mobEffect.isBeneficial()).collect(Collectors.toList());
        MobEffect effect = effects.get(RAND.nextInt(effects.size()));
        int duration = RAND.nextInt(1, 6) * 1200;
        int amplifier = RAND.nextInt(5);
        MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier);
        entity.addEffect(instance);
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_give_bad_effect"), entity.getUUID());
    }

    public void removeHunger(LivingEntity entity) {
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_hunger"), entity.getUUID());
        if (entity instanceof Player player) {
            player.getFoodData().setFoodLevel(0);
            player.getFoodData().setSaturation(0);
        }
    }

    public void lightningStorm(LivingEntity entity, Level level) {
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_lightning"), entity.getUUID());
        for (int j = 0; j < 10; j++) {
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            bolt.setPos(entity.getX(), entity.getY(), entity.getZ());
            level.addFreshEntity(bolt);
        }
    }

    public void sendToNether(LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            ServerLevel toLevel = Objects.requireNonNull(entity.level.getServer()).getLevel(Level.NETHER);
            player.teleportTo(toLevel, entity.blockPosition().getX(), Math.min(entity.blockPosition().getY(), 122), entity.blockPosition().getZ(), 0, 0);
            entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_nether"), entity.getUUID());
        }
    }

    public void giveRandomGoodEffect(LivingEntity entity) {
        List<MobEffect> effects = new ArrayList<>(ForgeRegistries.MOB_EFFECTS.getValues());
        effects = effects.stream().filter(MobEffect::isBeneficial).toList();
        MobEffect effect = effects.get(RAND.nextInt(effects.size()));
        int duration = RAND.nextInt(1, 6) * 1200;
        int amplifier = RAND.nextInt(5);
        MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier);
        entity.addEffect(instance);
        entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_give_good_effect"), entity.getUUID());
    }

    public void giveRandomItem(LivingEntity entity) {
        List<Item> items = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        items = items.stream().filter(item -> item.getItemCategory() != null).collect(Collectors.toList());
        List<Enchantment> enchantments = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues());

        ItemStack stack = new ItemStack(items.get(RAND.nextInt(items.size())));

        Enchantment enchant = enchantments.get(RAND.nextInt(enchantments.size()));
        int eLevel = RAND.nextInt(enchant.getMaxLevel() + 1);
        if (eLevel != 0)
            stack.enchant(enchant, eLevel);

        if (entity instanceof ServerPlayer player) {
            player.addItem(stack);
            entity.sendMessage(new TranslatableComponent("msg.acientology.fruit_give_item"), entity.getUUID());
        }
    }
}
