/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.entity.projectile.BloodFlechetteEntity;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class BloodFlechettesPower extends VampireActivePower {
	public BloodFlechettesPower() {
		super(40);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 3;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_BLOOD_FLECHETTES_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		spawnProjectiles(world, player);
	}

	public static void spawnProjectiles(World world, LivingEntity entity) {
		for (int i = 0; i < entity.getRandom().nextBetween(6, 8); i++) {
			BloodFlechetteEntity bloodFlechette = new BloodFlechetteEntity(entity, world);
			bloodFlechette.setVelocity(entity, entity.getPitch(), entity.getHeadYaw(), 0, 1, i == 0 ? 0 : 24);
			world.spawnEntity(bloodFlechette);
		}
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BLOOD_FLECHETTES.getCost(entity));
	}
}
