package io.github.darealturtywurty.ancientology.client.render.models;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.entities.Angeoa;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AngeoaModel extends AnimatedGeoModel<Angeoa> {
    private static final ResourceLocation ANIMATION_LOC = new ResourceLocation(Ancientology.MODID,
            "animations/entities/angeoa.animation.json");
    private static final ResourceLocation MODEL_LOC = new ResourceLocation(Ancientology.MODID,
            "geo/entities/angeoa.geo.json");
    private static final ResourceLocation TEXTURE_LOC = new ResourceLocation(Ancientology.MODID,
            "textures/entities/angeoa.png");
    
    @Override
    public ResourceLocation getAnimationFileLocation(Angeoa animatable) {
        return ANIMATION_LOC;
    }
    
    @Override
    public ResourceLocation getModelLocation(Angeoa object) {
        return MODEL_LOC;
    }
    
    @Override
    public ResourceLocation getTextureLocation(Angeoa object) {
        return TEXTURE_LOC;
    }
}
