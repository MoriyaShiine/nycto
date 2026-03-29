/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity;

import moriyashiine.nycto.client.renderer.entity.model.HunterModel;
import moriyashiine.nycto.client.renderer.entity.state.HunterRenderState;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;

public class HunterRenderer extends IllagerRenderer<Hunter, HunterRenderState> {
	public HunterRenderer(EntityRendererProvider.Context context) {
		super(context, new HunterModel(context.bakeLayer(HunterModel.LAYER)), 0.5F);
		addLayer(new ItemInHandLayer<>(this));
	}

	@Override
	public HunterRenderState createRenderState() {
		return new HunterRenderState();
	}

	@Override
	public Identifier getTextureLocation(HunterRenderState state) {
		return state.hunterType.texture();
	}

	@Override
	public void extractRenderState(Hunter entity, HunterRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.hunterType = entity.getEntityData().get(Hunter.HUNTER_TYPE_ID);
	}
}
