/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class CarnagePower extends VampireActivePower {
	public CarnagePower() {
		super(600);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_CARNAGE_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		activate(player);
	}

	public static void activate(LivingEntity entity) {
		ModEntityComponents.CARNAGE.get(entity).use();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.CARNAGE.getCost(entity));
	}
}
