/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.advancements.criterion.PlayerAppliesEffectsTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.PlayerTrigger;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerTrigger;

public class ModTriggers {
	public static final ConsumeItemTrigger EXTRACT_BLOOD = registerTrigger("extract_blood", new ConsumeItemTrigger());
	public static final PlayerAppliesEffectsTrigger PLAYER_APPLIES_EFFECTS = registerTrigger("player_applies_effects", new PlayerAppliesEffectsTrigger());
	public static final PlayerTrigger CHANGE_TRANSFORMATION = registerTrigger("change_transformation", new PlayerTrigger());
	public static final PlayerTrigger CHANGE_POWERS = registerTrigger("change_powers", new PlayerTrigger());

	public static void init() {
	}
}
