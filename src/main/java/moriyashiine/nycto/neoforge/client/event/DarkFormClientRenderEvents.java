/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.DarkFormModel;
import moriyashiine.nycto.neoforge.client.renderer.layer.BloodBarrierLayer;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import moriyashiine.nycto.neoforge.network.DarkFormClientState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, value = Dist.CLIENT)
public final class DarkFormClientRenderEvents {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/dark_form/dark_form.png");
	private static final ResourceLocation CARNAGE_AURA = NyctoNeoForge.id("textures/entity/carnage/carnage_aura.png");
	private static DarkFormModel<AbstractClientPlayer> model;
	private static DarkFormModel<AbstractClientPlayer> carnageModel;
	private static DarkFormModel<AbstractClientPlayer> firstPersonModel;
	private static final Map<Integer, DarkFormModel.AnimationContext> ANIMATION_CONTEXTS = new ConcurrentHashMap<>();

	private DarkFormClientRenderEvents() {
	}

	@SubscribeEvent
	public static void cleanupAnimationContexts(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			ANIMATION_CONTEXTS.clear();
			return;
		}
		ANIMATION_CONTEXTS.keySet().removeIf(id -> {
			var entity = minecraft.level.getEntity(id);
			return entity == null || !DarkFormClientState.isActive(entity);
		});
	}

	@SubscribeEvent
	public static void renderDarkForm(RenderLivingEvent.Post<?, ?> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer player) || !DarkFormClientState.isActive(player)) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}
		if (model == null) {
			model = new DarkFormModel<>(minecraft.getEntityModels().bakeLayer(DarkFormModel.LAYER));
		}
		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();
		applyLivingModelTransform(player, poseStack, event.getPartialTick());
		float ageInTicks = player.tickCount + event.getPartialTick();
		float limbSwingAmount = player.walkAnimation.speed(event.getPartialTick());
		float limbSwing = player.walkAnimation.position(event.getPartialTick());
		DarkFormModel.AnimationContext animationContext = ANIMATION_CONTEXTS.computeIfAbsent(player.getId(), ignored -> new DarkFormModel.AnimationContext());
		model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, player.getYHeadRot() - player.yBodyRot, player.getXRot(), animationContext, DarkFormClientState.isJumping(player));
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(TEXTURE));
		model.renderToBuffer(poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
		renderHeldItems(player, poseStack, event.getMultiBufferSource(), event.getPackedLight());
		renderCarnageAura(event, player, poseStack, ageInTicks, limbSwing, limbSwingAmount, animationContext);
		poseStack.popPose();
		BloodBarrierLayer.renderBarriers(poseStack, event.getMultiBufferSource(), event.getPackedLight(), player, event.getPartialTick(), ageInTicks);
	}

	private static void applyLivingModelTransform(AbstractClientPlayer player, PoseStack poseStack, float partialTick) {
		float bodyRot = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyRot));
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.501F, 0.0F);
	}

	private static void renderHeldItems(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		boolean rightMainArm = player.getMainArm() == HumanoidArm.RIGHT;
		ItemStack leftStack = rightMainArm ? player.getOffhandItem() : player.getMainHandItem();
		ItemStack rightStack = rightMainArm ? player.getMainHandItem() : player.getOffhandItem();
		if (rightStack.isEmpty() && leftStack.isEmpty()) {
			return;
		}
		poseStack.pushPose();
		renderHeldItem(player, rightStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, bufferSource, packedLight);
		renderHeldItem(player, leftStack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, bufferSource, packedLight);
		poseStack.popPose();
	}

	private static void renderHeldItem(AbstractClientPlayer player, ItemStack stack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		if (stack.isEmpty()) {
			return;
		}
		poseStack.pushPose();
		model.translateToHand(arm, poseStack);
		poseStack.mulPose(Axis.XP.rotationDegrees(-90));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));
		boolean left = arm == HumanoidArm.LEFT;
		poseStack.translate((left ? -1F : 1F) / 16F, 0.125F, -0.625F);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stack, displayContext, left, poseStack, bufferSource, packedLight);
		poseStack.popPose();
	}

	private static void renderCarnageAura(RenderLivingEvent.Post<?, ?> event, AbstractClientPlayer player, PoseStack poseStack, float ageInTicks, float limbSwing, float limbSwingAmount, DarkFormModel.AnimationContext animationContext) {
		float opacity = CarnageClientState.opacity(player, 0.5F);
		if (opacity <= 0 || player.isInvisible()) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (carnageModel == null) {
			carnageModel = new DarkFormModel<>(minecraft.getEntityModels().bakeLayer(DarkFormModel.LAYER));
		}
		poseStack.pushPose();
		poseStack.scale(1.04F, 1.04F, 1.04F);
		carnageModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, player.getYHeadRot() - player.yBodyRot, player.getXRot(), animationContext, DarkFormClientState.isJumping(player));
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.breezeWind(CARNAGE_AURA, 0, ageInTicks * -0.01F % 1.0F));
		int alpha = Math.max(0, Math.min(255, (int) (opacity * 255)));
		carnageModel.renderToBuffer(poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, alpha << 24 | 0xFFFFFF);
		poseStack.popPose();
	}

	@SubscribeEvent
	public static void hideDarkFormArm(RenderArmEvent event) {
		if (DarkFormClientState.isActive(event.getPlayer())) {
			renderDarkFormArm(event);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void hideDarkFormHand(RenderHandEvent event) {
		// Keep vanilla item rendering. RenderArmEvent replaces the visible player arm with a Dark Form claw.
	}

	private static void renderDarkFormArm(RenderArmEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}
		if (firstPersonModel == null) {
			firstPersonModel = new DarkFormModel<>(minecraft.getEntityModels().bakeLayer(DarkFormModel.LAYER));
		}
		AbstractClientPlayer player = event.getPlayer();
		float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(false);
		float ageInTicks = player.tickCount + partialTick;
		float limbSwingAmount = player.walkAnimation.speed(partialTick);
		float limbSwing = player.walkAnimation.position(partialTick);
		DarkFormModel.AnimationContext animationContext = ANIMATION_CONTEXTS.computeIfAbsent(player.getId(), ignored -> new DarkFormModel.AnimationContext());
		firstPersonModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, player.getYHeadRot() - player.yBodyRot, player.getXRot(), animationContext, DarkFormClientState.isJumping(player));

		HumanoidArm arm = event.getArm();
		float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();
		poseStack.translate(side * 0.76F, -0.72F, -0.92F);
		poseStack.mulPose(Axis.YP.rotationDegrees(side * 38.0F));
		poseStack.mulPose(Axis.XP.rotationDegrees(-28.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(side * -18.0F));
		float pulse = Mth.sin(ageInTicks * 0.16F) * 0.015F;
		poseStack.translate(side * pulse, pulse, 0);
		poseStack.scale(1.28F, 1.28F, 1.28F);
		VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(TEXTURE));
		firstPersonModel.renderArm(arm, poseStack, consumer, event.getPackedLight(), OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
		poseStack.popPose();
	}
}
