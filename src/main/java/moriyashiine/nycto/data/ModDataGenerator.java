/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data;

import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.data.provider.*;
import moriyashiine.nycto.data.provider.integration.anthropophagy.NyctoFleshDropsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModAdvancementProvider::new);
		pack.addProvider(ModBannerPatternTagsProvider::new);
		pack.addProvider(ModBiomeTagsProvider::new);
		pack.addProvider(ModBlockLootSubProvider::new);
		FabricTagsProvider.BlockTagsProvider blockTagProvider = pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModDamageTypeTagsProvider::new);
		pack.addProvider(ModDynamicRegistryProvider::new);
		pack.addProvider(ModEnchantmentTagsProvider::new);
		pack.addProvider(ModEntityLootSubProvider::new);
		pack.addProvider(ModEntityTypeTagsProvider::new);
		pack.addProvider((output, registriesFuture) -> new ModItemTagsProvider(output, registriesFuture, blockTagProvider));
		pack.addProvider(ModMobEffectTagsProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModPowerTagsProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModSoundEventTagsProvider::new);
		pack.addProvider(ModSoundsProvider::new);
		pack.addProvider(ModTimelineTagsProvider::new);
		pack.addProvider(ModVillagerTradesTagsProvider::new);

		pack.addProvider(NyctoFleshDropsProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModWorldGeneration::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, ModWorldGeneration::bootstrapPlaced);
		registryBuilder.add(Registries.BANNER_PATTERN, ModBannerPatterns::bootstrap);
		registryBuilder.add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap);
		registryBuilder.add(Registries.TIMELINE, ModTimelines::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap);
	}
}
