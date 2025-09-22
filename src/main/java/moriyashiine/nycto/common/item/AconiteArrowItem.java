/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item;

import moriyashiine.nycto.common.entity.projectile.AconiteArrowEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AconiteArrowItem extends ArrowItem {
	public AconiteArrowItem(Item.Settings settings) {
		super(settings);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
		return new AconiteArrowEntity(world, shooter, stack, shotFrom);
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		AconiteArrowEntity aconiteArrowEntity = new AconiteArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
		aconiteArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		return aconiteArrowEntity;
	}
}
