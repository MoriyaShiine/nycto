/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.power;

import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import net.minecraft.server.level.ServerPlayer;

public interface NyctoPower {
	String name();

	int bloodCost();

	default int bloodCost(ServerPlayer player) {
		return VampireArmorItem.adjustPowerCost(player, bloodCost());
	}

	int cooldownTicks();

	default int cooldownTicks(ServerPlayer player) {
		return cooldownTicks();
	}

	default boolean canUse(ServerPlayer player) {
		return true;
	}

	boolean use(ServerPlayer player);

	default void tick(ServerPlayer player) {
	}
}
