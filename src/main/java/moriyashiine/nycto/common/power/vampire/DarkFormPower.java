/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.DarkFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class DarkFormPower extends VampireActivePower implements FormChanger {
	public DarkFormPower() {
		super(200);
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		DarkFormComponent darkFormComponent = ModEntityComponents.DARK_FORM.get(player);
		if (darkFormComponent.isEnabled()) {
			darkFormComponent.toggle();
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
		return isDarkFormActive(player);
	}

	@Override
	public boolean shouldApplyCooldown(PlayerEntity player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModEntityComponents.DARK_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_DARK_FORM_OFF : ModSoundEvents.POWER_DARK_FORM_ON;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.DARK_FORM.get(player).toggle();
	}

	public static boolean isDarkFormActive(@Nullable Entity entity) {
		if (entity == null) {
			return false;
		}
		@Nullable DarkFormComponent darkFormComponent = ModEntityComponents.DARK_FORM.getNullable(entity);
		return darkFormComponent != null && darkFormComponent.isEnabled();
	}
}
