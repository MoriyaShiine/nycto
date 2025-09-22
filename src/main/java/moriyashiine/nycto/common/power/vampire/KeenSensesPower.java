/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.KeenSensesComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class KeenSensesPower extends VampireActivePower {
	public KeenSensesPower() {
		super(20);
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		KeenSensesComponent keenSensesComponent = ModEntityComponents.KEEN_SENSES.get(player);
		if (keenSensesComponent.isEnabled()) {
			keenSensesComponent.toggle();
		}
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		if (entity instanceof PlayerEntity player && ModEntityComponents.KEEN_SENSES.get(player).isEnabled()) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean shouldBroadcastUseSound() {
		return false;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModEntityComponents.KEEN_SENSES.get(player).isEnabled() ? ModSoundEvents.POWER_KEEN_SENSES_OFF : ModSoundEvents.POWER_KEEN_SENSES_ON;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		ModEntityComponents.KEEN_SENSES.get(player).toggle();
	}
}
