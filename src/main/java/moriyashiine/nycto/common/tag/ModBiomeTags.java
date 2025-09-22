/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
	public static final TagKey<Biome> GENERATES_GARLIC = TagKey.of(RegistryKeys.BIOME, Nycto.id("generates_garlic"));
	public static final TagKey<Biome> GENERATES_ACONITE = TagKey.of(RegistryKeys.BIOME, Nycto.id("generates_aconite"));

	public static final TagKey<Biome> SPAWNS_VAMPIRES = TagKey.of(RegistryKeys.BIOME, Nycto.id("spawns_vampires"));
}
