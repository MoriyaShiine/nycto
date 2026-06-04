/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.entity.projectile.AconiteArrowProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public final class AconiteArrowRenderer extends ArrowRenderer<AconiteArrowProjectile> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/projectiles/aconite_arrow.png");

	public AconiteArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(AconiteArrowProjectile entity) {
		return TEXTURE;
	}
}
