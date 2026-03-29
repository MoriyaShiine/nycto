/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers.carnage;

import moriyashiine.nycto.client.renderer.entity.layers.AbstractCarnageAuraLayer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.BatRenderState;

public class BatCarnageAuraLayer extends AbstractCarnageAuraLayer<BatRenderState, BatModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("bat"), "carnage_aura");

	private final BatModel model;

	public BatCarnageAuraLayer(RenderLayerParent<BatRenderState, BatModel> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new BatModel(modelSet.bakeLayer(LAYER));
	}

	@Override
	protected BatModel model() {
		return model;
	}
}
