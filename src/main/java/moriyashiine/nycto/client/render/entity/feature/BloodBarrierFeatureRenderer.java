/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature;

import moriyashiine.nycto.client.render.entity.model.BloodBarrierModel;
import moriyashiine.nycto.client.render.entity.state.BloodBarrierRenderStateAddition;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BloodBarrierFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends FeatureRenderer<S, M> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/blood_barrier.png");

	private final BloodBarrierModel model;

	public BloodBarrierFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels entityModels) {
		super(context);
		model = new BloodBarrierModel(entityModels.getModelPart(BloodBarrierModel.LAYER));
	}

	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
		int barriers = ((BloodBarrierRenderStateAddition) state).nycto$getBloodBarriers();
		for (int i = 0; i < barriers; i++) {
			matrices.push();
			matrices.translate(0, -BloodBarrierComponent.getHeightOffset(i, state.height), 0);
			float yRotation = state.age * 12;
			if (i == 0) {
				yRotation *= -1;
			}
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-state.bodyYaw + yRotation));
			queue.getBatchingQueue(1).submitModel(model, state, matrices, model.getLayer(TEXTURE), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
			matrices.pop();
		}
	}
}
