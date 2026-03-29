/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers.carnage;

import moriyashiine.nycto.client.renderer.entity.layers.AbstractCarnageAuraLayer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class PlayerCarnageAuraLayer extends AbstractCarnageAuraLayer<AvatarRenderState, PlayerModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("player"), "carnage_aura");
	public static final ModelLayerLocation LAYER_SLIM = new ModelLayerLocation(Nycto.id("player_slim"), "carnage_aura");

	private final PlayerModel model;

	public PlayerCarnageAuraLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer, EntityModelSet modelSet, boolean slim) {
		super(renderer);
		model = new PlayerModel(modelSet.bakeLayer(slim ? LAYER_SLIM : LAYER), slim);
	}

	@Override
	protected PlayerModel model() {
		return model;
	}
}
