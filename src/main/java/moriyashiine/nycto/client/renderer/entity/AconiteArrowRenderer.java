/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.world.entity.projectile.arrow.AconiteArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class AconiteArrowRenderer extends ArrowRenderer<AconiteArrow, ArrowRenderState> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/projectiles/aconite_arrow.png");

	public AconiteArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	protected Identifier getTextureLocation(ArrowRenderState state) {
		return TEXTURE;
	}
}
