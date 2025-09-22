/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.projectile;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WoodenStakeEntity extends PersistentProjectileEntity {
	public WoodenStakeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public WoodenStakeEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.WOODEN_STAKE, x, y, z, world, stack, shotFrom);
	}

	public WoodenStakeEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.WOODEN_STAKE, owner, world, stack, shotFrom);
		if (owner.isInCreativeMode()) {
			pickupType = PickupPermission.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return ModItems.WOODEN_STAKE.getDefaultStack();
	}
}
