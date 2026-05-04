/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider.integration.anthropophagy;

import moriyashiine.anthropophagy.api.datagen.FleshDropsProvider;
import moriyashiine.anthropophagy.common.init.ModItems;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class NyctoFleshDropsProvider extends FleshDropsProvider {
	public NyctoFleshDropsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(Output output) {
		output.accept(ModEntityTypes.HUNTER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(ModEntityTypes.VAMPIRE, ModItems.CORRUPT_FLESH);
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_flesh_drops";
	}
}
