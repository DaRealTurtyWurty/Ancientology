package io.github.darealturtywurty.ancientology.client.render.renderers;

import io.github.darealturtywurty.ancientology.client.render.models.AngeoaModel;
import io.github.darealturtywurty.ancientology.common.entities.Angeoa;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AngeoaRenderer extends GeoEntityRenderer<Angeoa> {
    public AngeoaRenderer(Context renderManager) {
        super(renderManager, new AngeoaModel());
        this.shadowRadius = 5.0f;
    }
}
