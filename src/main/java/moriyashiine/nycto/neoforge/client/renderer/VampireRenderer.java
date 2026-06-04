/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.VampireModel;
import moriyashiine.nycto.neoforge.entity.Vampire;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class VampireRenderer extends HumanoidMobRenderer<Vampire, VampireModel> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/vampire/vampire.png");
	private static final ResourceLocation EYES_TEXTURE = NyctoNeoForge.id("textures/entity/vampire/vampire_eyes.png");

	public VampireRenderer(EntityRendererProvider.Context context) {
		super(context, new VampireModel(context.bakeLayer(VampireModel.LAYER)), 0.5F);
		addLayer(new VampireEyesLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Vampire entity) {
		return TEXTURE;
	}

	private static final class VampireEyesLayer extends EyesLayer<Vampire, VampireModel> {
		private VampireEyesLayer(RenderLayerParent<Vampire, VampireModel> renderer) {
			super(renderer);
		}

		@Override
		public RenderType renderType() {
			return RenderType.eyes(EYES_TEXTURE);
		}
	}
}
