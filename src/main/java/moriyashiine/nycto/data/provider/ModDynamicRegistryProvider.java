/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	public ModDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
		add(entries, RegistryKeys.CONFIGURED_FEATURE);
		add(entries, RegistryKeys.PLACED_FEATURE);
		add(entries, RegistryKeys.TIMELINE);
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_dynamic";
	}

	private <T> void add(Entries entries, RegistryKey<Registry<T>> key) {
		entries.addAll((RegistryWrapper.Impl<T>) entries.getLookup(key));
	}
}
