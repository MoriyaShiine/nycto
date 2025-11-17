/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.NegativePower;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.power.NightVisionPower;
import moriyashiine.nycto.common.power.vampire.*;
import moriyashiine.nycto.common.power.vampire.weakness.HydrophobiaPower;
import moriyashiine.nycto.common.power.vampire.weakness.VilePresencePower;
import net.minecraft.registry.Registry;

public class ModPowers {
	public static final Power NIGHT_VISION = registerPower("night_vision", new NightVisionPower());

	public static final VampireActivePower BAT_FORM = registerPower("bat_form", new BatFormPower());
	public static final VampireActivePower BAT_SWARM = registerPower("bat_swarm", new BatSwarmPower());
	public static final VampireActivePower BATSTEP = registerPower("batstep", new BatstepPower());
	public static final VampireActivePower BLOOD_BARRIER = registerPower("blood_barrier", new BloodBarrierPower());
	public static final VampireActivePower BLOOD_FLECHETTES = registerPower("blood_flechettes", new BloodFlechettesPower());
	public static final VampireActivePower BLOODRUSH = registerPower("bloodrush", new BloodrushPower());
	public static final VampireActivePower CARNAGE = registerPower("carnage", new CarnagePower());
	public static final VampireActivePower DARK_FORM = registerPower("dark_form", new DarkFormPower());
	public static final VampireActivePower HAEMOGENESIS = registerPower("haemogenesis", new HaemogenesisPower());
	public static final VampireActivePower HYPNOTIZE = registerPower("hypnotize", new HypnotizePower());
	public static final VampireActivePower KEEN_SENSES = registerPower("keen_senses", new KeenSensesPower());
	public static final VampireActivePower MIST_FORM = registerPower("mist_form", new MistFormPower());
	public static final VampireActivePower VAMPIRIC_THRALL = registerPower("vampiric_thrall", new VampiricThrallPower());

	public static final Power HYDROPHOBIA = registerPower("hydrophobia", new HydrophobiaPower());
	public static final Power PHOTOPHOBIA = registerPower("photophobia", new NegativePower());
	public static final Power PYROPHOBIA = registerPower("pyrophobia", new NegativePower());
	public static final Power RICH_TASTES = registerPower("rich_tastes", new NegativePower());
	public static final Power THIN_BLOOD = registerPower("thin_blood", new NegativePower());
	public static final Power VILE_PRESENCE = registerPower("vile_presence", new VilePresencePower());

	private static <T extends Power> T registerPower(String name, T power) {
		return Registry.register(NyctoRegistries.POWER, Nycto.id(name), power);
	}

	public static void init() {
	}
}
