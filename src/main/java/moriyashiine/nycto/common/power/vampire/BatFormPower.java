/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.BatFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class BatFormPower extends VampireActivePower implements FormChanger {
	public BatFormPower() {
		super(200);
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		BatFormComponent batFormComponent = ModEntityComponents.BAT_FORM.get(player);
		if (batFormComponent.isEnabled()) {
			batFormComponent.toggle();
		}
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		if (entity instanceof PlayerEntity player && isFormActive(player)) {
			return 0;
		}
		return 3;
	}

	@Override
	public boolean isFormActive(PlayerEntity player) {
		return ModEntityComponents.BAT_FORM.get(player).isEnabled();
	}

	@Override
	public boolean shouldApplyCooldown(PlayerEntity player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModEntityComponents.BAT_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_BAT_FORM_OFF : ModSoundEvents.POWER_BAT_FORM_ON;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.BAT_FORM.get(player).toggle();
	}
}
