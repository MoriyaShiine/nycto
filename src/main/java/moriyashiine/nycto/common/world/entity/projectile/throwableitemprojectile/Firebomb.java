/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.projectile.throwableitemprojectile;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Firebomb extends ThrowableItemProjectile {
	private static final ParticleVelocity PARTICLE_VELOCITY = ParticleVelocity.of(new Vec3(0, 0.35, 0), 0.3);

	public Firebomb(EntityType<Firebomb> type, Level level) {
		super(type, level);
	}

	public Firebomb(Level level, double x, double y, double z, ItemStack stack) {
		super(ModEntityTypes.FIREBOMB, x, y, z, level, stack);
	}

	public Firebomb(Level level, LivingEntity owner, ItemStack stack) {
		super(ModEntityTypes.FIREBOMB, owner, level, stack);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.FIREBOMB;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (level() instanceof ServerLevel level) {
			AABB box = getBoundingBox().move(hitResult.getLocation().subtract(position())).inflate(3, 2, 3);
			BlockPos.betweenClosed(box).forEach(pos -> {
				if (pos.equals(blockPosition()) || (getRandom().nextInt(3) == 0 && pos.closerToCenterThan(position(), 2.5))) {
					if (!level.isRainingAt(pos)) {
						BlockState state = level.getBlockState(pos);
						if (state.canBeReplaced() && state.getFluidState().isEmpty() && ModBlocks.FIREBOMB.defaultBlockState().canSurvive(level, pos)) {
							level.setBlockAndUpdate(pos, ModBlocks.FIREBOMB.defaultBlockState());
						}
					}
				}
			});
			level.getEntitiesOfClass(LivingEntity.class, box).forEach(entity -> {
				if (!entity.isInWaterOrRain()) {
					entity.igniteForSeconds(8);
				}
			});
			SLibUtils.addParticles(this, ParticleTypes.FLAME, 32, ParticleAnchor.BODY, PARTICLE_VELOCITY);
			SLibUtils.addParticles(this, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY, PARTICLE_VELOCITY);
			SLibUtils.playSound(this, ModSoundEvents.ENTITY_FIREBOMB_IMPACT, 1, Mth.nextFloat(getRandom(), 0.8F, 1.2F));
			level.levelEvent(LevelEvent.PARTICLES_INSTANT_POTION_SPLASH, blockPosition(), MobEffects.FIRE_RESISTANCE.value().getColor());
			discard();
		}
	}

	@Override
	protected double getDefaultGravity() {
		return 0.05;
	}

	@Override
	public DoubleDoubleImmutablePair calculateHorizontalHurtKnockbackDirection(LivingEntity hurtEntity, DamageSource damageSource) {
		double dX = hurtEntity.position().x() - position().x();
		double dZ = hurtEntity.position().z() - position().z();
		return DoubleDoubleImmutablePair.of(dX, dZ);
	}
}
