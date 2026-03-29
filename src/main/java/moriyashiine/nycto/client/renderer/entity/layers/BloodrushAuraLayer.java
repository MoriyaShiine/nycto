/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers;

import moriyashiine.nycto.client.renderer.entity.state.BloodrushRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

public class BloodrushAuraLayer extends EnergySwirlLayer<AvatarRenderState, PlayerModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("player"), "bloodrush_aura");
	public static final ModelLayerLocation LAYER_SLIM = new ModelLayerLocation(Nycto.id("player_slim"), "bloodrush_aura");
	private static final Identifier TEXTURE = Nycto.id("textures/entity/bloodrush/bloodrush_aura.png");

	private final PlayerModel model;

	public BloodrushAuraLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer, EntityModelSet modelSet, boolean slim) {
		super(renderer);
		model = new PlayerModel(modelSet.bakeLayer(slim ? LAYER_SLIM : LAYER), slim);
	}

	@Override
	protected boolean isPowered(AvatarRenderState state) {
		@Nullable BloodrushRenderState bloodrushRenderState = state.getData(BloodrushRenderState.KEY);
		return bloodrushRenderState != null && bloodrushRenderState.usingBloodrush;
	}

	@Override
	protected float xOffset(float t) {
		return Mth.cos(t * 0.02F) / 2;
	}

	@Override
	protected Identifier getTextureLocation() {
		return TEXTURE;
	}

	@Override
	protected PlayerModel model() {
		return model;
	}
}
