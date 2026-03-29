/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.KeenSensesComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KeenSensesPower extends VampireActivePower {
	public KeenSensesPower() {
		super(20);
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		KeenSensesComponent keenSensesComponent = ModEntityComponents.KEEN_SENSES.get(player);
		if (keenSensesComponent.isEnabled()) {
			keenSensesComponent.toggle();
		}
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		if (entity instanceof Player player && ModEntityComponents.KEEN_SENSES.get(player).isEnabled()) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean shouldBroadcastUseSound() {
		return false;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModEntityComponents.KEEN_SENSES.get(player).isEnabled() ? ModSoundEvents.POWER_KEEN_SENSES_OFF : ModSoundEvents.POWER_KEEN_SENSES_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		ModEntityComponents.KEEN_SENSES.get(player).toggle();
	}
}
