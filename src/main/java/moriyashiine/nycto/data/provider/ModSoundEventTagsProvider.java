/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModSoundEventTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

import java.util.concurrent.CompletableFuture;

public class ModSoundEventTagsProvider extends FabricTagsProvider.FabricIntrinsicHolderTagsProvider<SoundEvent> {
	public ModSoundEventTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.SOUND_EVENT, registriesFuture, sound -> ResourceKey.create(Registries.SOUND_EVENT, sound.location()));
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
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
