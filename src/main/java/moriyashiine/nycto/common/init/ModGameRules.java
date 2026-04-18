/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;


import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.VampireSunExposureMode;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGameRules {
	public static final GameRule<VampireSunExposureMode> VAMPIRE_SUN_EXPOSURE_MODE = GameRuleBuilder.forEnum(VampireSunExposureMode.NORMAL).category(GameRuleCategory.PLAYER).buildAndRegister(Nycto.id("vampire_sun_exposure_mode"));

	public static final GameRule<Boolean> SPAWN_HUNTERS = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.SPAWNING).buildAndRegister(Nycto.id("spawn_hunters"));
	public static final GameRule<Boolean> SPAWN_VAMPIRES = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.SPAWNING).buildAndRegister(Nycto.id("spawn_vampires"));

	public static void init() {
	}
}
