/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class MistFormPower extends VampireActivePower implements FormChanger {
	public MistFormPower() {
		super(200);
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
		if (mistFormComponent.isEnabled()) {
			mistFormComponent.toggle();
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
		return ModEntityComponents.MIST_FORM.get(player).isEnabled();
	}

	@Override
	public boolean shouldApplyCooldown(PlayerEntity player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModEntityComponents.MIST_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_MIST_FORM_OFF : ModSoundEvents.POWER_MIST_FORM_ON;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.MIST_FORM.get(player).toggle();
	}
}
