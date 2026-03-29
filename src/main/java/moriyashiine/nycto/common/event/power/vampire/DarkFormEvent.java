/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DarkFormEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
		if (phase == Phase.FINAL && DarkFormPower.isDarkFormActive(victim) && !NyctoUtil.bypassesBloodVeil(source) && !NyctoUtil.haltsVampireRegeneration(source)) {
			return NyctoAPI.isBeastForm(source) ? 0.75F : 0.5F;
		}
		return 1;
	}
}
