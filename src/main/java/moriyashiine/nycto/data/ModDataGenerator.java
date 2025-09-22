/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data;

import moriyashiine.nycto.common.init.ModWorldGeneration;
import moriyashiine.nycto.data.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModBiomeTagProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		FabricTagProvider.BlockTagProvider blockTagProvider = pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModDamageTypeTagProvider::new);
		pack.addProvider(ModDynamicRegistryProvider::new);
		pack.addProvider(ModEnchantmentTagProvider::new);
		pack.addProvider(ModEntityLootTableProvider::new);
		pack.addProvider(ModEntityTypeTagProvider::new);
		pack.addProvider((output, registriesFuture) -> new ModItemTagProvider(output, registriesFuture, blockTagProvider));
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModPowerTagProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModSoundEventTagProvider::new);
		pack.addProvider(ModSoundsProvider::new);
		pack.addProvider(ModStatusEffectTagProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModWorldGeneration::bootstrapConfigured);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModWorldGeneration::bootstrapPlaced);
	}
}
