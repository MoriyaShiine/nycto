/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.common.component.entity.power.vampire.BatFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibRegistries;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;

public class BatFormPower extends VampireActivePower implements FormChanger {
	public BatFormPower() {
		super(200);
		SLibRegistries.registerModelReplacementCopyFunction((player, replacement) -> {
			if (replacement instanceof Bat bat && isFormActive(player)) {
				bat.setResting(!player.swinging && !player.hasMovedHorizontallyRecently() && !SLibUtils.isSufficientlyHigh(player, -(player.getBbHeight() + 0.01)));
			}
		});
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		BatFormComponent batFormComponent = ModEntityComponents.BAT_FORM.get(player);
		if (batFormComponent.isEnabled()) {
			batFormComponent.toggle();
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
		return ModEntityComponents.BAT_FORM.get(player).isEnabled();
	}

	@Override
	public boolean shouldApplyCooldown(Player player) {
		return isFormActive(player);
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModEntityComponents.BAT_FORM.get(player).isEnabled() ? ModSoundEvents.POWER_BAT_FORM_OFF : ModSoundEvents.POWER_BAT_FORM_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.BAT_FORM.get(player).toggle();
	}
}
