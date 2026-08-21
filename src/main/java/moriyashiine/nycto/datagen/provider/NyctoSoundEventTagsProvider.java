package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.tag.NyctoSoundEventTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

public class NyctoSoundEventTagsProvider extends FabricTagsProvider<SoundEvent> {
	public NyctoSoundEventTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.SOUND_EVENT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoSoundEventTags.NOT_MUFFLED)
				.addTag(NyctoSoundEventTags.POWER);
		builder(NyctoSoundEventTags.POWER)
				.addTag(NyctoSoundEventTags.VAMPIRE_POWER);
		add(NyctoSoundEventTags.VAMPIRE_POWER,
				NyctoSoundEvents.NIGHT_VISION_ON, NyctoSoundEvents.NIGHT_VISION_OFF,
				NyctoSoundEvents.BAT_FORM_ON, NyctoSoundEvents.BAT_FORM_OFF,
				NyctoSoundEvents.BAT_SWARM_USE,
				NyctoSoundEvents.BATSTEP_USE,
				NyctoSoundEvents.BLOOD_BARRIER_USE, NyctoSoundEvents.BLOOD_BARRIER_HIT, NyctoSoundEvents.BLOOD_BARRIER_BREAK,
				NyctoSoundEvents.BLOOD_FLECHETTES_USE, NyctoSoundEvents.BLOOD_FLECHETTES_LIFE_DRAIN,
				NyctoSoundEvents.BLOODRUSH_USE,
				NyctoSoundEvents.CARNAGE_USE, NyctoSoundEvents.CARNAGE_HIT,
				NyctoSoundEvents.DARK_FORM_ON, NyctoSoundEvents.DARK_FORM_OFF,
				NyctoSoundEvents.HAEMOGENESIS_USE,
				NyctoSoundEvents.HYPNOTIZE_USE, NyctoSoundEvents.HYPNOTIZE_USE_INVERSE,
				NyctoSoundEvents.KEEN_SENSES_ON, NyctoSoundEvents.KEEN_SENSES_OFF, NyctoSoundEvents.KEEN_SENSES_HEARTBEAT,
				NyctoSoundEvents.MIST_FORM_ON, NyctoSoundEvents.MIST_FORM_OFF,
				NyctoSoundEvents.VAMPIRIC_THRALL_CONVERT);
	}

	private void add(TagKey<SoundEvent> tagKey, SoundEvent... sounds) {
		TagAppender<SoundEvent> builder = builder(tagKey);
		for (SoundEvent soundEvent : sounds) {
			builder.add(key(soundEvent));
		}
	}

	private static ResourceKey<SoundEvent> key(SoundEvent sound) {
		return ResourceKey.create(Registries.SOUND_EVENT, sound.location());
	}
}
