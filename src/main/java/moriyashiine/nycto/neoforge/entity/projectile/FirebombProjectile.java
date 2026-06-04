/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.projectile;

import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

public final class FirebombProjectile extends ThrowableItemProjectile {
	public FirebombProjectile(EntityType<? extends FirebombProjectile> type, Level level) {
		super(type, level);
	}

	public FirebombProjectile(Level level, double x, double y, double z, ItemStack stack) {
		super(NyctoEntityTypes.FIREBOMB.get(), x, y, z, level);
		setItem(stack.copyWithCount(1));
	}

	public FirebombProjectile(Level level, LivingEntity owner, ItemStack stack) {
		super(NyctoEntityTypes.FIREBOMB.get(), owner, level);
		setItem(stack.copyWithCount(1));
	}

	@Override
	protected Item getDefaultItem() {
		return NyctoHunterContent.FIREBOMB.get();
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (level() instanceof ServerLevel serverLevel) {
			AABB box = getBoundingBox().move(hitResult.getLocation().subtract(position())).inflate(3, 2, 3);
			for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
				if ((pos.equals(blockPosition()) || random.nextInt(3) == 0 && pos.closerToCenterThan(position(), 2.5)) && !serverLevel.isRainingAt(pos)) {
					BlockState state = serverLevel.getBlockState(pos);
					BlockState firebomb = NyctoBlocks.FIREBOMB.get().defaultBlockState();
					if (state.canBeReplaced() && state.getFluidState().isEmpty() && firebomb.canSurvive(serverLevel, pos)) {
						serverLevel.setBlockAndUpdate(pos, firebomb);
					}
				}
			}
			NyctoHunterContent.applyFirebombImpact(serverLevel, hitResult.getLocation(), getOwner());
			serverLevel.sendParticles(ParticleTypes.FLAME, getX(), getY() + 0.4, getZ(), 32, 0.35, 0.35, 0.35, 0.03);
			serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY() + 0.4, getZ(), 8, 0.35, 0.35, 0.35, 0.02);
			serverLevel.playSound(null, blockPosition(), NyctoSoundEvents.FIREBOMB_IMPACT.get(), getSoundSource(), 1, Mth.nextFloat(random, 0.8F, 1.2F));
			serverLevel.levelEvent(LevelEvent.PARTICLES_INSTANT_POTION_SPLASH, blockPosition(), MobEffects.FIRE_RESISTANCE.value().getColor());
			discard();
		}
	}

	@Override
	protected double getDefaultGravity() {
		return 0.05;
	}
}
