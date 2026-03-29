/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.DisableContextualInfoEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.player.Player;

public class VampireClientEvent implements DisableContextualInfoEvent {
	@Override
	public TriState shouldDisable(Player player) {
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		if (vampireChargeJumpComponent.isEnabled() && vampireChargeJumpComponent.getBoostProgress() > 0) {
			return TriState.TRUE;
		}
		return TriState.DEFAULT;
	}
}
