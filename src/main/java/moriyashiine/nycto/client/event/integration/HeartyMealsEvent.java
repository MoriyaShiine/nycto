/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.integration;

import moriyashiine.heartymeals.api.event.DisableHudRepositioningEvent;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.player.Player;

public class HeartyMealsEvent implements DisableHudRepositioningEvent {
	@Override
	public boolean shouldDisableRepositioning(Player player) {
		return NyctoAPI.isVampire(player);
	}
}
