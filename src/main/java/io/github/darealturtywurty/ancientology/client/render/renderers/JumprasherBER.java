package io.github.darealturtywurty.ancientology.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.darealturtywurty.ancientology.common.blockentities.JumprasherBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class JumprasherBER implements BlockEntityRenderer<JumprasherBlockEntity> {
    private static final String PROFILER_SECTION = "jumprasher";
    private final BlockEntityRendererProvider.Context context;

    public JumprasherBER(BlockEntityRendererProvider.Context pContext) {
        this.context = pContext;
    }

    @Override
    public void render(JumprasherBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
            MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        final var profiler = pBlockEntity.getLevel().getProfiler();
        if (!pBlockEntity.getInventory().getItem(0).isEmpty()) {
            profiler.push(PROFILER_SECTION);
            pPoseStack.pushPose();
            pPoseStack.translate(-0.5F, -0.5F, -0.5F);
            renderItem(pPoseStack, pBufferSource, pBlockEntity.getInventory().getItem(0), pBlockEntity, pPackedLight,
                    pPackedOverlay);
            pPoseStack.popPose();
            profiler.pop();
        }
    }

    private static void renderItem(PoseStack poseStack, MultiBufferSource renderTypeBuffer, ItemStack itemStack,
            JumprasherBlockEntity jumprasher, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        float yTop = (9 - jumprasher.getItemHeight()) * 0.105F;
        if (jumprasher.getItemHeight() == 1) {
            yTop = (9 - jumprasher.getItemHeight()) * 0.125f;
        } else if (jumprasher.getItemHeight() == 7) {
            yTop = 0;
        }
        
        poseStack.translate(1F, (yTop - 1F) / 2 + 1F, 1F);
        final BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(itemStack, null, null, 0);
        if (model.isGui3d()) {
            poseStack.scale(1.7F, 1.7F, 1.7F);
        }

        poseStack.scale(1F, yTop - 0.125F, 1F);

        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED,
                packedLight, packedOverlay, poseStack, renderTypeBuffer, 0);
        poseStack.popPose();
    }
}
