/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.power;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public interface FormChanger {
	boolean isFormActive(PlayerEntity player);

	default void disable(ServerWorld world, ServerPlayerEntity player) {
		if (this instanceof ActivePower activePower) {
			activePower.playUseSound(player);
			NyctoAPI.setPowerCooldown(player, activePower, activePower.shouldApplyCooldown(player) ? activePower.getCooldown() : 1);
			activePower.use(world, player);
		}
	}
}
