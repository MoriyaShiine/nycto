/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature;

import moriyashiine.nycto.client.render.entity.state.BloodrushRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class BloodrushAuraFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("player"), "bloodrush_aura");
	public static final EntityModelLayer LAYER_SLIM = new EntityModelLayer(Nycto.id("player_slim"), "bloodrush_aura");
	private static final Identifier TEXTURE = Nycto.id("textures/entity/bloodrush_aura.png");

	private final PlayerEntityModel model;

	public BloodrushAuraFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels entityModels, boolean slim) {
		super(context);
		model = new PlayerEntityModel(entityModels.getModelPart(slim ? LAYER_SLIM : LAYER), slim);
	}

	@Override
	protected boolean shouldRender(PlayerEntityRenderState state) {
		@Nullable BloodrushRenderState bloodrushRenderState = state.getData(BloodrushRenderState.KEY);
		return bloodrushRenderState != null && bloodrushRenderState.usingBloodrush;
	}

	@Override
	protected float getEnergySwirlX(float partialAge) {
		return MathHelper.cos(partialAge * 0.02F) / 2;
	}

	@Override
	protected Identifier getEnergySwirlTexture() {
		return TEXTURE;
	}

	@Override
	protected PlayerEntityModel getEnergySwirlModel() {
		return model;
	}
}
