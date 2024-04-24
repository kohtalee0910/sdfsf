package net.ltxprogrammer.changed.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.client.gui.ContentWarningScreen;
import net.ltxprogrammer.changed.client.tfanimations.TransfurAnimator;
import net.ltxprogrammer.changed.data.BiListener;
import net.ltxprogrammer.changed.entity.LivingEntityDataExtension;
import net.ltxprogrammer.changed.entity.PlayerDataExtension;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changed.entity.PlayerMover;
import net.ltxprogrammer.changed.entity.SeatEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.fluid.AbstractLatexFluid;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedDamageSources;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.network.packet.QueryTransfurPacket;
import net.ltxprogrammer.changed.network.packet.SyncTransfurProgressPacket;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.PatreonBenefits;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EventHandlerClient {
    @OnlyIn(Dist.CLIENT)
    public static final BiListener<UUID, Float> PROGRESS_LISTENER = SyncTransfurProgressPacket.SIGNAL.addListener((uuid, progress) -> {
        var player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        if (player == null)
            return;
        var oldProgress = ProcessTransfur.getPlayerTransfurProgress(player);
        if (Math.abs(oldProgress - progress) < 0.02f) // Prevent sync shudder
            return;
        ProcessTransfur.setPlayerTransfurProgress(player, progress);
    });

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderEntityPre(RenderLivingEvent.Pre<?, ?> event) {
        if (event.getEntity() instanceof LivingEntityDataExtension ext && ext.getGrabbedBy() != null) {
            var grabAbility = AbstractAbility.getAbilityInstance(ext.getGrabbedBy(), ChangedAbilities.GRAB_ENTITY_ABILITY.get());
            if (grabAbility != null && !grabAbility.shouldRenderGrabbedEntity()) {
                event.setCanceled(true);
            } else if (grabAbility != null && grabAbility.shouldRenderGrabbedEntity()) {
                if (grabAbility.grabbedHasControl && grabAbility.syncEntity != null) {
                    grabAbility.syncEntity.mirrorLiving(event.getEntity());
                    float scale = 1F;
                    if (event.getEntity() instanceof Player player) {
                        TransfurVariantInstance.syncInventory(grabAbility.syncEntity, player, false);
                        if (player instanceof PlayerDataExtension ext2) {
                            scale = ext2.getBasicPlayerInfo().getSize();
                        }
                    }
                    PoseStack posestack = event.getPoseStack();
                    posestack.scale(scale,scale,scale);
                    FormRenderHandler.renderLiving(grabAbility.syncEntity, posestack, event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick());
                }
            }
            return;
        }

        var entityGrabAbility = AbstractAbility.getAbilityInstance(event.getEntity(), ChangedAbilities.GRAB_ENTITY_ABILITY.get());
        if (entityGrabAbility != null && !entityGrabAbility.shouldRenderLatex())
            event.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getPlayer();

        if (player.isDeadOrDying() && player.getLastDamageSource() instanceof ChangedDamageSources.TransfurDamageSource) {
            event.setCanceled(true);
            return;
        }

        if (player.vehicle != null && player.vehicle instanceof SeatEntity seat) {
            if (seat.shouldSeatedBeInvisible()) {
                event.setCanceled(true);
                return;
            }
        }

        if (player instanceof PlayerDataExtension ext && ext.getPlayerMover() != null) {
            if (ext.getPlayerMover().is(PlayerMover.WHITE_LATEX_MOVER.get())) {
                event.setCanceled(true);
                return;
            }
        }

        if (event.getEntity() instanceof LivingEntityDataExtension ext && ext.getGrabbedBy() != null) {
            var grabAbility = AbstractAbility.getAbilityInstance(ext.getGrabbedBy(), ChangedAbilities.GRAB_ENTITY_ABILITY.get());
            if (grabAbility != null && grabAbility.suited && !grabAbility.grabbedHasControl) {
                event.setCanceled(true);
                return;
            }
        }

        if (!player.isRemoved() && !player.isSpectator() && !TransfurAnimator.shouldRenderHuman()) {
            if (RenderOverride.renderOverrides(player, null, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick()))
                event.setCanceled(true);
            else if (ProcessTransfur.isPlayerLatex(player)) {
                event.setCanceled(true);
                FormRenderHandler.renderForm(player, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if(!mc.player.isRemoved()) //we need to cache this as the hand may be rendered even in the death screen.
        {
            FormRenderHandler.lastPartialTick = event.getPartialTicks();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        if (!(event.getCamera().getBlockAtCamera().getFluidState().getType() instanceof AbstractLatexFluid abstractLatexFluid)) return;

        event.setNearPlaneDistance(0.25F);
        event.setFarPlaneDistance(1.0F);
        event.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onFogColors(EntityViewRenderEvent.FogColors event) {
        if (!(event.getCamera().getBlockAtCamera().getFluidState().getType() instanceof AbstractLatexFluid abstractLatexFluid)) return;

        var color = abstractLatexFluid.getLatexType().color;
        event.setRed(color.red());
        event.setGreen(color.green());
        event.setBlue(color.blue());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRespawn(ClientPlayerNetworkEvent.RespawnEvent event) {
        Changed.PACKET_HANDLER.sendToServer(QueryTransfurPacket.Builder.of(event.getNewPlayer()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onInputEvent(InputEvent.ClickInputEvent event) {
        if (event.isAttack() || event.isUseItem()) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;

            ProcessTransfur.ifPlayerTransfurred(localPlayer, variant -> {
                variant.ifHasAbility(ChangedAbilities.GRAB_ENTITY_ABILITY.get(), ability -> {
                    if (ability.grabbedEntity != null && !ability.suited) {
                        event.setCanceled(true);
                        event.setSwingHand(false);
                    }
                });
            });
        }
    }

    /**
     * This function needs to be static
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRegisterReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ChangedClient.particleSystem);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onSetScreen(ScreenOpenEvent event) {
        if (event.getScreen() instanceof TitleScreen && Changed.config.client.showContentWarning.get()) {
            // Comment this line out to disable the content warning screen
            event.setScreen(new ContentWarningScreen());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEventHandler {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onNameFormat(PlayerEvent.NameFormat event) {
            if (event.getEntity() instanceof Player player)
                event.setDisplayname(PatreonBenefits.getPlayerName(player, event.getDisplayname()));
        }

        @SubscribeEvent
        public static void onChangedVariant(ProcessTransfur.EntityVariantAssigned.ChangedVariant event) {
            if (event.livingEntity.level.isClientSide)
                return;

            if (event.livingEntity instanceof Player player && player.isCreative())
                return; // Don't do effect if player is creative mode

            if (event.oldVariant != null || event.newVariant == null || event.livingEntity.tickCount < 20)
                return; // Only do effect if player was human

            event.livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 6 * 20, 1, false, false));
            if (event.newVariant.getEntityType().is(ChangedTags.EntityTypes.ORGANIC_LATEX))
                return; // Only do blindness if variant is goo

            event.livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2 * 20, 1, false, false));
        }
    }
}
