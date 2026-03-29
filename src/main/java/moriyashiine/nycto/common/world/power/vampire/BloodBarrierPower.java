/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BloodBarrierPower extends VampireActivePower {
	public BloodBarrierPower() {
		super(600);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_BLOOD_BARRIER_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		activate(player);
	}

	public static void activate(LivingEntity entity) {
		ModEntityComponents.BLOOD_BARRIER.get(entity).use();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BLOOD_BARRIER.getCost(entity));
	}
}
