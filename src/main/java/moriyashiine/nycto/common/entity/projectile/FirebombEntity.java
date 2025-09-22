/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.projectile;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class FirebombEntity extends ThrownItemEntity {
	private static final ParticleVelocity PARTICLE_VELOCITY = ParticleVelocity.of(new Vec3d(0, 0.35, 0), 0.3);

	public FirebombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public FirebombEntity(World world, double x, double y, double z, ItemStack stack) {
		super(ModEntityTypes.FIREBOMB, x, y, z, world, stack);
	}

	public FirebombEntity(World world, LivingEntity owner, ItemStack stack) {
		super(ModEntityTypes.FIREBOMB, owner, world, stack);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.FIREBOMB;
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (getWorld() instanceof ServerWorld serverWorld) {
			Box box = getBoundingBox().offset(hitResult.getPos().subtract(getPos())).expand(3, 2, 3);
			BlockPos.Mutable mutable = new BlockPos.Mutable();
			for (double x = box.minX; x <= box.maxX; x++) {
				for (double y = box.minY; y <= box.maxY; y++) {
					for (double z = box.minZ; z <= box.maxZ; z++) {
						mutable.set(x, y, z);
						if (mutable.equals(getBlockPos()) || (getRandom().nextInt(3) == 0 && mutable.isWithinDistance(getPos(), 2.5))) {
							if (!serverWorld.hasRain(mutable)) {
								BlockState state = serverWorld.getBlockState(mutable);
								if (state.isReplaceable() && state.getFluidState().isEmpty() && ModBlocks.FIREBOMB.getDefaultState().canPlaceAt(serverWorld, mutable)) {
									serverWorld.setBlockState(mutable, ModBlocks.FIREBOMB.getDefaultState());
								}
							}
						}
					}
				}
			}
			getWorld().getNonSpectatingEntities(LivingEntity.class, box).forEach(foundEntity -> {
				if (!foundEntity.isTouchingWaterOrRain()) {
					foundEntity.setOnFireFor(8);
				}
			});
			SLibUtils.addParticles(this, ParticleTypes.FLAME, 32, ParticleAnchor.BODY, PARTICLE_VELOCITY);
			SLibUtils.addParticles(this, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY, PARTICLE_VELOCITY);
			SLibUtils.playSound(this, ModSoundEvents.ENTITY_FIREBOMB_IMPACT, 1, MathHelper.nextFloat(getRandom(), 0.8F, 1.2F));
			serverWorld.syncWorldEvent(WorldEvents.INSTANT_SPLASH_POTION_SPLASHED, getBlockPos(), StatusEffects.FIRE_RESISTANCE.value().getColor());
			discard();
		}
	}

	@Override
	protected double getGravity() {
		return 0.05;
	}

	@Override
	public DoubleDoubleImmutablePair getKnockback(LivingEntity target, DamageSource source) {
		double dX = target.getPos().getX() - this.getPos().getX();
		double dZ = target.getPos().getZ() - this.getPos().getZ();
		return DoubleDoubleImmutablePair.of(dX, dZ);
	}
}
