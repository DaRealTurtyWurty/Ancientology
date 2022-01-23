package io.github.darealturtywurty.ancientology.common.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Angeoa extends AbstractFish implements IAnimatable {
    private static final AABB BOUNDING_BOX = new AABB(-7, -3, -2, 7, 3, 2);
    private final AnimationFactory animFactory = new AnimationFactory(this);

    public Angeoa(EntityType<? extends AbstractFish> type, Level level) {
        super(type, level);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return Items.WATER_BUCKET.getDefaultInstance();
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animFactory;
    }
    
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::animPredicate));
    }
    
    @Override
    protected AABB getBoundingBoxForPose(Pose pPose) {
        return BOUNDING_BOX;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // TODO: Add goals
    }

    private <Entity extends IAnimatable> PlayState animPredicate(AnimationEvent<Entity> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.angeoa.idle", true));
        return PlayState.CONTINUE;
    }
    
    public static AttributeSupplier.Builder createAttributeMap() {
        return AbstractFish.createAttributes().add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.ATTACK_SPEED, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.MAX_HEALTH, 540.0D).add(Attributes.ARMOR, 5.0D);
    }
}
