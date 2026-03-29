/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import moriyashiine.nycto.client.renderer.entity.model.BloodBarrierModel;
import moriyashiine.nycto.client.renderer.entity.state.BloodBarrierRenderState;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class BloodBarrierLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/blood_barrier/blood_barrier.png");

	private final BloodBarrierModel model;

	public BloodBarrierLayer(RenderLayerParent<S, M> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new BloodBarrierModel(modelSet.bakeLayer(BloodBarrierModel.LAYER));
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
		@Nullable BloodBarrierRenderState bloodBarrierRenderState = state.getData(BloodBarrierRenderState.KEY);
		if (bloodBarrierRenderState != null) {
			for (int i = 0; i < bloodBarrierRenderState.bloodBarriers; i++) {
				poseStack.pushPose();
				poseStack.translate(0, -BloodBarrierComponent.getHeightOffset(i, state.boundingBoxHeight), 0);
				float yRotation = state.ageInTicks * 12;
				if (i == 0) {
					yRotation *= -1;
				}
				poseStack.mulPose(Axis.YP.rotationDegrees(-state.bodyRot + yRotation));
				submitNodeCollector.order(1).submitModel(model, state, poseStack, model.renderType(TEXTURE), lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
				poseStack.popPose();
			}
		}
	}
}
