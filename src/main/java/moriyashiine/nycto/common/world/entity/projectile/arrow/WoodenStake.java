/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.projectile.arrow;

import moriyashiine.nycto.common.init.NyctoEntityTypes;
import moriyashiine.nycto.common.init.NyctoItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class WoodenStake extends AbstractArrow {
	public WoodenStake(EntityType<WoodenStake> type, Level level) {
		super(type, level);
	}

	public WoodenStake(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(NyctoEntityTypes.WOODEN_STAKE, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	public WoodenStake(Level level, LivingEntity mob, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(NyctoEntityTypes.WOODEN_STAKE, mob, level, pickupItemStack, firedFromWeapon);
		if (mob.hasInfiniteMaterials()) {
			pickup = Pickup.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return NyctoItems.WOODEN_STAKE.getDefaultInstance();
	}
}
