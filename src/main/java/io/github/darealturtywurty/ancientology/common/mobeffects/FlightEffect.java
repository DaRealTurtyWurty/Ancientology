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
            player.fallDistance = 0;
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.getAbilities().setFlyingSpeed(0.05F * (pAmplifier + 1));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
