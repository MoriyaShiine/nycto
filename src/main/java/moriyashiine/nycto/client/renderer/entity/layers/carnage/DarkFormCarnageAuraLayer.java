/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers.carnage;

import moriyashiine.nycto.client.renderer.entity.layers.AbstractCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.DarkFormModel;
import moriyashiine.nycto.client.renderer.entity.state.DarkFormRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.RenderLayerParent;

public class DarkFormCarnageAuraLayer extends AbstractCarnageAuraLayer<DarkFormRenderState, DarkFormModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("dark_form"), "carnage_aura");

	private final DarkFormModel model;

	public DarkFormCarnageAuraLayer(RenderLayerParent<DarkFormRenderState, DarkFormModel> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new DarkFormModel(modelSet.bakeLayer(LAYER));
	}

	@Override
	protected DarkFormModel model() {
		return model;
	}
}
