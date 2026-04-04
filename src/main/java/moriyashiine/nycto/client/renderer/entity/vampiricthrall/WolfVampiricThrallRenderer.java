/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.vampiricthrall;

import moriyashiine.nycto.api.renderer.entity.vampiricthrall.VampiricThrallRenderer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;

public class WolfVampiricThrallRenderer implements VampiricThrallRenderer<Wolf> {
	private static final Identifier THRALLED_DARK_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_dark.png");
	private static final Identifier THRALLED_DARK_WOLF_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_dark_baby.png");
	private static final Identifier THRALLED_LIGHT_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_light.png");
	private static final Identifier THRALLED_LIGHT_WOLF_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_light_baby.png");

	@Override
	public Identifier getCustomTexture(Wolf entity) {
		Holder<WolfVariant> variant = entity.getVariant();
		if (variant.is(WolfVariants.BLACK) || variant.is(WolfVariants.RUSTY) || variant.is(WolfVariants.SPOTTED) || variant.is(WolfVariants.WOODS)) {
			return entity.isBaby() ? THRALLED_DARK_WOLF_BABY_TEXTURE : THRALLED_DARK_WOLF_TEXTURE;
		}
		return entity.isBaby() ? THRALLED_LIGHT_WOLF_BABY_TEXTURE : THRALLED_LIGHT_WOLF_TEXTURE;
	}
}
