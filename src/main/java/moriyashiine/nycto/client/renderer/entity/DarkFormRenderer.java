/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity;

import moriyashiine.nycto.client.renderer.entity.layers.BloodBarrierLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.DarkFormCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.DarkFormModel;
import moriyashiine.nycto.client.renderer.entity.state.BloodBarrierRenderState;
import moriyashiine.nycto.client.renderer.entity.state.CarnageRenderState;
import moriyashiine.nycto.client.renderer.entity.state.DarkFormRenderState;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.world.entity.monster.DarkForm;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.Identifier;

public class DarkFormRenderer extends MobRenderer<DarkForm, DarkFormRenderState, DarkFormModel> {
	public static final Identifier TEXTURE = Nycto.id("textures/entity/darm_form/dark_form.png");

	public DarkFormRenderer(EntityRendererProvider.Context context) {
		super(context, new DarkFormModel(context.bakeLayer(DarkFormModel.LAYER)), 0.6F);
		addLayer(new ItemInHandLayer<>(this));
		addLayer(new BloodBarrierLayer<>(this, context.getModelSet()));
		addLayer(new DarkFormCarnageAuraLayer(this, context.getModelSet()));
	}

	@Override
	public DarkFormRenderState createRenderState() {
		return new DarkFormRenderState();
	}

	@Override
	public Identifier getTextureLocation(DarkFormRenderState state) {
		return TEXTURE;
	}

	@Override
	public void extractRenderState(DarkForm entity, DarkFormRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		ArmedEntityRenderState.extractArmedEntityRenderState(entity, state, itemModelResolver, partialTicks);
		BloodBarrierRenderState.extractRenderState(entity, state);
		CarnageRenderState.extractRenderState(entity, state);
		state.idleAnimationState.copyFrom(entity.idleAnimationState);
		state.idleSneakAnimationState.copyFrom(entity.idleSneakAnimationState);
		state.jumpAnimationState.copyFrom(entity.jumpAnimationState);
		state.flyAnimationState.copyFrom(entity.flyAnimationState);
		state.leftAttackAnimationState.copyFrom(entity.leftAttackAnimationState);
		state.rightAttackAnimationState.copyFrom(entity.rightAttackAnimationState);
		state.onGround = entity.onGround();
		state.sprinting = entity.isSprinting();
	}
}
