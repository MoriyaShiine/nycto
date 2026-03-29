/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MistFormPower extends VampireActivePower implements FormChanger {
	public MistFormPower() {
		super(200);
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
		if (mistFormComponent.isEnabled()) {
			mistFormComponent.toggle();
		}
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		if (entity instanceof Player player && isFormActive(player)) {
			return 0;
		}
		return 3;
	}

	@Override
	public boolean isFormActive(Player player) {
		return ModEntityComponents.MIST_FORM.get(player).isEnabled();
	}

	@Override
	public boolean shouldApplyCooldown(Player player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModEntityComponents.MIST_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_MIST_FORM_OFF : ModSoundEvents.POWER_MIST_FORM_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.MIST_FORM.get(player).toggle();
	}
}
