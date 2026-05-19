/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider.integration.anthropophagy;

import moriyashiine.anthropophagy.api.datagen.FleshDropsProvider;
import moriyashiine.anthropophagy.common.references.AnthropophagyItemIds;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.references.NyctoEntityTypeIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class NyctoFleshDropsProvider extends FleshDropsProvider {
	public NyctoFleshDropsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(Output output) {
		output.accept(NyctoEntityTypeIds.HUNTER, AnthropophagyItemIds.FLESH, AnthropophagyItemIds.COOKED_FLESH);
		output.accept(NyctoEntityTypeIds.VAMPIRE, AnthropophagyItemIds.CORRUPT_FLESH);
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_flesh_drops";
	}
}
