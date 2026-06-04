/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DarkFormClientState {
	private static final Map<Integer, Integer> ACTIVE_UNTIL = new ConcurrentHashMap<>();
	private static final Map<Integer, Integer> JUMP_UNTIL = new ConcurrentHashMap<>();

	private DarkFormClientState() {
	}

	public static void setActive(Entity entity, int ticks) {
		setActive(entity, ticks, 0);
	}

	public static void setActive(Entity entity, int ticks, int jumpTicks) {
		if (ticks <= 0) {
			ACTIVE_UNTIL.remove(entity.getId());
			JUMP_UNTIL.remove(entity.getId());
		} else {
			ACTIVE_UNTIL.put(entity.getId(), entity.tickCount + ticks);
			if (jumpTicks > 0) {
				JUMP_UNTIL.put(entity.getId(), entity.tickCount + jumpTicks);
			} else {
				JUMP_UNTIL.remove(entity.getId());
			}
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

	public static boolean isJumping(Entity entity) {
		if (!isActive(entity)) {
			return false;
		}
		Integer until = JUMP_UNTIL.get(entity.getId());
		if (until == null) {
			return false;
		}
		if (until <= entity.tickCount) {
			JUMP_UNTIL.remove(entity.getId());
			return false;
		}
		return true;
	}
}
