/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;


import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;

public class ModGameRules {
	public static final GameRule<Boolean> SPAWN_HUNTERS = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.SPAWNING).buildAndRegister(Nycto.id("spawn_hunters"));
	public static final GameRule<Boolean> SPAWN_VAMPIRES = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.SPAWNING).buildAndRegister(Nycto.id("spawn_vampires"));

	public static void init() {
	}
}
