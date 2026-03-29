/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
	public static final TagKey<Biome> GENERATES_GARLIC = TagKey.create(Registries.BIOME, Nycto.id("generates_garlic"));
	public static final TagKey<Biome> GENERATES_ACONITE = TagKey.create(Registries.BIOME, Nycto.id("generates_aconite"));

	public static final TagKey<Biome> SPAWNS_VAMPIRES = TagKey.create(Registries.BIOME, Nycto.id("spawns_vampires"));
}
