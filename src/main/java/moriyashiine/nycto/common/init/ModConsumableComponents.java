/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.item.consume.ClearNegativeEffectsConsumeEffect;
import moriyashiine.nycto.common.item.consume.FillBloodConsumeEffect;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

public class ModConsumableComponents {
	public static final int BLOOD_FILL_AMOUNT = 10;

	public static final ConsumableComponent BLOOD_BOTTLE = ConsumableComponents.drink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.consumeEffect(new FillBloodConsumeEffect(BLOOD_FILL_AMOUNT, new ApplyEffectsConsumeEffect(
					List.of(
							new StatusEffectInstance(StatusEffects.POISON, 200),
							new StatusEffectInstance(StatusEffects.HUNGER, 200),
							new StatusEffectInstance(StatusEffects.NAUSEA, 200)
					)
			)))
			.build();
	public static final ConsumableComponent VAMPIRE_BLOOD_BOTTLE = ConsumableComponents.drink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.consumeEffect(new FillBloodConsumeEffect(BLOOD_FILL_AMOUNT, new ApplyEffectsConsumeEffect(
					List.of(
							new StatusEffectInstance(ModStatusEffects.VAMPIRISM, 600)
					)
			)))
			.build();
	public static final ConsumableComponent AMBROSIA_BOTTLE = ConsumableComponents.drink()
			.consumeSeconds(2)
			.sound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK)
			.consumeEffect(new ApplyEffectsConsumeEffect(
					List.of(
							new StatusEffectInstance(StatusEffects.HASTE, 400, 1),
							new StatusEffectInstance(StatusEffects.STRENGTH, 6000),
							new StatusEffectInstance(StatusEffects.SPEED, 6000),
							new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2400)
					)
			))
			.consumeEffect(ClearNegativeEffectsConsumeEffect.INSTANCE)
			.consumeEffect(new FillBloodConsumeEffect(Short.MAX_VALUE))
			.build();
}
