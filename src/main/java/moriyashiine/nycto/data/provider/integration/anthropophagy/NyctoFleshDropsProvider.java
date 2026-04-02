/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider.integration.anthropophagy;

import moriyashiine.anthropophagy.common.init.ModItems;
import moriyashiine.anthropophagy.data.provider.ModFleshDropsProvider;
import moriyashiine.nycto.common.init.ModEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class NyctoFleshDropsProvider extends ModFleshDropsProvider {
	public NyctoFleshDropsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(Output output) {
		output.accept(ModEntityTypes.HUNTER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(ModEntityTypes.VAMPIRE, ModItems.CORRUPT_FLESH);
	}
}
