/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.tag.ModBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {
	public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BIOME, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
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
