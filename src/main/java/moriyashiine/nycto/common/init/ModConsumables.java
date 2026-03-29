/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.item.consumeeffects.ClearNegativeEffectsConsumeEffect;
import moriyashiine.nycto.common.world.item.consumeeffects.FillBloodConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class ModConsumables {
	public static final int BLOOD_FILL_AMOUNT = 10;

	public static final Consumable BLOOD_BOTTLE = Consumables.defaultDrink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.onConsume(new FillBloodConsumeEffect(BLOOD_FILL_AMOUNT, new ApplyStatusEffectsConsumeEffect(
					List.of(
							new MobEffectInstance(MobEffects.POISON, 200),
							new MobEffectInstance(MobEffects.HUNGER, 200),
							new MobEffectInstance(MobEffects.NAUSEA, 200)
					)
			)))
			.build();
	public static final Consumable VAMPIRE_BLOOD_BOTTLE = Consumables.defaultDrink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.onConsume(new FillBloodConsumeEffect(BLOOD_FILL_AMOUNT, new ApplyStatusEffectsConsumeEffect(
					List.of(
							new MobEffectInstance(ModMobEffects.VAMPIRISM, 600)
					)
			)))
			.build();
	public static final Consumable AMBROSIA_BOTTLE = Consumables.defaultDrink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.onConsume(new ApplyStatusEffectsConsumeEffect(
					List.of(
							new MobEffectInstance(MobEffects.HASTE, 400, 1),
							new MobEffectInstance(MobEffects.STRENGTH, 6000),
							new MobEffectInstance(MobEffects.SPEED, 6000),
							new MobEffectInstance(MobEffects.NIGHT_VISION, 2400)
					)
			))
			.onConsume(ClearNegativeEffectsConsumeEffect.INSTANCE)
			.onConsume(new FillBloodConsumeEffect(Short.MAX_VALUE))
			.build();
}
