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

public class HaemogenesisPower extends VampireActivePower {
	public HaemogenesisPower() {
		super(400);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_HAEMOGENESIS_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		startHealing(player);
	}

	public static void startHealing(LivingEntity entity) {
		ModEntityComponents.HAEMOGENESIS.get(entity).startHealing();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.HAEMOGENESIS.getCost(entity));
	}
}
