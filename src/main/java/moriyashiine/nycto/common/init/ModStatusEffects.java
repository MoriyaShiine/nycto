/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.statuseffect.TransformStatusEffect;
import moriyashiine.nycto.common.statuseffect.VampireWardStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerStatusEffect;

public class ModStatusEffects {
	public static final RegistryEntry<StatusEffect> VAMPIRE_WARD = registerStatusEffect("vampire_ward", new VampireWardStatusEffect(StatusEffectCategory.BENEFICIAL, 0xE0C16D));
	public static final RegistryEntry<StatusEffect> VAMPIRISM = registerStatusEffect("vampirism", new TransformStatusEffect(StatusEffectCategory.NEUTRAL, 0x7F0000, ModTransformations.HUMAN, ModTransformations.VAMPIRE, ModSoundEvents.ENTITY_GENERIC_TRANSFORM_VAMPIRE));
	public static final RegistryEntry<StatusEffect> HYPNOTIZED = registerStatusEffect("hypnotized", new StatusEffect(StatusEffectCategory.HARMFUL, 0x9ABBB7) {
	});
	public static final RegistryEntry<StatusEffect> STUNNED = registerStatusEffect("stunned", new StatusEffect(StatusEffectCategory.HARMFUL, 0x43372F) {
	});

	public static void init() {
	}
}
