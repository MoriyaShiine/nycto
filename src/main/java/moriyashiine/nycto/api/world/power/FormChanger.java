/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.power;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface FormChanger {
	boolean isFormActive(Player player);

	default void disable(ServerLevel level, ServerPlayer player) {
		if (this instanceof ActivePower activePower) {
			activePower.playUseSound(player);
			NyctoAPI.setPowerCooldown(player, activePower, activePower.shouldApplyCooldown(player) ? activePower.getCooldown() : 1);
			activePower.use(level, player);
		}
	}
}
