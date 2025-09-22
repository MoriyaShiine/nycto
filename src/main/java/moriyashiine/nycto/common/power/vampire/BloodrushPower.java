/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.client.payload.PlayBloodrushSoundPayload;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class BloodrushPower extends VampireActivePower implements FormChanger {
	public BloodrushPower() {
		super(200);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 3;
	}

	@Override
	public boolean isFormActive(PlayerEntity player) {
		return ModEntityComponents.BLOODRUSH.get(player).isActive(false);
	}

	@Override
	public void disable(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.BLOODRUSH.get(player).use(1);
	}

	@Override
	public void sendUseSoundPacket(ServerPlayerEntity origin, SoundEvent soundEvent, ServerPlayerEntity target) {
		PlayBloodrushSoundPayload.send(target, origin);
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_BLOODRUSH_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.BLOODRUSH.get(player).use(90);
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}
}
