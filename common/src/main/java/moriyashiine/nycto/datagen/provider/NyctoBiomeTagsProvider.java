package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.tag.NyctoBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class NyctoBiomeTagsProvider extends FabricTagsProvider<Biome> {
	public NyctoBiomeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BIOME, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoBiomeTags.GENERATES_GARLIC)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_SWAMP);
		builder(NyctoBiomeTags.GENERATES_ACONITE)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_TAIGA);

		builder(NyctoBiomeTags.SPAWNS_VAMPIRES)
				.forceAddTag(ConventionalBiomeTags.IS_FOREST)
				.forceAddTag(ConventionalBiomeTags.IS_TAIGA);
	}
}
