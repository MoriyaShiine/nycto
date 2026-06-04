/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoSoundEvents {
	private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> ALTAR_USE = register("block.altar.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_BOTTLE_DRINK = register("item.blood_bottle.drink");
	public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRIC_DAGGER_EXTRACT_FAIL = register("item.vampiric_dagger.extract_fail");
	public static final DeferredHolder<SoundEvent, SoundEvent> GENERIC_TRANSFORM_VAMPIRE = register("entity.generic.transform.vampire");
	public static final DeferredHolder<SoundEvent, SoundEvent> GENERIC_BLOOD_DRAIN_BLOCKED = register("entity.generic.blood_drain_blocked");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIREBOMB_IMPACT = register("entity.firebomb.impact");
	public static final DeferredHolder<SoundEvent, SoundEvent> HUNTER_AMBIENT = register("entity.hunter.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> HUNTER_HURT = register("entity.hunter.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> HUNTER_DEATH = register("entity.hunter.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_AMBIENT = register("entity.vampire.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_HURT = register("entity.vampire.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_DEATH = register("entity.vampire.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_FLECHETTE_HIT_BLOCK = register("entity.blood_flechette.hit_block");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_FLECHETTE_HIT_ENTITY = register("entity.blood_flechette.hit_entity");

	public static final DeferredHolder<SoundEvent, SoundEvent> NIGHT_VISION_ON = register("power.night_vision.on");
	public static final DeferredHolder<SoundEvent, SoundEvent> NIGHT_VISION_OFF = register("power.night_vision.off");
	public static final DeferredHolder<SoundEvent, SoundEvent> BAT_FORM_ON = register("power.bat_form.on");
	public static final DeferredHolder<SoundEvent, SoundEvent> BAT_FORM_OFF = register("power.bat_form.off");
	public static final DeferredHolder<SoundEvent, SoundEvent> BAT_SWARM_USE = register("power.bat_swarm.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> BATSTEP_USE = register("power.batstep.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_BARRIER_USE = register("power.blood_barrier.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_BARRIER_HIT = register("power.blood_barrier.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_BARRIER_BREAK = register("power.blood_barrier.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_FLECHETTES_USE = register("power.blood_flechettes.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_FLECHETTES_LIFE_DRAIN = register("power.blood_flechettes.life_drain");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOODRUSH_USE = register("power.bloodrush.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> CARNAGE_USE = register("power.carnage.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> CARNAGE_HIT = register("power.carnage.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> DARK_FORM_ON = register("power.dark_form.on");
	public static final DeferredHolder<SoundEvent, SoundEvent> DARK_FORM_OFF = register("power.dark_form.off");
	public static final DeferredHolder<SoundEvent, SoundEvent> DARK_FORM_FLAP = register("entity.dark_form.flap");
	public static final DeferredHolder<SoundEvent, SoundEvent> HAEMOGENESIS_USE = register("power.haemogenesis.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> HYPNOTIZE_USE = register("power.hypnotize.use");
	public static final DeferredHolder<SoundEvent, SoundEvent> KEEN_SENSES_ON = register("power.keen_senses.on");
	public static final DeferredHolder<SoundEvent, SoundEvent> KEEN_SENSES_OFF = register("power.keen_senses.off");
	public static final DeferredHolder<SoundEvent, SoundEvent> KEEN_SENSES_HEARTBEAT = register("power.keen_senses.heartbeat");
	public static final DeferredHolder<SoundEvent, SoundEvent> MIST_FORM_ON = register("power.mist_form.on");
	public static final DeferredHolder<SoundEvent, SoundEvent> MIST_FORM_OFF = register("power.mist_form.off");
	public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRIC_THRALL_CONVERT = register("power.vampiric_thrall.convert");

	private NyctoSoundEvents() {
	}

	public static void register(IEventBus bus) {
		SOUND_EVENTS.register(bus);
	}

	private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(NyctoNeoForge.id(name)));
	}
}
