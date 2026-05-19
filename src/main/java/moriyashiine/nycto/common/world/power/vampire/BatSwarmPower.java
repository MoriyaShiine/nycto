/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.level.power.BatSwarmComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoLevelComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
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
		return NyctoSoundEvents.BAT_SWARM_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		spawnSwarm(level, player);
	}

	public static void spawnSwarm(Level level, LivingEntity entity) {
		BatSwarmComponent batSwarm = NyctoLevelComponents.BAT_SWARM.get(level);
		batSwarm.addBatSwarm(entity);
		batSwarm.sync();
		NyctoEntityComponents.BLOOD.get(entity).drain(NyctoPowers.BAT_SWARM.getCost(entity));
	}
}
