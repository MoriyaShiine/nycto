/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature.carnage;

import moriyashiine.nycto.client.render.entity.feature.AbstractCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.model.VampireEntityModel;
import moriyashiine.nycto.client.render.entity.state.VampireEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;

public class VampireCarnageAuraFeatureRenderer extends AbstractCarnageAuraFeatureRenderer<VampireEntityRenderState, VampireEntityModel> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("vampire"), "carnage_aura");

	private final VampireEntityModel model;

	public VampireCarnageAuraFeatureRenderer(FeatureRendererContext<VampireEntityRenderState, VampireEntityModel> context, LoadedEntityModels entityModels) {
		super(context);
		model = new VampireEntityModel(entityModels.getModelPart(LAYER));
	}

	@Override
	protected VampireEntityModel getEnergySwirlModel() {
		return model;
	}
}
