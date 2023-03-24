package net.ltxprogrammer.changed.client.renderer.animate;

import net.ltxprogrammer.changed.entity.LatexEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

public class QuadrupedalStandAnimator<T extends LatexEntity, M extends EntityModel<T>> extends AbstractQuadrupedalAnimator<T, M> {
    public QuadrupedalStandAnimator(ModelPart torso, ModelPart frontLeftLeg, ModelPart frontRightLeg, ModelPart backLeftLeg, ModelPart backRightLeg) {
        super(torso, frontLeftLeg, frontRightLeg, backLeftLeg, backRightLeg);
    }

    @Override
    public LatexAnimator.AnimateStage preferredStage() {
        return LatexAnimator.AnimateStage.STAND;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        frontRightLeg.y = 12.0F + core.hipOffset;
        frontLeftLeg.y = 12.0F + core.hipOffset;
        backRightLeg.y = 12.0F + core.hipOffset;
        backLeftLeg.y = 12.0F + core.hipOffset;
    }
}
