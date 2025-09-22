/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.projectile;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BloodFlechetteEntity extends ThrownEntity {
	public BloodFlechetteEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public BloodFlechetteEntity(LivingEntity owner, World world) {
		super(ModEntityTypes.BLOOD_FLECHETTE, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), world);
		setOwner(owner);
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		playSoundIfNotSilent(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_BLOCK);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		playSoundIfNotSilent(ModSoundEvents.ENTITY_BLOOD_FLECHETTE_HIT_ENTITY);
		Entity entity = entityHitResult.getEntity();
		if (getWorld() instanceof ServerWorld serverWorld) {
			entity.timeUntilRegen = 0;
			boolean damage = entity.damage(serverWorld, getDamageSources().thrown(this, getOwner()), 2);
			if (damage && !entity.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD) && entity instanceof LivingEntity living) {
				NyctoAPI.applyHealBlock(living, 160, getOwner());
			}
		}
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!getWorld().isClient) {
			NyctoUtil.spawnBloodParticles(this);
			discard();
		}
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
	}
}
