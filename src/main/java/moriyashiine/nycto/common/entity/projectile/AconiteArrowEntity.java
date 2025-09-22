/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.projectile;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AconiteArrowEntity extends PersistentProjectileEntity {
	public AconiteArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public AconiteArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.ACONITE_ARROW, x, y, z, world, stack, shotFrom);
	}

	public AconiteArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.ACONITE_ARROW, owner, world, stack, shotFrom);
		if (owner.isInCreativeMode()) {
			pickupType = PickupPermission.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return ModItems.ACONITE_ARROW.getDefaultStack();
	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60), getEffectCause());
	}
}
