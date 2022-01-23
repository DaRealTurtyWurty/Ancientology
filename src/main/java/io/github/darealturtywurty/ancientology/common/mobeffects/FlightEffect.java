package io.github.darealturtywurty.ancientology.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FlightEffect extends MobEffect {
    public FlightEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            if (!player.getAbilities().flying) {
                player.getAbilities().flying = true;
                player.getAbilities().setFlyingSpeed(0.05F * (pAmplifier + 1));
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
