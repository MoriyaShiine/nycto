/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.projectile.WoodenStakeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;

public class WoodenStakeEntityRenderer extends ProjectileEntityRenderer<WoodenStakeEntity, ProjectileEntityRenderState> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/projectiles/wooden_stake.png");

	public WoodenStakeEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public ProjectileEntityRenderState createRenderState() {
		return new ProjectileEntityRenderState();
	}

	@Override
	protected Identifier getTexture(ProjectileEntityRenderState state) {
		return TEXTURE;
	}
}
