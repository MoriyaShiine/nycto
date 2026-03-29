/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.projectile.arrow;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class WoodenStake extends AbstractArrow {
	public WoodenStake(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public WoodenStake(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(ModEntityTypes.WOODEN_STAKE, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	public WoodenStake(Level level, LivingEntity mob, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(ModEntityTypes.WOODEN_STAKE, mob, level, pickupItemStack, firedFromWeapon);
		if (mob.hasInfiniteMaterials()) {
			pickup = Pickup.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ModItems.WOODEN_STAKE.getDefaultInstance();
	}
}
