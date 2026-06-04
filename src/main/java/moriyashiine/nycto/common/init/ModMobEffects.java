/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.world.effect.TransformationMobEffect;
import moriyashiine.nycto.common.world.effect.VampireWardMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModMobEffects {
	public static final Holder<MobEffect> VAMPIRE_WARD = ModRegistration.MOB_EFFECTS.register("vampire_ward", () -> new VampireWardMobEffect(MobEffectCategory.BENEFICIAL, 0xE0C16D));
	public static final Holder<MobEffect> VAMPIRISM = ModRegistration.MOB_EFFECTS.register("vampirism", () -> new TransformationMobEffect(MobEffectCategory.NEUTRAL, 0x7F0000, ModTransformations.HUMAN, ModTransformations.VAMPIRE, ModSoundEvents.GENERIC_TRANSFORM_VAMPIRE));
	public static final Holder<MobEffect> HYPNOTIZED = ModRegistration.MOB_EFFECTS.register("hypnotized", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x9ABBB7) {
	});
	public static final Holder<MobEffect> STUNNED = ModRegistration.MOB_EFFECTS.register("stunned", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x43372F) {
	});

	public static void init() {
	}
}
