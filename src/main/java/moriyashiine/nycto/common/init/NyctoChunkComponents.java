/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.chunk.NaturalOresComponent;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;

public class NyctoChunkComponents implements ChunkComponentInitializer {
	public static final ComponentKey<NaturalOresComponent> NATURAL_ORES = ComponentRegistry.getOrCreate(Nycto.id("natural_ores"), NaturalOresComponent.class);

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(NATURAL_ORES, NaturalOresComponent::new);
	}
}
