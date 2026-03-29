/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.client.payload.PlayBloodrushSoundPayload;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BloodrushPower extends VampireActivePower implements FormChanger {
	public BloodrushPower() {
		super(200);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 3;
	}

	@Override
	public boolean isFormActive(Player player) {
		return ModEntityComponents.BLOODRUSH.get(player).isActive(false);
	}

	@Override
	public void disable(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.BLOODRUSH.get(player).use(1);
	}

	@Override
	public void sendUseSoundPayload(ServerPlayer receiver, ServerPlayer origin, SoundEvent sound) {
		PlayBloodrushSoundPayload.send(receiver, origin);
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_BLOODRUSH_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.BLOODRUSH.get(player).use(90);
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}
}
