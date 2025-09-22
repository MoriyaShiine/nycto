/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEvent;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEventReference;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_ALTAR_USE = registerSoundEvent("block.altar.use");

	public static final RegistryEntry<SoundEvent> ITEM_BLOOD_BOTTLE_DRINK = registerSoundEventReference("item.blood_bottle.drink");
	public static final SoundEvent ITEM_VAMPIRIC_DAGGER_EXTRACT_FAIL = registerSoundEvent("item.vampiric_dagger.extract_fail");

	public static final SoundEvent ENTITY_GENERIC_REMOVE_POWER = registerSoundEvent("entity.generic.remove_power");
	public static final SoundEvent ENTITY_GENERIC_TRANSFORM_HUMAN = registerSoundEvent("entity.generic.transform_human");
	public static final SoundEvent ENTITY_GENERIC_TRANSFORM_VAMPIRE = registerSoundEvent("entity.generic.transform.vampire");
	public static final SoundEvent ENTITY_GENERIC_BLOOD_DRAIN_BLOCKED = registerSoundEvent("entity.generic.blood_drain_blocked");
	public static final SoundEvent ENTITY_GENERIC_SIZZLE = registerSoundEvent("entity.generic.sizzle");

	public static final SoundEvent ENTITY_VAMPIRE_AMBIENT = registerSoundEvent("entity.vampire.ambient");
	public static final SoundEvent ENTITY_VAMPIRE_HURT = registerSoundEvent("entity.vampire.hurt");
	public static final SoundEvent ENTITY_VAMPIRE_DEATH = registerSoundEvent("entity.vampire.death");

	public static final SoundEvent ENTITY_HUNTER_AMBIENT = registerSoundEvent("entity.hunter.ambient");
	public static final SoundEvent ENTITY_HUNTER_HURT = registerSoundEvent("entity.hunter.hurt");
	public static final SoundEvent ENTITY_HUNTER_DEATH = registerSoundEvent("entity.hunter.death");

	public static final SoundEvent ENTITY_DARK_FORM_FLAP = registerSoundEvent("entity.dark_form.flap");

	public static final SoundEvent ENTITY_BLOOD_FLECHETTE_HIT_BLOCK = registerSoundEvent("entity.blood_flechette.hit_block");
	public static final SoundEvent ENTITY_BLOOD_FLECHETTE_HIT_ENTITY = registerSoundEvent("entity.blood_flechette.hit_entity");

	public static final SoundEvent ENTITY_FIREBOMB_IMPACT = registerSoundEvent("entity.firebomb.impact");

	public static final SoundEvent POWER_NIGHT_VISION_ON = registerSoundEvent("power.night_vision.on");
	public static final SoundEvent POWER_NIGHT_VISION_OFF = registerSoundEvent("power.night_vision.off");

	public static final SoundEvent POWER_BAT_FORM_ON = registerSoundEvent("power.bat_form.on");
	public static final SoundEvent POWER_BAT_FORM_OFF = registerSoundEvent("power.bat_form.off");

	public static final SoundEvent POWER_BAT_SWARM_USE = registerSoundEvent("power.bat_swarm.use");

	public static final SoundEvent POWER_BATSTEP_USE = registerSoundEvent("power.batstep.use");

	public static final SoundEvent POWER_BLOOD_BARRIER_USE = registerSoundEvent("power.blood_barrier.use");
	public static final SoundEvent POWER_BLOOD_BARRIER_HIT = registerSoundEvent("power.blood_barrier.hit");
	public static final SoundEvent POWER_BLOOD_BARRIER_BREAK = registerSoundEvent("power.blood_barrier.break");

	public static final SoundEvent POWER_BLOOD_FLECHETTES_USE = registerSoundEvent("power.blood_flechettes.use");
	public static final SoundEvent POWER_BLOOD_FLECHETTES_LIFE_DRAIN = registerSoundEvent("power.blood_flechettes.life_drain");

	public static final SoundEvent POWER_BLOODRUSH_USE = registerSoundEvent("power.bloodrush.use");

	public static final SoundEvent POWER_CARNAGE_USE = registerSoundEvent("power.carnage.use");
	public static final SoundEvent POWER_CARNAGE_HIT = registerSoundEvent("power.carnage.hit");

	public static final SoundEvent POWER_DARK_FORM_ON = registerSoundEvent("power.dark_form.on");
	public static final SoundEvent POWER_DARK_FORM_OFF = registerSoundEvent("power.dark_form.off");

	public static final SoundEvent POWER_HAEMOGENESIS_USE = registerSoundEvent("power.haemogenesis.use");

	public static final SoundEvent POWER_HYPNOTIZE_USE = registerSoundEvent("power.hypnotize.use");

	public static final SoundEvent POWER_KEEN_SENSES_ON = registerSoundEvent("power.keen_senses.on");
	public static final SoundEvent POWER_KEEN_SENSES_OFF = registerSoundEvent("power.keen_senses.off");
	public static final SoundEvent POWER_KEEN_SENSES_HEARTBEAT = registerSoundEvent("power.keen_senses.heartbeat");

	public static final SoundEvent POWER_MIST_FORM_ON = registerSoundEvent("power.mist_form.on");
	public static final SoundEvent POWER_MIST_FORM_OFF = registerSoundEvent("power.mist_form.off");

	public static final SoundEvent POWER_VAMPIRIC_THRALL_CONVERT = registerSoundEvent("power.vampiric_thrall.convert");

	public static void init() {
	}
}
