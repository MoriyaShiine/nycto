/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import moriyashiine.nycto.common.init.ModChunkComponents;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModLevelComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

public final class NyctoComponentTicker {
	private NyctoComponentTicker() {
	}

	public static void serverTick(Entity entity) {
		for (NyctoEntityComponentKey<?> key : ModEntityComponents.components()) {
			Object component = key.getNullable(entity);
			if (component instanceof NyctoCommonTickingComponent ticking) {
				ticking.serverTick();
			} else if (component instanceof NyctoServerTickingComponent ticking) {
				ticking.serverTick();
			}
		}
	}

	public static void clientTick(Entity entity) {
		for (NyctoEntityComponentKey<?> key : ModEntityComponents.components()) {
			Object component = key.getNullable(entity);
			if (component instanceof NyctoCommonTickingComponent ticking) {
				ticking.clientTick();
			} else if (component instanceof NyctoClientTickingComponent ticking) {
				ticking.clientTick();
			}
		}
	}

	public static void serverTick(Level level) {
		for (NyctoLevelComponentKey<?> key : ModLevelComponents.components()) {
			Object component = key.get(level);
			if (component instanceof NyctoCommonTickingComponent ticking) {
				ticking.serverTick();
			} else if (component instanceof NyctoServerTickingComponent ticking) {
				ticking.serverTick();
			}
		}
	}

	public static void clientTick(Level level) {
		for (NyctoLevelComponentKey<?> key : ModLevelComponents.components()) {
			Object component = key.get(level);
			if (component instanceof NyctoCommonTickingComponent ticking) {
				ticking.clientTick();
			} else if (component instanceof NyctoClientTickingComponent ticking) {
				ticking.clientTick();
			}
		}
	}

	public static void serverTick(ChunkAccess chunk) {
		for (NyctoChunkComponentKey<?> key : ModChunkComponents.components()) {
			Object component = key.get(chunk);
			if (component instanceof NyctoCommonTickingComponent ticking) {
				ticking.serverTick();
			} else if (component instanceof NyctoServerTickingComponent ticking) {
				ticking.serverTick();
			}
		}
	}
}
