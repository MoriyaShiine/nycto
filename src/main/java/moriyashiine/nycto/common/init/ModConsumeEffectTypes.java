/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.item.consume.ClearNegativeEffectsConsumeEffect;
import moriyashiine.nycto.common.item.consume.FillBloodConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerConsumeEffectType;

public class ModConsumeEffectTypes {
	public static final ConsumeEffect.Type<ClearNegativeEffectsConsumeEffect> CLEAR_NEGATIVE_EFFECTS = registerConsumeEffectType("clear_negative_effects", ClearNegativeEffectsConsumeEffect.CODEC, ClearNegativeEffectsConsumeEffect.PACKET_CODEC);
	public static final ConsumeEffect.Type<FillBloodConsumeEffect> FILL_BLOOD = registerConsumeEffectType("fill_blood", FillBloodConsumeEffect.CODEC, FillBloodConsumeEffect.PACKET_CODEC);

	public static void init() {
	}
}
