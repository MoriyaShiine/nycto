package moriyashiine.nycto.datagen;

import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.datagen.provider.*;
import moriyashiine.nycto.datagen.provider.integration.anthropophagy.NyctoFleshDropsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class NyctoDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(NyctoAdvancementProvider::new);
		pack.addProvider(NyctoBannerPatternTagsProvider::new);
		pack.addProvider(NyctoBiomeTagsProvider::new);
		pack.addProvider(NyctoBlockLootSubProvider::new);
		FabricTagsProvider.BlockTagsProvider blockTagsProvider = pack.addProvider(NyctoBlockTagsProvider::new);
		pack.addProvider(NyctoDamageTypeTagsProvider::new);
		pack.addProvider(NyctoDynamicRegistryProvider::new);
		pack.addProvider(NyctoEnchantmentTagsProvider::new);
		pack.addProvider(NyctoEntityLootSubProvider::new);
		pack.addProvider(NyctoEntityTypeTagsProvider::new);
		pack.addProvider(NyctoEquipmentAssetProvider::new);
		pack.addProvider((output, registriesFuture) -> new NyctoItemTagsProvider(output, registriesFuture, blockTagsProvider));
		pack.addProvider(NyctoMobEffectTagsProvider::new);
		pack.addProvider(NyctoModelProvider::new);
		pack.addProvider(NyctoPowerTagsProvider::new);
		pack.addProvider(NyctoRecipeProvider::new);
		pack.addProvider(NyctoSoundEventTagsProvider::new);
		pack.addProvider(NyctoSoundsProvider::new);
		pack.addProvider(NyctoTimelineTagsProvider::new);
		pack.addProvider(NyctoVillagerTradesTagsProvider::new);

		pack.addProvider(NyctoFleshDropsProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, NyctoWorldGeneration::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, NyctoWorldGeneration::bootstrapPlaced);
		registryBuilder.add(Registries.BANNER_PATTERN, NyctoBannerPatterns::bootstrap);
		registryBuilder.add(Registries.DAMAGE_TYPE, NyctoDamageTypes::bootstrap);
		registryBuilder.add(Registries.TIMELINE, NyctoTimelines::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, NyctoVillagerTrades::bootstrap);
	}
}
