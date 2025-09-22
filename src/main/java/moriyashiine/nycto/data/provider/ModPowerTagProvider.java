/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModPowerTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<Power> {
	public ModPowerTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, NyctoRegistries.POWER_KEY, registriesFuture, power -> RegistryKey.of(NyctoRegistries.POWER_KEY, NyctoRegistries.POWER.getId(power)));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModPowerTags.VAMPIRE_CHOOSABLE)
				.add(ModPowers.BAT_FORM)
				.add(ModPowers.BAT_SWARM)
				.add(ModPowers.BATSTEP)
				.add(ModPowers.BLOOD_BARRIER)
				.add(ModPowers.BLOOD_FLECHETTES)
				.add(ModPowers.BLOODRUSH)
				.add(ModPowers.CARNAGE)
				.add(ModPowers.DARK_FORM)
				.add(ModPowers.HAEMOGENESIS)
				.add(ModPowers.HYPNOTIZE)
				.add(ModPowers.KEEN_SENSES)
				.add(ModPowers.MIST_FORM)
				.add(ModPowers.VAMPIRIC_THRALL)
				.add(ModPowers.HYDROPHOBIA)
				.add(ModPowers.PHOTOPHOBIA)
				.add(ModPowers.PYROPHOBIA)
				.add(ModPowers.RICH_TASTES)
				.add(ModPowers.THIN_BLOOD)
				.add(ModPowers.VILE_PRESENCE);
	}
}
