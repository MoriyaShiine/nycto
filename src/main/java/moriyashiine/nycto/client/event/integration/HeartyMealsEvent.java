/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.integration;

import moriyashiine.heartymeals.api.event.DisableHudRepositioningEvent;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.entity.player.PlayerEntity;

public class HeartyMealsEvent implements DisableHudRepositioningEvent {
	@Override
	public boolean shouldDisableRepositioning(PlayerEntity player) {
		return NyctoAPI.isVampire(player);
	}
}
