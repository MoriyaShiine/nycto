/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BatFormClientState {
	private static final Map<Integer, Integer> ACTIVE_UNTIL = new ConcurrentHashMap<>();

	private BatFormClientState() {
	}

	public static void setActive(Entity entity, int ticks) {
		if (ticks <= 0) {
			ACTIVE_UNTIL.remove(entity.getId());
		} else {
			ACTIVE_UNTIL.put(entity.getId(), entity.tickCount + ticks);
		}
	}

	public static boolean isActive(Entity entity) {
		Integer until = ACTIVE_UNTIL.get(entity.getId());
		if (until == null) {
			return false;
		}
		if (until <= entity.tickCount) {
			ACTIVE_UNTIL.remove(entity.getId());
			return false;
		}
		return true;
	}
}
