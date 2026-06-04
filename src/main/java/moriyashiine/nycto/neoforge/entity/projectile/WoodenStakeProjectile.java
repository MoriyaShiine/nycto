/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.projectile;

import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class WoodenStakeProjectile extends AbstractArrow {
	public WoodenStakeProjectile(EntityType<? extends WoodenStakeProjectile> type, Level level) {
		super(type, level);
	}

	public WoodenStakeProjectile(Level level, double x, double y, double z, ItemStack pickupStack, @Nullable ItemStack weapon) {
		super(NyctoEntityTypes.WOODEN_STAKE.get(), x, y, z, level, pickupStack, weapon);
		setBaseDamage(3);
	}

	public WoodenStakeProjectile(Level level, LivingEntity owner, ItemStack pickupStack, @Nullable ItemStack weapon) {
		super(NyctoEntityTypes.WOODEN_STAKE.get(), owner, level, pickupStack, weapon);
		setBaseDamage(3);
		setSoundEvent(SoundEvents.CROSSBOW_HIT);
		if (owner.hasInfiniteMaterials()) {
			pickup = Pickup.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return NyctoItems.WOODEN_STAKE.get().getDefaultInstance();
	}
}
