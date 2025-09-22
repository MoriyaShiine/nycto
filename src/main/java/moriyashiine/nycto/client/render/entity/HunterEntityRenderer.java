/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.client.render.entity.model.HunterEntityModel;
import moriyashiine.nycto.client.render.entity.state.HunterEntityRenderState;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

public class HunterEntityRenderer extends IllagerEntityRenderer<HunterEntity, HunterEntityRenderState> {
	public HunterEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new HunterEntityModel(context.getPart(HunterEntityModel.LAYER)), 0.5F);
		addFeature(new HeldItemFeatureRenderer<>(this));
	}

	@Override
	public HunterEntityRenderState createRenderState() {
		return new HunterEntityRenderState();
	}

	@Override
	public Identifier getTexture(HunterEntityRenderState state) {
		return state.hunterType.texture();
	}

	@Override
	public void updateRenderState(HunterEntity entity, HunterEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.hunterType = entity.getDataTracker().get(HunterEntity.HUNTER_TYPE);
	}
}
