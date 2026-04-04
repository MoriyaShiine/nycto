/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.vampiricthrall;

import moriyashiine.nycto.api.renderer.entity.vampiricthrall.VampiricThrallRenderer;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Vex;

public class VexVampiricThrallRenderer implements VampiricThrallRenderer<Vex> {
	private static final Identifier THRALLED_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex.png");
	private static final Identifier THRALLED_CHARGING_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex_charging.png");

	@Override
	public Identifier getCustomTexture(Vex entity) {
		return entity.isCharging() ? THRALLED_CHARGING_VEX_TEXTURE : THRALLED_VEX_TEXTURE;
	}
}
