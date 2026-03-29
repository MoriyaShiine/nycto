/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

public class NightVisionPower extends ActivePower {
	public NightVisionPower() {
		super(20);
	}

	@Override
	public void onAdded(ServerPlayer player) {
		NightVisionComponent nightVisionComponent = ModEntityComponents.NIGHT_VISION.get(player);
		if (!nightVisionComponent.isEnabled()) {
			nightVisionComponent.toggle();
		}
	}

	@Override
	public void onRemoved(ServerPlayer player) {
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
	public SoundEvent getUseSound(Player player) {
		return ModEntityComponents.NIGHT_VISION.get(player).isEnabled() ? ModSoundEvents.POWER_NIGHT_VISION_OFF : ModSoundEvents.POWER_NIGHT_VISION_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.NIGHT_VISION.get(player).toggle();
	}
}
