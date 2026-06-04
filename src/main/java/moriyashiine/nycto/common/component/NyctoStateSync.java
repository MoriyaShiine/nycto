/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

public final class NyctoStateSync {
	private NyctoStateSync() {
	}

	public static void syncEntity(Entity entity, NyctoEntityComponentKey<?> key) {
		// NeoForge attachment sync is packet-driven; network workers can fan out from this hook.
	}

	public static void syncLevel(Level level, NyctoLevelComponentKey<?> key) {
	}

	public static void syncChunk(ChunkAccess chunk, NyctoChunkComponentKey<?> key) {
		chunk.setUnsaved(true);
	}
}
