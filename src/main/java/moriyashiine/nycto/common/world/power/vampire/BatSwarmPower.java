/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.level.power.BatSwarmComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModLevelComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BatSwarmPower extends VampireActivePower {
	public BatSwarmPower() {
		super(BatSwarmComponent.BatSwarm.MAX_AGE);
	}

	@Override
	protected int getBaseCost(LivingEntity player) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_BAT_SWARM_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		spawnSwarm(level, player);
	}

	public static void spawnSwarm(Level level, LivingEntity entity) {
		BatSwarmComponent batSwarmComponent = ModLevelComponents.BAT_SWARM.get(level);
		batSwarmComponent.addBatSwarm(entity);
		batSwarmComponent.sync();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BAT_SWARM.getCost(entity));
	}
}
