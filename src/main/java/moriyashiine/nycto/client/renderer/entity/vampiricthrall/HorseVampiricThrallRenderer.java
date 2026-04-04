/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.vampiricthrall;

import moriyashiine.nycto.api.renderer.entity.vampiricthrall.VampiricThrallRenderer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.equine.Horse;

public class HorseVampiricThrallRenderer implements VampiricThrallRenderer<Horse> {
	private static final Identifier THRALLED_DARK_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_dark.png");
	private static final Identifier THRALLED_DARK_HORSE_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_dark_baby.png");
	private static final Identifier THRALLED_LIGHT_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_light.png");
	private static final Identifier THRALLED_LIGHT_HORSE_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_light_baby.png");

	@Override
	public Identifier getCustomTexture(Horse entity) {
		return entity.getVariant().ordinal() > 2 ? entity.isBaby() ? THRALLED_DARK_HORSE_BABY_TEXTURE : THRALLED_DARK_HORSE_TEXTURE : entity.isBaby() ? THRALLED_LIGHT_HORSE_BABY_TEXTURE : THRALLED_LIGHT_HORSE_TEXTURE;
	}
}
