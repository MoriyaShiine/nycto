/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.projectile.AconiteArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;

public class AconiteArrowEntityRenderer extends ProjectileEntityRenderer<AconiteArrowEntity, ProjectileEntityRenderState> {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/projectiles/aconite_arrow.png");

	public AconiteArrowEntityRenderer(EntityRendererFactory.Context context) {
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
