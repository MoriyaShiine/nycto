/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BloodrushClientState {
	private static final Map<Integer, Integer> BLOODRUSH_UNTIL = new ConcurrentHashMap<>();

	private BloodrushClientState() {
	}

	public static void setActive(Entity entity, int ticks) {
		if (ticks <= 0) {
			BLOODRUSH_UNTIL.remove(entity.getId());
		} else {
			BLOODRUSH_UNTIL.put(entity.getId(), entity.tickCount + ticks);
		}
	}

	public static boolean isActive(Entity entity) {
		Integer until = BLOODRUSH_UNTIL.get(entity.getId());
		if (until == null) {
			return false;
		}
		if (entity.tickCount > until) {
			BLOODRUSH_UNTIL.remove(entity.getId());
			return false;
		}
		return true;
	}
}
