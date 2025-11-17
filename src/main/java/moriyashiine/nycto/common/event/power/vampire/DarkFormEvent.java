/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.power.vampire.DarkFormPower;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;

public class DarkFormEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		if (phase == Phase.FINAL && DarkFormPower.isDarkFormActive(victim) && !NyctoUtil.bypassesBloodVeil(source) && !NyctoUtil.haltsVampireRegeneration(source)) {
			return NyctoAPI.isBeastForm(source) ? 0.75F : 0.5F;
		}
		return 1;
	}
}
