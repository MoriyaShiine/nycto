/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.world.entity.projectile.arrow.WoodenStake;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class WoodenStakeRenderer extends ArrowRenderer<WoodenStake, ArrowRenderState> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/projectiles/wooden_stake.png");

	public WoodenStakeRenderer(EntityRendererProvider.Context context) {
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
