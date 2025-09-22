/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.client.render.entity.feature.BloodBarrierFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.VampireCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.model.VampireEntityModel;
import moriyashiine.nycto.client.render.entity.state.BloodBarrierRenderStateAddition;
import moriyashiine.nycto.client.render.entity.state.CarnageRenderStateAddition;
import moriyashiine.nycto.client.render.entity.state.VampireEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.mob.VampireEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;

public class VampireEntityRenderer extends BipedEntityRenderer<VampireEntity, VampireEntityRenderState, VampireEntityModel> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/vampire/vampire.png");
	private static final Identifier EYES_TEXTURE = Nycto.id("textures/entity/vampire/vampire_eyes.png");

	public VampireEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new VampireEntityModel(context.getPart(VampireEntityModel.LAYER)), 0.5F);
		addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(EYES_TEXTURE);
			}
		});
		addFeature(new BloodBarrierFeatureRenderer<>(this, context.getEntityModels()));
		addFeature(new VampireCarnageAuraFeatureRenderer(this, context.getEntityModels()));
	}

	@Override
	public VampireEntityRenderState createRenderState() {
		return new VampireEntityRenderState();
	}

	@Override
	public Identifier getTexture(VampireEntityRenderState state) {
		return TEXTURE;
	}

	@Override
	public void updateRenderState(VampireEntity entity, VampireEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		BloodBarrierRenderStateAddition.updateRenderState(entity, state);
		CarnageRenderStateAddition.updateRenderState(entity, state);
		state.attacking = entity.getDataTracker().get(VampireEntity.ATTACKING) || state.handSwingProgress > 0;
		state.limbAmplitudeInverse *= 2;
	}
}
