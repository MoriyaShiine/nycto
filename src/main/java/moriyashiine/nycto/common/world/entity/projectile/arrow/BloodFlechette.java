/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.projectile.arrow;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BloodFlechette extends ThrowableProjectile {
	public BloodFlechette(EntityType<? extends ThrowableProjectile> type, Level level) {
		super(type, level);
	}

	public BloodFlechette(Level level, LivingEntity mob) {
		super(ModEntityTypes.BLOOD_FLECHETTE, mob.getX(), mob.getEyeY() - 0.1, mob.getZ(), level);
		setOwner(mob);
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		playSound(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_BLOCK);
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		playSound(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_ENTITY);
		Entity entity = hitResult.getEntity();
		if (level() instanceof ServerLevel level) {
			entity.invulnerableTime = 0;
			boolean wasHurt = entity.hurtServer(level, damageSources().thrown(this, getOwner()), 2);
			if (wasHurt && !entity.is(ModEntityTypeTags.HAS_NO_BLOOD) && entity instanceof LivingEntity living) {
				NyctoAPI.applyHealBlock(living, 160, getOwner());
			}
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level().isClientSide()) {
			NyctoUtil.spawnBloodParticles(this);
			discard();
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
	}
}
