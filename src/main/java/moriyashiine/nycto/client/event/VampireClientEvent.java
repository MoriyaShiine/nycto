/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.DisableHudBarEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.player.PlayerEntity;

public class VampireClientEvent implements DisableHudBarEvent {
	@Override
	public TriState shouldDisableHudBar(PlayerEntity player) {
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		if (vampireChargeJumpComponent.isEnabled() && vampireChargeJumpComponent.getBoostProgress() > 0) {
			return TriState.TRUE;
		}
		return TriState.DEFAULT;
	}
}
