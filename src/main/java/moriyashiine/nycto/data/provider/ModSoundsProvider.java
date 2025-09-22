/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;

import java.util.concurrent.CompletableFuture;

import static moriyashiine.nycto.common.Nycto.id;
import static net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.EntryBuilder.ofEvent;
import static net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.EntryBuilder.ofFile;
import static net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.of;

public class ModSoundsProvider extends FabricSoundsProvider {
	public ModSoundsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registryLookup, SoundExporter exporter) {
		exporter.add(ModSoundEvents.BLOCK_ALTAR_USE, of().subtitle("subtitles.nycto.block.altar.use")
				.sound(ofFile(id("block/altar/use"))));

		exporter.add(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK, of().subtitle("subtitles.item.honey_bottle.drink")
				.sound(ofEvent(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)));
		exporter.add(ModSoundEvents.ITEM_VAMPIRIC_DAGGER_EXTRACT_FAIL, of().subtitle("subtitles.nycto.item.vampiric_dagger.extract_fail")
				.sound(ofEvent(SoundEvents.ITEM_BUNDLE_INSERT_FAIL)));

		exporter.add(ModSoundEvents.ENTITY_GENERIC_REMOVE_POWER, of().subtitle("subtitles.nycto.entity.generic.remove_power")
				.sound(ofFile(id("entity/remove_power"))));
		exporter.add(ModSoundEvents.ENTITY_GENERIC_TRANSFORM_HUMAN, of().subtitle("subtitles.nycto.entity.generic.transform")
				.sound(ofFile(id("entity/transform_human"))));
		exporter.add(ModSoundEvents.ENTITY_GENERIC_TRANSFORM_VAMPIRE, of().subtitle("subtitles.nycto.entity.generic.transform")
				.sound(ofFile(id("entity/transform_vampire"))));
		exporter.add(ModSoundEvents.ENTITY_GENERIC_BLOOD_DRAIN_BLOCKED, of().subtitle("subtitles.nycto.entity.generic.blood_drain_blocked")
				.sound(ofFile(id("entity/blood_drain_blocked"))));
		exporter.add(ModSoundEvents.ENTITY_GENERIC_SIZZLE, of().subtitle("subtitles.nycto.entity.generic.sizzle")
				.sound(ofEvent(SoundEvents.BLOCK_CANDLE_EXTINGUISH)));

		exporter.add(ModSoundEvents.ENTITY_VAMPIRE_AMBIENT, of().subtitle("subtitles.nycto.entity.vampire.ambient")
				.sound(ofEvent(SoundEvents.ENTITY_VINDICATOR_AMBIENT)));
		exporter.add(ModSoundEvents.ENTITY_VAMPIRE_HURT, of().subtitle("subtitles.nycto.entity.vampire.hurt")
				.sound(ofEvent(SoundEvents.ENTITY_VINDICATOR_HURT)));
		exporter.add(ModSoundEvents.ENTITY_VAMPIRE_DEATH, of().subtitle("subtitles.nycto.entity.vampire.death")
				.sound(ofEvent(SoundEvents.ENTITY_VINDICATOR_DEATH)));

		exporter.add(ModSoundEvents.ENTITY_HUNTER_AMBIENT, of().subtitle("subtitles.nycto.entity.hunter.ambient")
				.sound(ofEvent(SoundEvents.ENTITY_VILLAGER_AMBIENT)));
		exporter.add(ModSoundEvents.ENTITY_HUNTER_HURT, of().subtitle("subtitles.nycto.entity.hunter.hurt")
				.sound(ofEvent(SoundEvents.ENTITY_VILLAGER_HURT)));
		exporter.add(ModSoundEvents.ENTITY_HUNTER_DEATH, of().subtitle("subtitles.nycto.entity.hunter.death")
				.sound(ofEvent(SoundEvents.ENTITY_VILLAGER_DEATH)));

		exporter.add(ModSoundEvents.ENTITY_DARK_FORM_FLAP, of().subtitle("subtitles.nycto.entity.dark_form.flap")
				.sound(ofEvent(SoundEvents.ENTITY_PHANTOM_FLAP)));

		exporter.add(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_BLOCK, of().subtitle("subtitles.nycto.entity.blood_flechette.impact")
				.sound(ofFile(id("entity/blood_flechette/hit_block")).volume(0.5F)));
		exporter.add(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_ENTITY, of().subtitle("subtitles.nycto.entity.blood_flechette.impact")
				.sound(ofFile(id("entity/blood_flechette/hit_block")).volume(0.15F)));

		exporter.add(ModSoundEvents.ENTITY_FIREBOMB_IMPACT, of().subtitle("subtitles.nycto.entity.firebomb.impact")
				.sound(ofEvent(SoundEvents.BLOCK_FIRE_EXTINGUISH)));

		exporter.add(ModSoundEvents.POWER_NIGHT_VISION_ON, of()
				.sound(ofFile(id("power/night_vision/on"))));
		exporter.add(ModSoundEvents.POWER_NIGHT_VISION_OFF, of()
				.sound(ofFile(id("power/night_vision/off"))));

		exporter.add(ModSoundEvents.POWER_BAT_FORM_ON, of()
				.sound(ofFile(id("power/bat_form/on"))));
		exporter.add(ModSoundEvents.POWER_BAT_FORM_OFF, of()
				.sound(ofFile(id("power/vampire_form_change_off"))));

		exporter.add(ModSoundEvents.POWER_BAT_SWARM_USE, of()
				.sound(ofFile(id("power/bat_swarm/use"))));

		exporter.add(ModSoundEvents.POWER_BATSTEP_USE, of()
				.sound(ofFile(id("power/batstep/use"))));

		exporter.add(ModSoundEvents.POWER_BLOOD_BARRIER_USE, of()
				.sound(ofFile(id("power/blood_barrier/use"))));
		exporter.add(ModSoundEvents.POWER_BLOOD_BARRIER_HIT, of()
				.sound(ofFile(id("power/blood_barrier/hit"))));
		exporter.add(ModSoundEvents.POWER_BLOOD_BARRIER_BREAK, of()
				.sound(ofFile(id("power/blood_barrier/break"))));

		exporter.add(ModSoundEvents.POWER_BLOOD_FLECHETTES_USE, of()
				.sound(ofFile(id("power/blood_flechettes/use"))));
		exporter.add(ModSoundEvents.POWER_BLOOD_FLECHETTES_LIFE_DRAIN, of()
				.sound(ofFile(id("power/blood_flechettes/life_drain"))));

		exporter.add(ModSoundEvents.POWER_BLOODRUSH_USE, of()
				.sound(ofFile(id("power/bloodrush/use"))));

		exporter.add(ModSoundEvents.POWER_CARNAGE_USE, of()
				.sound(ofFile(id("power/carnage/use"))));
		exporter.add(ModSoundEvents.POWER_CARNAGE_HIT, of()
				.sound(ofFile(id("power/carnage/hit"))));

		exporter.add(ModSoundEvents.POWER_DARK_FORM_ON, of()
				.sound(ofFile(id("power/dark_form/on"))));
		exporter.add(ModSoundEvents.POWER_DARK_FORM_OFF, of()
				.sound(ofFile(id("power/vampire_form_change_off"))));

		exporter.add(ModSoundEvents.POWER_HAEMOGENESIS_USE, of()
				.sound(ofFile(id("power/haemogenesis/use"))));

		exporter.add(ModSoundEvents.POWER_HYPNOTIZE_USE, of()
				.sound(ofFile(id("power/hypnotize/use"))));

		exporter.add(ModSoundEvents.POWER_KEEN_SENSES_ON, of()
				.sound(ofFile(id("power/keen_senses/on"))));
		exporter.add(ModSoundEvents.POWER_KEEN_SENSES_OFF, of()
				.sound(ofFile(id("power/night_vision/off"))));
		exporter.add(ModSoundEvents.POWER_KEEN_SENSES_HEARTBEAT, of()
				.sound(ofEvent(SoundEvents.ENTITY_WARDEN_HEARTBEAT)));

		exporter.add(ModSoundEvents.POWER_MIST_FORM_ON, of()
				.sound(ofFile(id("power/mist_form/on"))));
		exporter.add(ModSoundEvents.POWER_MIST_FORM_OFF, of()
				.sound(ofFile(id("power/vampire_form_change_off"))));

		exporter.add(ModSoundEvents.POWER_VAMPIRIC_THRALL_CONVERT, of()
				.sound(ofFile(id("power/vampiric_thrall/convert"))));
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_sounds";
	}
}
