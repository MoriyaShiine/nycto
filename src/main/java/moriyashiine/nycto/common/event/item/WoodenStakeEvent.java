/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.common.entity.projectile.WoodenStakeEntity;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.item.WoodenStakeItem;
import moriyashiine.strawberrylib.api.event.ModifyStackDamageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class WoodenStakeEvent implements ModifyStackDamageEvent {
	private static final float MAX_STAKE_DAMAGE = (WoodenStakeItem.DAMAGE + 1) * 1.5F;

	@Override
	public float modify(float amount, ServerWorld world, ItemStack stack, Entity target, DamageSource source) {
		return amount > MAX_STAKE_DAMAGE && isWoodenStake(source) ? -(amount - MAX_STAKE_DAMAGE) : 0;
	}

	public static boolean isWoodenStake(DamageSource source) {
		return source.getSource() instanceof WoodenStakeEntity || (source.getSource() instanceof LivingEntity living && living.getMainHandStack().isOf(ModItems.WOODEN_STAKE));
	}
}
