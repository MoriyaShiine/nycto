/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class EntityRemovableMobEffect extends MobEffect {
	protected EntityRemovableMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	public abstract void onEffectRemoved(LivingEntity mob);
}
