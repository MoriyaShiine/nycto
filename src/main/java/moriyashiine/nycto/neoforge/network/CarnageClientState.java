/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CarnageClientState {
	private static final int MAX_TICKS = 300;
	private static final Map<Integer, Integer> CARNAGE_UNTIL = new ConcurrentHashMap<>();

	private CarnageClientState() {
	}

	public static void setActive(Entity entity, int ticks) {
		if (ticks <= 0) {
			CARNAGE_UNTIL.remove(entity.getId());
		} else {
			CARNAGE_UNTIL.put(entity.getId(), entity.tickCount + ticks);
		}
	}

	public static boolean isActive(Entity entity) {
		return opacity(entity, 1.0F) > 0;
	}

	public static float opacity(Entity entity, float maxOpacity) {
		Integer until = CARNAGE_UNTIL.get(entity.getId());
		if (until == null) {
			return 0;
		}
		int ticks = until - entity.tickCount;
		if (ticks <= 0) {
			CARNAGE_UNTIL.remove(entity.getId());
			return 0;
		}
		int inverseTicks = MAX_TICKS - ticks;
		if (ticks < 20) {
			return Mth.lerp(ticks / 20F, 0, maxOpacity);
		}
		if (inverseTicks < 20) {
			return Mth.lerp(inverseTicks / 20F, 0, maxOpacity);
		}
		return maxOpacity;
	}
}
