/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.criterion.PlayerAppliesEffectsCriterion;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.TickCriterion;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerCriterion;

public class ModCriteria {
	public static final ConsumeItemCriterion EXTRACT_BLOOD = registerCriterion("extract_blood", new ConsumeItemCriterion());
	public static final PlayerAppliesEffectsCriterion PLAYER_APPLIES_EFFECTS = registerCriterion("player_applies_effects", new PlayerAppliesEffectsCriterion());
	public static final TickCriterion CHANGE_TRANSFORMATION = registerCriterion("change_transformation", new TickCriterion());
	public static final TickCriterion CHANGE_POWERS = registerCriterion("change_powers", new TickCriterion());

	public static void init() {
	}
}
