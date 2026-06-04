/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.projectile;

import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class AconiteArrowProjectile extends AbstractArrow {
	public AconiteArrowProjectile(EntityType<? extends AconiteArrowProjectile> type, Level level) {
		super(type, level);
	}

	public AconiteArrowProjectile(Level level, double x, double y, double z, ItemStack pickupStack, @Nullable ItemStack weapon) {
		super(NyctoEntityTypes.ACONITE_ARROW.get(), x, y, z, level, pickupStack, weapon);
	}

	public AconiteArrowProjectile(Level level, LivingEntity owner, ItemStack pickupStack, @Nullable ItemStack weapon) {
		super(NyctoEntityTypes.ACONITE_ARROW.get(), owner, level, pickupStack, weapon);
		if (owner.hasInfiniteMaterials()) {
			pickup = Pickup.CREATIVE_ONLY;
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return NyctoHunterContent.ACONITE_ARROW.get().getDefaultInstance();
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60), getEffectSource());
	}
}
