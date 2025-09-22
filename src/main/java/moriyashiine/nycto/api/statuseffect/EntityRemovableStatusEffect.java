/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class EntityRemovableStatusEffect extends StatusEffect {
	protected EntityRemovableStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	public abstract void onRemoved(LivingEntity entity);
}
