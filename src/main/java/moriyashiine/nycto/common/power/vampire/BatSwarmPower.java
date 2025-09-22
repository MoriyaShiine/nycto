/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.component.world.power.BatSwarmComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModWorldComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class BatSwarmPower extends VampireActivePower {
	public BatSwarmPower() {
		super(BatSwarmComponent.BatSwarm.MAX_AGE);
	}

	@Override
	protected int getBaseCost(LivingEntity player) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_BAT_SWARM_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		spawnSwarm(world, player);
	}

	public static void spawnSwarm(World world, LivingEntity entity) {
		BatSwarmComponent batSwarmComponent = ModWorldComponents.BAT_SWARM.get(world);
		batSwarmComponent.addBatSwarm(entity);
		batSwarmComponent.sync();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BAT_SWARM.getCost(entity));
	}
}
