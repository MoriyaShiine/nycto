/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.renderer.layer.BloodBarrierLayer;
import moriyashiine.nycto.neoforge.network.BatFormClientState;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ambient.Bat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, value = Dist.CLIENT)
public final class BatFormClientRenderEvents {
	private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/bat.png");
	private static final ResourceLocation CARNAGE_AURA = NyctoNeoForge.id("textures/entity/carnage/carnage_aura.png");
	private static final int BAT_FORM_TINT = 0xFFFFFFFF;
	private static BatModel model;
	private static Bat displayBat;

	private BatFormClientRenderEvents() {
	}

	@SubscribeEvent
	public static void renderBatForm(RenderLivingEvent.Post<?, ?> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer player) || !BatFormClientState.isActive(player)) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}
		if (model == null) {
			model = new BatModel(minecraft.getEntityModels().bakeLayer(ModelLayers.BAT));
		}
		if (displayBat == null || displayBat.level() != minecraft.level) {
			displayBat = new Bat(EntityType.BAT, minecraft.level);
		}
		displayBat.tickCount = player.tickCount;
		displayBat.setResting(isClingingToCeiling(player));
		if (displayBat.isResting()) {
			displayBat.flyAnimationState.stop();
			displayBat.restAnimationState.startIfStopped(player.tickCount);
		} else {
			displayBat.restAnimationState.stop();
			displayBat.flyAnimationState.startIfStopped(player.tickCount);
		}
		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();
		applyLivingModelTransform(player, poseStack, event.getPartialTick());
		poseStack.scale(1.15F, 1.15F, 1.15F);
		float bodyRot = Mth.rotLerp(event.getPartialTick(), player.yBodyRotO, player.yBodyRot);
		model.setupAnim(displayBat, 0, 0, player.tickCount + event.getPartialTick(), player.getYHeadRot() - bodyRot, player.getXRot());
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.entityCutout(TEXTURE));
		model.renderToBuffer(poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, BAT_FORM_TINT);
		renderCarnageAura(event, player, poseStack, player.tickCount + event.getPartialTick());
		poseStack.popPose();
		BloodBarrierLayer.renderBarriers(poseStack, event.getMultiBufferSource(), event.getPackedLight(), player, event.getPartialTick(), player.tickCount + event.getPartialTick());
	}

	private static void applyLivingModelTransform(AbstractClientPlayer player, PoseStack poseStack, float partialTick) {
		float bodyRot = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyRot));
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.501F, 0.0F);
	}

	private static boolean isClingingToCeiling(AbstractClientPlayer player) {
		if (!player.horizontalCollision && player.getDeltaMovement().horizontalDistanceSqr() >= 0.0025 || player.swinging) {
			return false;
		}
		BlockPos ceiling = BlockPos.containing(player.getX(), player.getBoundingBox().maxY + 0.05, player.getZ());
		return player.level().getBlockState(ceiling).isFaceSturdy(player.level(), ceiling, net.minecraft.core.Direction.DOWN);
	}

	private static void renderCarnageAura(RenderLivingEvent.Post<?, ?> event, AbstractClientPlayer player, PoseStack poseStack, float ageInTicks) {
		float opacity = CarnageClientState.opacity(player, 0.5F);
		if (opacity <= 0) {
			return;
		}
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.breezeWind(CARNAGE_AURA, 0, ageInTicks * -0.01F % 1.0F));
		int alpha = Math.max(0, Math.min(255, (int) (opacity * 255)));
		model.renderToBuffer(poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, alpha << 24 | 0xFFFFFF);
	}

	@SubscribeEvent
	public static void hideBatFormArm(RenderArmEvent event) {
		if (BatFormClientState.isActive(event.getPlayer())) {
			renderBatFormArm(event);
			event.setCanceled(true);
		}
	}

	private static void renderBatFormArm(RenderArmEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}
		if (model == null) {
			model = new BatModel(minecraft.getEntityModels().bakeLayer(ModelLayers.BAT));
		}
		if (displayBat == null || displayBat.level() != minecraft.level) {
			displayBat = new Bat(EntityType.BAT, minecraft.level);
		}
		AbstractClientPlayer player = event.getPlayer();
		float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(false);
		float ageInTicks = player.tickCount + partialTick;
		displayBat.tickCount = player.tickCount;
		displayBat.setResting(false);
		displayBat.restAnimationState.stop();
		displayBat.flyAnimationState.startIfStopped(player.tickCount);
		model.setupAnim(displayBat, 0, 0, ageInTicks, player.getYHeadRot(), player.getXRot());

		HumanoidArm arm = event.getArm();
		float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
		ModelPart body = model.root().getChild("body");
		ModelPart wing = arm == HumanoidArm.RIGHT ? body.getChild("right_wing") : body.getChild("left_wing");
		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();
		poseStack.translate(side * 0.45F, -0.58F, -0.84F);
		poseStack.mulPose(Axis.YP.rotationDegrees(side * 36.0F));
		poseStack.mulPose(Axis.XP.rotationDegrees(-12.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(side * -20.0F));
		float flap = Mth.sin(ageInTicks * 0.45F) * 0.035F;
		poseStack.translate(side * flap, flap * 0.5F, 0);
		poseStack.scale(1.7F, 1.7F, 1.7F);
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(TEXTURE));
		wing.render(poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, BAT_FORM_TINT);
		poseStack.popPose();
	}
}
