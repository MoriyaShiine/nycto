package moriyashiine.nycto.common.init;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEvent;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEventHolder;

public class NyctoSoundEvents {
	public static final SoundEvent ALTAR_USE = registerSoundEvent("block.altar.use");
	public static final SoundEvent BLOOD_FOUNTAIN_LOCK = registerSoundEvent("block.blood_fountain.lock");
	public static final SoundEvent BLOOD_FOUNTAIN_UNLOCK = registerSoundEvent("block.blood_fountain.unlock");

	public static final Holder<SoundEvent> BLOOD_BOTTLE_DRINK = registerSoundEventHolder("item.blood_bottle.drink");
	public static final SoundEvent VAMPIRIC_DAGGER_EXTRACT_FAIL = registerSoundEvent("item.vampiric_dagger.extract_fail");

	public static final SoundEvent GENERIC_REMOVE_POWER = registerSoundEvent("entity.generic.remove_power");
	public static final SoundEvent GENERIC_TRANSFORM_HUMAN = registerSoundEvent("entity.generic.transform_human");
	public static final SoundEvent GENERIC_TRANSFORM_VAMPIRE = registerSoundEvent("entity.generic.transform.vampire");
	public static final SoundEvent GENERIC_BLOOD_DRAIN_BLOCKED = registerSoundEvent("entity.generic.blood_drain_blocked");
	public static final SoundEvent GENERIC_SIZZLE = registerSoundEvent("entity.generic.sizzle");

	public static final SoundEvent VAMPIRE_AMBIENT = registerSoundEvent("entity.vampire.ambient");
	public static final SoundEvent VAMPIRE_HURT = registerSoundEvent("entity.vampire.hurt");
	public static final SoundEvent VAMPIRE_DEATH = registerSoundEvent("entity.vampire.death");

	public static final SoundEvent HUNTER_AMBIENT = registerSoundEvent("entity.hunter.ambient");
	public static final SoundEvent HUNTER_HURT = registerSoundEvent("entity.hunter.hurt");
	public static final SoundEvent HUNTER_DEATH = registerSoundEvent("entity.hunter.death");

	public static final SoundEvent DARK_FORM_FLAP = registerSoundEvent("entity.dark_form.flap");

	public static final SoundEvent BLOOD_FLECHETTE_HIT_BLOCK = registerSoundEvent("entity.blood_flechette.hit_block");
	public static final SoundEvent BLOOD_FLECHETTE_HIT_ENTITY = registerSoundEvent("entity.blood_flechette.hit_entity");

	public static final SoundEvent FIREBOMB_IMPACT = registerSoundEvent("entity.firebomb.impact");

	public static final SoundEvent NIGHT_VISION_ON = registerSoundEvent("power.night_vision.on");
	public static final SoundEvent NIGHT_VISION_OFF = registerSoundEvent("power.night_vision.off");

	public static final SoundEvent BAT_FORM_ON = registerSoundEvent("power.bat_form.on");
	public static final SoundEvent BAT_FORM_OFF = registerSoundEvent("power.bat_form.off");

	public static final SoundEvent BAT_SWARM_USE = registerSoundEvent("power.bat_swarm.use");

	public static final SoundEvent BATSTEP_USE = registerSoundEvent("power.batstep.use");

	public static final SoundEvent BLOOD_BARRIER_USE = registerSoundEvent("power.blood_barrier.use");
	public static final SoundEvent BLOOD_BARRIER_HIT = registerSoundEvent("power.blood_barrier.hit");
	public static final SoundEvent BLOOD_BARRIER_BREAK = registerSoundEvent("power.blood_barrier.break");

	public static final SoundEvent BLOOD_FLECHETTES_USE = registerSoundEvent("power.blood_flechettes.use");
	public static final SoundEvent BLOOD_FLECHETTES_LIFE_DRAIN = registerSoundEvent("power.blood_flechettes.life_drain");

	public static final SoundEvent BLOODRUSH_USE = registerSoundEvent("power.bloodrush.use");

	public static final SoundEvent CARNAGE_USE = registerSoundEvent("power.carnage.use");
	public static final SoundEvent CARNAGE_HIT = registerSoundEvent("power.carnage.hit");

	public static final SoundEvent DARK_FORM_ON = registerSoundEvent("power.dark_form.on");
	public static final SoundEvent DARK_FORM_OFF = registerSoundEvent("power.dark_form.off");

	public static final SoundEvent HAEMOGENESIS_USE = registerSoundEvent("power.haemogenesis.use");

	public static final SoundEvent HYPNOTIZE_USE = registerSoundEvent("power.hypnotize.use");
	public static final SoundEvent HYPNOTIZE_USE_INVERSE = registerSoundEvent("power.hypnotize.use_inverse");

	public static final SoundEvent KEEN_SENSES_ON = registerSoundEvent("power.keen_senses.on");
	public static final SoundEvent KEEN_SENSES_OFF = registerSoundEvent("power.keen_senses.off");
	public static final SoundEvent KEEN_SENSES_HEARTBEAT = registerSoundEvent("power.keen_senses.heartbeat");

	public static final SoundEvent MIST_FORM_ON = registerSoundEvent("power.mist_form.on");
	public static final SoundEvent MIST_FORM_OFF = registerSoundEvent("power.mist_form.off");

	public static final SoundEvent VAMPIRIC_THRALL_CONVERT = registerSoundEvent("power.vampiric_thrall.convert");

	public static void init() {
	}
}
