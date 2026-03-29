/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoisonedFoodEvent implements EatFoodEvent {
	@Override
	public void eat(Level level, LivingEntity user, ItemStack stack, FoodProperties properties) {
		if (level instanceof ServerLevel serverLevel && stack.getOrDefault(ModComponentTypes.POISONED, false)) {
			user.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
			if (NyctoAPI.isWerewolf(user)) {
				user.hurtServer(serverLevel, level.damageSources().source(ModDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
			}
		}
	}
}
