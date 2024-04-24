package net.ltxprogrammer.changed.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.ltxprogrammer.changed.client.LivingEntityRendererExtender;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class LatexHeldEntityLayer<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends RenderLayer<T, M> {
    private final ModelPart torso;

    public LatexHeldEntityLayer(RenderLayerParent<T, M> parent) {
        super(parent);
        torso = parent.getModel().getTorso();
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        var ability = entity.getAbilityInstance(ChangedAbilities.GRAB_ENTITY_ABILITY.get());
        if (ability == null) return;
        if (ability.grabbedEntity == null) return;
        if (ability.suited) return;
        float scale = 1f/entity.getBasicPlayerInfo().getSize();
        //pose.scale(scale,scale,scale);
        TransfurVariantInstance.syncEntityPosRotWithEntity(ability.grabbedEntity, entity);
        pose.pushPose();
        torso.translateAndRotate(pose);

        pose.translate(-0.0625, 0.0, -4.5 / 16.0);
        pose.mulPose(Vector3f.ZP.rotationDegrees(-11.0f));
        pose.scale(scale,scale,scale);
        var entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(ability.grabbedEntity);

        if (!(entityRenderer instanceof LivingEntityRendererExtender ext)) return;

        ext.directRender(ability.grabbedEntity, 0.0f, partialTicks, pose, bufferSource, packedLight);

        pose.popPose();
    }
}
