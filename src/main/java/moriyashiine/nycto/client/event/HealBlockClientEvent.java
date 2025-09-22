/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import net.minecraft.entity.player.PlayerEntity;

public class HealBlockClientEvent implements ReplaceHeartTexturesEvent {
	private static final ReplaceHeartTexturesEvent.TextureSet TEXTURES = new TextureSet(
			Nycto.id("hud/heal_block/full"), Nycto.id("hud/heal_block/full_blinking"),
			Nycto.id("hud/heal_block/half"), Nycto.id("hud/heal_block/half_blinking"),
			Nycto.id("hud/heal_block/hardcore_full"), Nycto.id("hud/heal_block/hardcore_full_blinking"),
			Nycto.id("hud/heal_block/hardcore_half"), Nycto.id("hud/heal_block/hardcore_half_blinking")
	);

	@Override
	public TextureSet getTextureSet(PlayerEntity player) {
		if (ModEntityComponents.HEAL_BLOCK.get(player).isHealingBlocked()) {
			return TEXTURES;
		}
		return null;
	}
}
