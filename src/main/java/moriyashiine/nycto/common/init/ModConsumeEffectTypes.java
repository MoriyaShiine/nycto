/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.item.consumeeffects.ClearNegativeEffectsConsumeEffect;
import moriyashiine.nycto.common.world.item.consumeeffects.FillBloodConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerConsumeEffectType;

public class ModConsumeEffectTypes {
	public static final ConsumeEffect.Type<ClearNegativeEffectsConsumeEffect> CLEAR_NEGATIVE_EFFECTS = registerConsumeEffectType("clear_negative_effects", ClearNegativeEffectsConsumeEffect.CODEC, ClearNegativeEffectsConsumeEffect.PACKET_CODEC);
	public static final ConsumeEffect.Type<FillBloodConsumeEffect> FILL_BLOOD = registerConsumeEffectType("fill_blood", FillBloodConsumeEffect.CODEC, FillBloodConsumeEffect.PACKET_CODEC);

	public static void init() {
	}
}
