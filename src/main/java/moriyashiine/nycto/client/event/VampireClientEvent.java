/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.ReplaceContextualInfoEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class VampireClientEvent implements ReplaceContextualInfoEvent {
	private static final Identifier BACKGROUND_TEXTURE = Nycto.id("hud/vampire_charge_jump/background");
	private static final Identifier PROGRESS_TEXTURE = Nycto.id("hud/vampire_charge_jump/progress");

	@Override
	public ContextualInfo getInfo(Player player) {
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		if (vampireChargeJumpComponent.isEnabled()) {
			float boostProgress = vampireChargeJumpComponent.getBoostProgress();
			if (boostProgress > 0) {
				return new ContextualInfo(BACKGROUND_TEXTURE, PROGRESS_TEXTURE, boostProgress);
			}
		}
		return null;
	}

	@Override
	public int getPriority() {
		return 900;
	}
}
