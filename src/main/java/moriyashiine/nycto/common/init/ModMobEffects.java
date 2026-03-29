/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.world.effect.TransformationMobEffect;
import moriyashiine.nycto.common.world.effect.VampireWardMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerMobEffect;

public class ModMobEffects {
	public static final Holder<MobEffect> VAMPIRE_WARD = registerMobEffect("vampire_ward", new VampireWardMobEffect(MobEffectCategory.BENEFICIAL, 0xE0C16D));
	public static final Holder<MobEffect> VAMPIRISM = registerMobEffect("vampirism", new TransformationMobEffect(MobEffectCategory.NEUTRAL, 0x7F0000, ModTransformations.HUMAN, ModTransformations.VAMPIRE, ModSoundEvents.ENTITY_GENERIC_TRANSFORM_VAMPIRE));
	public static final Holder<MobEffect> HYPNOTIZED = registerMobEffect("hypnotized", new MobEffect(MobEffectCategory.HARMFUL, 0x9ABBB7) {
	});
	public static final Holder<MobEffect> STUNNED = registerMobEffect("stunned", new MobEffect(MobEffectCategory.HARMFUL, 0x43372F) {
	});

	public static void init() {
	}
}
