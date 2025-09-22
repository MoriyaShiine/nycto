/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature.carnage;

import moriyashiine.nycto.client.render.entity.feature.AbstractCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.model.DarkFormEntityModel;
import moriyashiine.nycto.client.render.entity.state.DarkFormEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;

public class DarkFormCarnageAuraFeatureRenderer extends AbstractCarnageAuraFeatureRenderer<DarkFormEntityRenderState, DarkFormEntityModel> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("dark_form"), "carnage_aura");

	private final DarkFormEntityModel model;

	public DarkFormCarnageAuraFeatureRenderer(FeatureRendererContext<DarkFormEntityRenderState, DarkFormEntityModel> context, LoadedEntityModels entityModels) {
		super(context);
		model = new DarkFormEntityModel(entityModels.getModelPart(LAYER));
	}

	@Override
	protected DarkFormEntityModel getEnergySwirlModel() {
		return model;
	}
}
