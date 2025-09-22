/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class PoisonedFoodEvent implements EatFoodEvent {
	@Override
	public void eat(World world, LivingEntity entity, ItemStack stack, FoodComponent foodComponent) {
		if (world instanceof ServerWorld serverWorld && stack.getOrDefault(ModComponentTypes.POISONED, false)) {
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1));
			if (NyctoAPI.isWerewolf(entity)) {
				entity.damage(serverWorld, world.getDamageSources().create(ModDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
			}
		}
	}
}
