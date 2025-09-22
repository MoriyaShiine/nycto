/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
	public static final GameRules.Key<GameRules.BooleanRule> DO_HUNTER_SPAWNING = GameRuleRegistry.register("doHunterSpawning", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));

	public static void init() {
	}
}
