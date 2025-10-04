/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.feature;

import moriyashiine.nycto.client.render.entity.state.CarnageRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCarnageAuraFeatureRenderer<S extends EntityRenderState, M extends EntityModel<S>> extends EnergySwirlOverlayFeatureRenderer<S, M> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/carnage_aura.png");

	public AbstractCarnageAuraFeatureRenderer(FeatureRendererContext<S, M> context) {
		super(context);
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
		if (shouldRender(state)) {
			RenderLayer renderLayer = RenderLayer.getBreezeWind(TEXTURE, 0, state.age * -0.01F % 1);
			int color = ColorHelper.getArgb((int) (state.getData(CarnageRenderState.KEY).carnageOpacity * 255), 255, 255, 255);
			queue.getBatchingQueue(1).submitModel(getEnergySwirlModel(), state, matrices, renderLayer, light, OverlayTexture.DEFAULT_UV, color, null, state.outlineColor, null);
		}
	}

	@Override
	protected boolean shouldRender(S state) {
		@Nullable CarnageRenderState carnageRenderState = state.getData(CarnageRenderState.KEY);
		return carnageRenderState != null && carnageRenderState.carnageOpacity > 0;
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
