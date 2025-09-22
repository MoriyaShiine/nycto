/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature.carnage;

import moriyashiine.nycto.client.render.entity.feature.AbstractCarnageAuraFeatureRenderer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.BatEntityRenderState;

public class BatCarnageAuraFeatureRenderer extends AbstractCarnageAuraFeatureRenderer<BatEntityRenderState, BatEntityModel> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("bat"), "carnage_aura");

	private final BatEntityModel model;

	public BatCarnageAuraFeatureRenderer(FeatureRendererContext<BatEntityRenderState, BatEntityModel> context, LoadedEntityModels entityModels) {
		super(context);
		model = new BatEntityModel(entityModels.getModelPart(LAYER));
	}

	@Override
	protected BatEntityModel getEnergySwirlModel() {
		return model;
	}
}
