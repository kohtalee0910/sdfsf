package net.ltxprogrammer.changed.mixin.entity;

import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.block.WhiteLatexTransportInterface;
import net.ltxprogrammer.changed.entity.*;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedDamageSources;
import net.ltxprogrammer.changed.init.ChangedSounds;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.CameraUtil;
import net.ltxprogrammer.changed.util.EntityUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDataExtension {
    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract boolean isSpectator();

    @Nullable
    @Unique public PlayerMoverInstance<?> playerMover = null;

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "getMyRidingOffset", at = @At("HEAD"), cancellable = true)
    public void getMyRidingOffset(CallbackInfoReturnable<Double> callback) {
        ProcessTransfur.ifPlayerTransfurred(EntityUtil.playerOrNull(this), variant -> {
            callback.setReturnValue(variant.getChangedEntity().getMyRidingOffset());
        });
    }

    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    protected void tryToStartFallFlying(CallbackInfoReturnable<Boolean> ci) {
        Player player = (Player)(Object)this;
        if (latexVariant != null && latexVariant.getParent().canGlide) {
            if (!player.isOnGround() && !player.isFallFlying() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)) {
                player.startFallFlying();
                ci.setReturnValue(true);
                ci.cancel();

                //player.respawn();
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickPre(CallbackInfo ci) {
        ProcessTransfur.tickPlayerTransfurProgress((Player)(Object)this);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tickPost(CallbackInfo ci) {
        var tug = CameraUtil.getTugData((Player)(Object)this);
        if (tug != null) {
            if (tug.shouldExpire(this)) {
                CameraUtil.resetTugData((Player)(Object)this);
                return;
            }

            tug.ticksExpire--;
        }
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    protected void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> ci) {
        if (source instanceof ChangedDamageSources.TransfurDamageSource)
            ci.setReturnValue(ChangedSounds.BLOW1);
    }

    @Inject(method = "getSwimSound", at = @At("HEAD"), cancellable = true)
    protected void getSwimSound(CallbackInfoReturnable<SoundEvent> ci) {
        if (WhiteLatexTransportInterface.isEntityInWhiteLatex(this)) {
            ci.setReturnValue(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.step")));
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void orNotHurt(DamageSource p_36154_, float p_36155_, CallbackInfoReturnable<Boolean> cir) {
        ProcessTransfur.ifPlayerTransfurred(EntityUtil.playerOrNull(this), variant -> {
            if (variant.ageAsVariant < 30)
                cir.cancel();
        });
    }

    // ADDITIONAL DATA
    @Unique
    public TransfurVariantInstance<?> latexVariant = null;
    @Unique
    public float transfurProgress = 0.0f;
    @Unique
    public CameraUtil.TugData wantToLookAt;
    @Unique
    public int paleExposure;
    @Unique
    public BasicPlayerInfo basicPlayerInfo = new BasicPlayerInfo();

    @Override
    public TransfurVariantInstance<?> getLatexVariant() {
        return latexVariant;
    }

    @Override
    public void setLatexVariant(TransfurVariantInstance<?> latexVariant) {
        this.latexVariant = latexVariant;
    }

    @NotNull
    @Override
    public float getTransfurProgress() {
        return transfurProgress;
    }

    @Override
    public void setTransfurProgress(@NotNull float transfurProgress) {
        this.transfurProgress = transfurProgress;
    }

    @Override
    public CameraUtil.TugData getTugData() {
        return wantToLookAt;
    }

    @Override
    public void setTugData(CameraUtil.TugData data) {
        this.wantToLookAt = data;
    }

    @Override
    public int getPaleExposure() {
        return paleExposure;
    }

    @Override
    public void setPaleExposure(int paleExposure) {
        this.paleExposure = paleExposure;
    }

    @Inject(method = "makeStuckInBlock", at = @At("HEAD"), cancellable = true)
    public void makeStuckInBlock(BlockState state, Vec3 v3, CallbackInfo ci) {
        if (latexVariant != null)
            if (latexVariant.getParent().canClimb && state.is(Blocks.COBWEB))
                ci.cancel();
    }

    private float getFoodMul(TransfurVariant<?> variant) {
        if (isSwimming() || this.isEyeInFluid(FluidTags.WATER) || this.isInWater()) {
            return 1.0f - (variant.swimMultiplier() - 1.0f) * 0.85f;
        }

        else if (onGround && isSprinting()) {
            return 1.0f - (variant.landMultiplier() - 1.0f) * 0.85f;
        }

        return 1.0f;
    }

    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    public void causeFoodExhaustion(float amount, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        if (latexVariant != null && !player.getAbilities().invulnerable && !this.level.isClientSide) {
            ci.cancel();
            player.getFoodData().addExhaustion(amount * getFoodMul(latexVariant.getParent()));
        }
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> callback) {
        ProcessTransfur.ifPlayerTransfurred(EntityUtil.playerOrNull(this), variant -> {
            final float morphProgress = variant.getMorphProgression();

            if (morphProgress < 1f) {
                ChangedEntity ChangedEntity = variant.getChangedEntity();

                final var playerDim = callback.getReturnValue();
                final var latexDim = ChangedEntity.getDimensions(pose);
                float width = Mth.lerp(morphProgress, playerDim.width, latexDim.width);
                float height = Mth.lerp(morphProgress, playerDim.height, latexDim.height);

                callback.setReturnValue(new EntityDimensions(width, height, latexDim.fixed));
            } else {
                callback.setReturnValue(variant.getChangedEntity().getDimensions(pose));
            }
        });
    }

    // TODO make interactions pass the grabbed entity
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"))
    public boolean isSpectatorOrGrabbed(Player player) {
        if (player instanceof LivingEntityDataExtension ext) {
            var grabbedBy = ext.getGrabbedBy();
            var ability = AbstractAbility.getAbilityInstance(grabbedBy, ChangedAbilities.GRAB_ENTITY_ABILITY.get());
            if (ability != null && !ability.grabbedHasControl)
                return true;
        }

        return this.isSpectator();
    }

    @Nullable
    @Override
    public PlayerMoverInstance<?> getPlayerMover() {
        return playerMover;
    }

    @Override
    public void setPlayerMover(@Nullable PlayerMoverInstance<?> playerMover) {
        this.playerMover = playerMover;
    }

    @Override
    public BasicPlayerInfo getBasicPlayerInfo() {
        return basicPlayerInfo;
    }

    @Override
    public void setBasicPlayerInfo(BasicPlayerInfo basicPlayerInfo) {
        this.basicPlayerInfo = basicPlayerInfo;
    }
}
