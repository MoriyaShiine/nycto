/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.projectile.BloodFlechetteEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.ArrowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BloodFlechetteEntityRenderer extends EntityRenderer<BloodFlechetteEntity, ProjectileEntityRenderState> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/projectiles/blood_flechette.png");

	private final ArrowEntityModel model;

	public BloodFlechetteEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		model = new ArrowEntityModel(context.getPart(EntityModelLayers.ARROW));
	}

	@Override
	public ProjectileEntityRenderState createRenderState() {
		return new ProjectileEntityRenderState();
	}

	@Override
	public void render(ProjectileEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch));
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
		model.setAngles(state);
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		matrices.pop();
		super.render(state, matrices, vertexConsumers, light);
	}

	@Override
	public void updateRenderState(BloodFlechetteEntity entity, ProjectileEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.pitch = entity.getLerpedPitch(tickProgress);
		state.yaw = entity.getLerpedYaw(tickProgress);
	}
}
