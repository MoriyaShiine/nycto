/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModSoundEventTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;

import java.util.concurrent.CompletableFuture;

public class ModSoundEventTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<SoundEvent> {
	public ModSoundEventTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.SOUND_EVENT, registriesFuture, soundEvent -> RegistryKey.of(RegistryKeys.SOUND_EVENT, soundEvent.id()));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg) {
		builder(ModSoundEventTags.NOT_MUFFLED)
				.addTag(ModSoundEventTags.POWER);
		valueLookupBuilder(ModSoundEventTags.POWER)
				.add(ModSoundEvents.POWER_NIGHT_VISION_ON).add(ModSoundEvents.POWER_NIGHT_VISION_OFF)
				.add(ModSoundEvents.POWER_BAT_FORM_ON).add(ModSoundEvents.POWER_BAT_FORM_OFF)
				.add(ModSoundEvents.POWER_BAT_SWARM_USE)
				.add(ModSoundEvents.POWER_BATSTEP_USE)
				.add(ModSoundEvents.POWER_BLOOD_BARRIER_USE).add(ModSoundEvents.POWER_BLOOD_BARRIER_HIT).add(ModSoundEvents.POWER_BLOOD_BARRIER_BREAK)
				.add(ModSoundEvents.POWER_BLOOD_FLECHETTES_USE).add(ModSoundEvents.POWER_BLOOD_FLECHETTES_LIFE_DRAIN)
				.add(ModSoundEvents.POWER_BLOODRUSH_USE)
				.add(ModSoundEvents.POWER_CARNAGE_USE).add(ModSoundEvents.POWER_CARNAGE_HIT)
				.add(ModSoundEvents.POWER_DARK_FORM_ON).add(ModSoundEvents.POWER_DARK_FORM_OFF)
				.add(ModSoundEvents.POWER_HAEMOGENESIS_USE)
				.add(ModSoundEvents.POWER_HYPNOTIZE_USE).add(ModSoundEvents.POWER_HYPNOTIZE_USE_INVERSE)
				.add(ModSoundEvents.POWER_KEEN_SENSES_ON).add(ModSoundEvents.POWER_KEEN_SENSES_OFF).add(ModSoundEvents.POWER_KEEN_SENSES_HEARTBEAT)
				.add(ModSoundEvents.POWER_MIST_FORM_ON).add(ModSoundEvents.POWER_MIST_FORM_OFF)
				.add(ModSoundEvents.POWER_VAMPIRIC_THRALL_CONVERT);
	}
}
