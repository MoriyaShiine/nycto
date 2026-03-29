/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.tag.ModBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends FabricTagsProvider<Biome> {
	public ModBiomeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BIOME, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(ModBiomeTags.GENERATES_GARLIC)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_SWAMP);
		builder(ModBiomeTags.GENERATES_ACONITE)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_TAIGA);

		builder(ModBiomeTags.SPAWNS_VAMPIRES)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_TAIGA);
	}
}
