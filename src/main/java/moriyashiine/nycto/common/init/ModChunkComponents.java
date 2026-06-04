/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.NyctoChunkComponentKey;
import moriyashiine.nycto.common.component.chunk.NaturalOresComponent;

public final class ModChunkComponents {
	public static final NyctoChunkComponentKey<NaturalOresComponent> NATURAL_ORES = new NyctoChunkComponentKey<>(Nycto.id("natural_ores"), NaturalOresComponent.class, NaturalOresComponent::new);

	private ModChunkComponents() {
	}

	public static void init() {
	}

	public static Iterable<NyctoChunkComponentKey<?>> components() {
		return java.util.List.of(NATURAL_ORES);
	}
}
