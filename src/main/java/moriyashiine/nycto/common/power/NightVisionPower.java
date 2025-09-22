/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power;

import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class NightVisionPower extends ActivePower {
	public NightVisionPower() {
		super(20);
	}

	@Override
	public void onAdded(ServerPlayerEntity player) {
		NightVisionComponent nightVisionComponent = ModEntityComponents.NIGHT_VISION.get(player);
		if (!nightVisionComponent.isEnabled()) {
			nightVisionComponent.toggle();
		}
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		NightVisionComponent nightVisionComponent = ModEntityComponents.NIGHT_VISION.get(player);
		if (nightVisionComponent.isEnabled()) {
			nightVisionComponent.toggle();
		}
	}

	@Override
	public boolean shouldBroadcastUseSound() {
		return false;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModEntityComponents.NIGHT_VISION.get(player).isEnabled() ? ModSoundEvents.POWER_NIGHT_VISION_OFF : ModSoundEvents.POWER_NIGHT_VISION_ON;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.NIGHT_VISION.get(player).toggle();
	}
}
