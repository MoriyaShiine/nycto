/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.DarkFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public class DarkFormPower extends VampireActivePower implements FormChanger {
	public DarkFormPower() {
		super(200);
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		DarkFormComponent darkFormComponent = ModEntityComponents.DARK_FORM.get(player);
		if (darkFormComponent.isEnabled()) {
			darkFormComponent.toggle();
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
		return isDarkFormActive(player);
	}

	@Override
	public boolean shouldApplyCooldown(Player player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModEntityComponents.DARK_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_DARK_FORM_OFF : ModSoundEvents.POWER_DARK_FORM_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
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
