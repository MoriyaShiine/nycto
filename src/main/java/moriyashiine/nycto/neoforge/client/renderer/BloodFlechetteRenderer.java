/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.entity.projectile.BloodFlechetteProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public final class BloodFlechetteRenderer extends ArrowRenderer<BloodFlechetteProjectile> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/projectiles/blood_flechette.png");

	public BloodFlechetteRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(BloodFlechetteProjectile entity) {
		return TEXTURE;
	}
}
