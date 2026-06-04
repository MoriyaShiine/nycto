/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.item;

import moriyashiine.nycto.neoforge.entity.projectile.AconiteArrowProjectile;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.Nullable;

public final class AconiteArrowItem extends ArrowItem {
	public AconiteArrowItem(Properties properties) {
		super(properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
		return new AconiteArrowProjectile(level, shooter, stack.copyWithCount(1), weapon);
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
		AconiteArrowProjectile projectile = new AconiteArrowProjectile(level, position.x(), position.y(), position.z(), stack.copyWithCount(1), null);
		projectile.pickup = AbstractArrow.Pickup.ALLOWED;
		return projectile;
	}
}
