/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.client.model.BloodBarrierModel;
import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class BloodBarrierLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/blood_barrier/blood_barrier.png");

	private static BloodBarrierModel<LivingEntity> sharedModel;

	private final BloodBarrierModel<T> model;

	public BloodBarrierLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new BloodBarrierModel<>(modelSet.bakeLayer(BloodBarrierModel.LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		renderBarriers(poseStack, bufferSource, packedLight, entity, partialTick, ageInTicks);
	}

	public static void renderBarriers(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, LivingEntity entity, float partialTick, float ageInTicks) {
		int layers = bloodBarrierLayers(entity);
		if (layers <= 0 || entity.isInvisible()) {
			return;
		}
		BloodBarrierModel<LivingEntity> model = sharedModel();
		for (int i = 0; i < layers; i++) {
			poseStack.pushPose();
			poseStack.translate(0, -heightOffset(i, entity.getBbHeight()), 0);
			float rotation = ageInTicks * 12;
			if (i == 0) {
				rotation *= -1;
			}
			poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot) + rotation));
			model.renderToBuffer(poseStack, bufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, -1);
			poseStack.popPose();
		}
	}

	private static int bloodBarrierLayers(LivingEntity entity) {
		if (entity instanceof Player player) {
			return NyctoData.getBloodBarrierLayers(player);
		}
		if (entity instanceof Vampire vampire) {
			return vampire.getBloodBarrierLayers();
		}
		return Mth.clamp(entity.getPersistentData().getInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS), 0, 3);
	}

	private static float heightOffset(int barrier, float height) {
		return switch (barrier) {
			case 0 -> height * 0.5F;
			case 1 -> height * 0.2F;
			default -> height * 0.8F;
		};
	}

	private static BloodBarrierModel<LivingEntity> sharedModel() {
		if (sharedModel == null) {
			sharedModel = new BloodBarrierModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(BloodBarrierModel.LAYER));
		}
		return sharedModel;
	}
}
