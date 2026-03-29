/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item;

import moriyashiine.nycto.common.world.entity.projectile.arrow.AconiteArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.Nullable;

public class AconiteArrowItem extends ArrowItem {
	public AconiteArrowItem(Properties properties) {
		super(properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		return new AconiteArrow(level, owner, itemStack, firedFromWeapon);
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
		AconiteArrow aconiteArrow = new AconiteArrow(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
		aconiteArrow.pickup = AbstractArrow.Pickup.ALLOWED;
		return aconiteArrow;
	}
}
