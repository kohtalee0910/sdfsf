package net.ltxprogrammer.changed.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.extension.ChangedCompatibility;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiFunction;

public class CustomEyesLayer<M extends AdvancedHumanoidModel<T>, T extends ChangedEntity> extends RenderLayer<T, M> {
    public static class ColorData {
        public final Color3 color;
        public final float alpha;
        public final boolean emissive;

        private ColorData(Color3 color, float alpha, boolean emissive) {
            this.color = color;
            this.alpha = alpha;
            this.emissive = emissive;
        }

        public static ColorData ofColor(Color3 color) {
            return new ColorData(color, 1.0f, false);
        }

        public static ColorData ofTranslucentColor(Color3 color, float alpha) {
            return new ColorData(color, alpha, false);
        }

        public static ColorData ofEmissiveColor(Color3 color) {
            return new ColorData(color, 1.0f, true);
        }

        public RenderType getRenderType(ResourceLocation texture) {
            return alpha < 1.0f ? RenderType.entityTranslucent(texture) : (emissive ? RenderType.eyes(texture) : RenderType.entityCutout(texture));
        }
    }

    public interface ColorFunction<T extends ChangedEntity> extends BiFunction<T, BasicPlayerInfo, ColorData> {
        @Nullable
        ColorData getColor(T entity, BasicPlayerInfo bpi);

        @Override
        default ColorData apply(T entity, BasicPlayerInfo bpi) {
            return getColor(entity, bpi);
        }

        default Optional<ColorData> getColorSafe(T entity, BasicPlayerInfo bpi) {
            var safe = Optional.ofNullable(this.getColor(entity, bpi));
            if (safe.isPresent() && safe.get().alpha <= 0.0f)
                return Optional.empty();
            return safe;
        }
    }

    public static final ModelLayerLocation HEAD = new ModelLayerLocation(Changed.modResource("head"), "main");
    private final ModelPart head;
    private final ColorFunction<T> scleraColorFn;
    private final ColorFunction<T> irisColorLeftFn;
    private final ColorFunction<T> irisColorRightFn;
    private final ColorFunction<T> eyeBrowsColorFn;

    public static <T extends ChangedEntity> ColorData noRender(T entity, BasicPlayerInfo bpi) {
        return null;
    }

    public static <T extends ChangedEntity> ColorData irisColorLeft(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofColor(bpi.getLeftIrisColor());
    }

    public static <T extends ChangedEntity> ColorData irisColorRight(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofColor(bpi.getRightIrisColor());
    }

    public static <T extends ChangedEntity> ColorData glowingIrisColorLeft(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofEmissiveColor(bpi.getLeftIrisColor());
    }

    public static <T extends ChangedEntity> ColorData glowingIrisColorRight(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofEmissiveColor(bpi.getRightIrisColor());
    }

    public static <T extends ChangedEntity> ColorFunction<T> translucentIrisColorLeft(float alpha) {
        return (entity, bpi) -> ColorData.ofTranslucentColor(bpi.getLeftIrisColor(), alpha);
    }

    public static <T extends ChangedEntity> ColorFunction<T> translucentIrisColorRight(float alpha) {
        return (entity, bpi) -> ColorData.ofTranslucentColor(bpi.getRightIrisColor(), alpha);
    }

    public static <T extends ChangedEntity> ColorData scleraColor(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofColor(bpi.getScleraColor());
    }

    public static <T extends ChangedEntity> ColorData hairColor(T entity, BasicPlayerInfo bpi) {
        return ColorData.ofColor(bpi.getHairColor());
    }

    public static <T extends ChangedEntity> ColorFunction<T> fixedColor(Color3 color) {
        return (entity, bpi) -> ColorData.ofColor(color);
    }

    public static <T extends ChangedEntity> ColorFunction<T> fixedColorGlowing(Color3 color) {
        return (entity, bpi) -> ColorData.ofEmissiveColor(color);
    }

    public static <T extends ChangedEntity> ColorFunction<T> fixedColor(Color3 color, float alpha) {
        return (entity, bpi) -> ColorData.ofTranslucentColor(color, alpha);
    }

    public static <T extends ChangedEntity> ColorFunction<T> fixedIfNotDarkLatexOverrideLeft(Color3 color) {
        return (entity, bpi) -> ColorData.ofColor(bpi.isOverrideIrisOnDarkLatex() ? bpi.getLeftIrisColor() : color);
    }

    public static <T extends ChangedEntity> ColorFunction<T> fixedIfNotDarkLatexOverrideRight(Color3 color) {
        return (entity, bpi) -> ColorData.ofColor(bpi.isOverrideIrisOnDarkLatex() ? bpi.getRightIrisColor() : color);
    }

    public CustomEyesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        this(parent, modelSet, CustomEyesLayer::scleraColor, CustomEyesLayer::irisColorLeft, CustomEyesLayer::irisColorRight, CustomEyesLayer::noRender);
    }

    public CustomEyesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet, ColorFunction<T> scleraColorFn, ColorFunction<T> irisColorDualFn) {
        this(parent, modelSet, scleraColorFn, irisColorDualFn, irisColorDualFn);
    }

    public CustomEyesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet, ColorFunction<T> scleraColorFn, ColorFunction<T> irisColorLeftFn, ColorFunction<T> irisColorRightFn) {
        this(parent, modelSet, scleraColorFn, irisColorLeftFn, irisColorRightFn, CustomEyesLayer::noRender);
    }

    public CustomEyesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet,
                           ColorFunction<T> scleraColorFn, ColorFunction<T> irisColorLeftFn, ColorFunction<T> irisColorRightFn, ColorFunction<T> eyeBrowsColorFn) {
        super(parent);
        this.head = modelSet.bakeLayer(HEAD);
        this.scleraColorFn = scleraColorFn;
        this.irisColorLeftFn = irisColorLeftFn;
        this.irisColorRightFn = irisColorRightFn;
        this.eyeBrowsColorFn = eyeBrowsColorFn;
    }

    public static LayerDefinition createHead() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }

    private void renderHead(PoseStack pose, VertexConsumer buffer, int packedLight, int overlay, Color3 color, float alpha) {
        head.render(pose, buffer, packedLight, overlay, color.red(), color.green(), color.blue(), alpha);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible())
            return;
        if (ChangedCompatibility.isFirstPersonRendering())
            return;

        BasicPlayerInfo info = new BasicPlayerInfo();
        info.copyFrom(entity.getBasicPlayerInfo());
        if (Changed.config.client.basicPlayerInfo.isOverrideOthersToMatchStyle())
            info.setEyeStyle(Changed.config.client.basicPlayerInfo.getEyeStyle());

        var style = info.getEyeStyle();

        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

        head.copyFrom(this.getParentModel().getHead());

        scleraColorFn.getColorSafe(entity, info).ifPresent(data -> {
            renderHead(pose, bufferSource.getBuffer(data.getRenderType(style.getSclera())), packedLight, overlay, data.color, data.alpha);
        });
        irisColorLeftFn.getColorSafe(entity, info).ifPresent(data -> {
            renderHead(pose, bufferSource.getBuffer(data.getRenderType(style.getLeftIris())), packedLight, overlay, data.color, data.alpha);
        });
        irisColorRightFn.getColorSafe(entity, info).ifPresent(data -> {
            renderHead(pose, bufferSource.getBuffer(data.getRenderType(style.getRightIris())), packedLight, overlay, data.color, data.alpha);
        });
        eyeBrowsColorFn.getColorSafe(entity, info).ifPresent(data -> {
            renderHead(pose, bufferSource.getBuffer(data.getRenderType(style.getEyeBrows())), packedLight, overlay, data.color, data.alpha);
        });
    }
}