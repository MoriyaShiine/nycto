/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.state.CarnageRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public abstract class AbstractCarnageAuraLayer<S extends EntityRenderState, M extends EntityModel<S>> extends EnergySwirlLayer<S, M> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/carnage/carnage_aura.png");

	public AbstractCarnageAuraLayer(RenderLayerParent<S, M> renderer) {
		super(renderer);
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
		if (isPowered(state)) {
			RenderType renderType = RenderTypes.breezeWind(TEXTURE, 0, state.ageInTicks * -0.01F % 1);
			int color = ARGB.color((int) (state.getData(CarnageRenderState.KEY).carnageOpacity * 255), 255, 255, 255);
			submitNodeCollector.order(1).submitModel(model(), state, poseStack, renderType, lightCoords, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
		}
	}

	@Override
	protected boolean isPowered(S state) {
		CarnageRenderState carnageRenderState = state.getData(CarnageRenderState.KEY);
		return carnageRenderState != null && carnageRenderState.carnageOpacity > 0;
	}

	@Override
	protected float xOffset(float t) {
		return 0;
	}

	@Override
	protected Identifier getTextureLocation() {
		return TEXTURE;
	}
}
