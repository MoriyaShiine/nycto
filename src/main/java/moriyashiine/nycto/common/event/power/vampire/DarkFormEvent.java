/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DarkFormEvent implements ModifyDamageTakenEvent {
	public static void init() {
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new DarkFormEvent());
	}

	@Override
	public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
		if (phase == Phase.FINAL && DarkFormPower.isDarkFormActive(victim) && !NyctoUtil.bypassesBloodVeil(source) && !NyctoUtil.haltsVampireRegeneration(source)) {
			int weaknesses = victim instanceof Player player ? NyctoAPI.getWeaknesses(player, NyctoPowerTags.VAMPIRE_CHOOSABLE) : 3;
			return 1 - (0.32F / 3) * weaknesses;
		}
		return 1;
	}
}
