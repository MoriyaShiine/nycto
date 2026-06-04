/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.entity.projectile.WoodenStakeProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public final class WoodenStakeRenderer extends ArrowRenderer<WoodenStakeProjectile> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/projectiles/wooden_stake.png");

	public WoodenStakeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(WoodenStakeProjectile entity) {
		return TEXTURE;
	}
}
