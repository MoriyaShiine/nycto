/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature.carnage;

import moriyashiine.nycto.client.render.entity.feature.AbstractCarnageAuraFeatureRenderer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

public class PlayerCarnageAuraFeatureRenderer extends AbstractCarnageAuraFeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("player"), "carnage_aura");
	public static final EntityModelLayer LAYER_SLIM = new EntityModelLayer(Nycto.id("player_slim"), "carnage_aura");

	private final PlayerEntityModel model;

	public PlayerCarnageAuraFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels entityModels, boolean slim) {
		super(context);
		model = new PlayerEntityModel(entityModels.getModelPart(slim ? LAYER_SLIM : LAYER), slim);
	}

	@Override
	protected PlayerEntityModel getEnergySwirlModel() {
		return model;
	}
}
