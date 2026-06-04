/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.projectile;

import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class BloodFlechetteProjectile extends AbstractArrow {
	public BloodFlechetteProjectile(EntityType<? extends BloodFlechetteProjectile> type, Level level) {
		super(type, level);
		pickup = Pickup.DISALLOWED;
	}

	public BloodFlechetteProjectile(Level level, LivingEntity owner) {
		super(NyctoEntityTypes.BLOOD_FLECHETTE.get(), owner, level, ItemStack.EMPTY, null);
		setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
		setBaseDamage(0);
		pickup = Pickup.DISALLOWED;
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		level().playSound(null, blockPosition(), NyctoSoundEvents.BLOOD_FLECHETTE_HIT_BLOCK.get(), SoundSource.PLAYERS, 0.5F, 1);
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		level().playSound(null, blockPosition(), NyctoSoundEvents.BLOOD_FLECHETTE_HIT_ENTITY.get(), SoundSource.PLAYERS, 0.15F, 1);
		Entity entity = hitResult.getEntity();
		if (level() instanceof ServerLevel serverLevel && entity instanceof LivingEntity living) {
			entity.invulnerableTime = 0;
			boolean hurt = living.hurt(damageSources().thrown(this, getOwner()), 3);
			if (hurt && NyctoBloodUtil.hasDrainableBlood(living)) {
				living.getPersistentData().putInt(NyctoPowers.TAG_BLOOD_FLECHETTES_MARK_UNTIL, living.tickCount + 160);
				moriyashiine.nycto.neoforge.NyctoData.applyHealBlock(living, 160);
			}
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.DAMAGE_INDICATOR, getX(), getY(), getZ(), 4, 0.12, 0.12, 0.12, 0.02);
			serverLevel.sendParticles(moriyashiine.nycto.neoforge.registry.NyctoParticleTypes.BLOOD.get(), getX(), getY(), getZ(), 8, 0.16, 0.16, 0.16, 0.02);
			discard();
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ItemStack.EMPTY;
	}
}
