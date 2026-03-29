/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity;

import moriyashiine.nycto.client.renderer.entity.layers.BloodBarrierLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.VampireCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.VampireModel;
import moriyashiine.nycto.client.renderer.entity.state.BloodBarrierRenderState;
import moriyashiine.nycto.client.renderer.entity.state.CarnageRenderState;
import moriyashiine.nycto.client.renderer.entity.state.VampireRenderState;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class VampireRenderer extends HumanoidMobRenderer<Vampire, VampireRenderState, VampireModel> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/vampire/vampire.png");
	private static final Identifier EYES_TEXTURE = Nycto.id("textures/entity/vampire/vampire_eyes.png");

	public VampireRenderer(EntityRendererProvider.Context context) {
		super(context, new VampireModel(context.bakeLayer(VampireModel.LAYER)), 0.5F);
		addLayer(new EyesLayer<>(this) {
			@Override
			public RenderType renderType() {
				return RenderTypes.eyes(EYES_TEXTURE);
			}
		});
		addLayer(new BloodBarrierLayer<>(this, context.getModelSet()));
		addLayer(new VampireCarnageAuraLayer(this, context.getModelSet()));
	}

	@Override
	public VampireRenderState createRenderState() {
		return new VampireRenderState();
	}

	@Override
	public Identifier getTextureLocation(VampireRenderState state) {
		return TEXTURE;
	}

	@Override
	public void extractRenderState(Vampire entity, VampireRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		BloodBarrierRenderState.extractRenderState(entity, state);
		CarnageRenderState.extractRenderState(entity, state);
		state.attacking = entity.getEntityData().get(Vampire.ATTACKING) || state.attackTime > 0;
		state.speedValue *= 2;
	}
}
