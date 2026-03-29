/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.projectile.arrow;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class AconiteArrow extends AbstractArrow {
	public AconiteArrow(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public AconiteArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(ModEntityTypes.ACONITE_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	public AconiteArrow(Level level, LivingEntity mob, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(ModEntityTypes.ACONITE_ARROW, mob, level, pickupItemStack, firedFromWeapon);
		if (mob.hasInfiniteMaterials()) {
			pickup = Pickup.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ModItems.ACONITE_ARROW.getDefaultInstance();
	}

	@Override
	protected void doPostHurtEffects(LivingEntity mob) {
		super.doPostHurtEffects(mob);
		mob.addEffect(new MobEffectInstance(MobEffects.POISON, 60), getEffectSource());
	}
}
