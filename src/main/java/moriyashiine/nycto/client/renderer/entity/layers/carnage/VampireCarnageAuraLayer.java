/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers.carnage;

import moriyashiine.nycto.client.renderer.entity.layers.AbstractCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.VampireModel;
import moriyashiine.nycto.client.renderer.entity.state.VampireRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.RenderLayerParent;

public class VampireCarnageAuraLayer extends AbstractCarnageAuraLayer<VampireRenderState, VampireModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("vampire"), "carnage_aura");

	private final VampireModel model;

	public VampireCarnageAuraLayer(RenderLayerParent<VampireRenderState, VampireModel> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new VampireModel(modelSet.bakeLayer(LAYER));
	}

	@Override
	protected VampireModel model() {
		return model;
	}
}
