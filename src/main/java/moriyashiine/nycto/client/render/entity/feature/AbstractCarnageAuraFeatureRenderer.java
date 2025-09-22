/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature;

import moriyashiine.nycto.client.render.entity.state.CarnageRenderStateAddition;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public abstract class AbstractCarnageAuraFeatureRenderer<S extends EntityRenderState, M extends EntityModel<S>> extends EnergySwirlOverlayFeatureRenderer<S, M> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/carnage_aura.png");

	public AbstractCarnageAuraFeatureRenderer(FeatureRendererContext<S, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
		if (shouldRender(state)) {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getBreezeWind(TEXTURE, 0, state.age * -0.01F % 1));
			getEnergySwirlModel().setAngles(state);
			getEnergySwirlModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, ColorHelper.getArgb((int) (((CarnageRenderStateAddition) state).nycto$getCarnageOpacity() * 255), 255, 255, 255));
		}
	}

	@Override
	protected boolean shouldRender(S state) {
		return ((CarnageRenderStateAddition) state).nycto$getCarnageOpacity() > 0;
	}

	@Override
	protected float getEnergySwirlX(float partialAge) {
		return 0;
	}

	@Override
	protected Identifier getEnergySwirlTexture() {
		return TEXTURE;
	}
}
