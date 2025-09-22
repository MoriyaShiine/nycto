/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.client.render.entity.feature.BloodBarrierFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.DarkFormCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.model.DarkFormEntityModel;
import moriyashiine.nycto.client.render.entity.state.BloodBarrierRenderStateAddition;
import moriyashiine.nycto.client.render.entity.state.CarnageRenderStateAddition;
import moriyashiine.nycto.client.render.entity.state.DarkFormEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.mob.DarkFormEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.util.Identifier;

public class DarkFormEntityRenderer extends MobEntityRenderer<DarkFormEntity, DarkFormEntityRenderState, DarkFormEntityModel> {
	public static final Identifier TEXTURE = Nycto.id("textures/entity/dark_form.png");

	public DarkFormEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new DarkFormEntityModel(context.getPart(DarkFormEntityModel.LAYER)), 0.6F);
		addFeature(new HeldItemFeatureRenderer<>(this));
		addFeature(new BloodBarrierFeatureRenderer<>(this, context.getEntityModels()));
		addFeature(new DarkFormCarnageAuraFeatureRenderer(this, context.getEntityModels()));
	}

	@Override
	public DarkFormEntityRenderState createRenderState() {
		return new DarkFormEntityRenderState();
	}

	@Override
	public Identifier getTexture(DarkFormEntityRenderState state) {
		return TEXTURE;
	}

	@Override
	public void updateRenderState(DarkFormEntity entity, DarkFormEntityRenderState state, float tickDelta) {
		super.updateRenderState(entity, state, tickDelta);
		ArmedEntityRenderState.updateRenderState(entity, state, itemModelResolver);
		BloodBarrierRenderStateAddition.updateRenderState(entity, state);
		CarnageRenderStateAddition.updateRenderState(entity, state);
		state.idleAnimationState.copyFrom(entity.idleAnimationState);
		state.idleSneakAnimationState.copyFrom(entity.idleSneakAnimationState);
		state.jumpAnimationState.copyFrom(entity.jumpAnimationState);
		state.flyAnimationState.copyFrom(entity.flyAnimationState);
		state.leftAttackAnimationState.copyFrom(entity.leftAttackAnimationState);
		state.rightAttackAnimationState.copyFrom(entity.rightAttackAnimationState);
		state.onGround = entity.isOnGround();
		state.sprinting = entity.isSprinting();
	}
}
