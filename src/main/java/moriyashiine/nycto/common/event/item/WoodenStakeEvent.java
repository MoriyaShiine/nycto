/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.world.entity.projectile.arrow.WoodenStake;
import moriyashiine.nycto.common.world.item.WoodenStakeItem;
import moriyashiine.strawberrylib.api.event.ModifyStackDamageEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WoodenStakeEvent implements ModifyStackDamageEvent {
	private static final float MAX_STAKE_DAMAGE = (WoodenStakeItem.DAMAGE + 1) * 1.5F;

	@Override
	public float modify(ServerLevel level, ItemStack stack, Entity victim, DamageSource source, float damage) {
		return damage > MAX_STAKE_DAMAGE && isWoodenStake(source) ? -(damage - MAX_STAKE_DAMAGE) : 0;
	}

	public static boolean isWoodenStake(DamageSource source) {
		return source.getDirectEntity() instanceof WoodenStake || (source.getDirectEntity() instanceof LivingEntity living && living.getMainHandItem().is(ModItems.WOODEN_STAKE));
	}
}
