/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoDamageTypes;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PyrophobiaEvent implements ModifyDamageTakenEvent {
	public static void init() {
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new PyrophobiaEvent());
	}

	@Override
	public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
		if (phase == Phase.BASE && !source.is(DamageTypes.LAVA)) {
			if (source.is(DamageTypeTags.IS_FIRE) || source.is(NyctoDamageTypes.SUN)) {
				if (victim instanceof Player player && NyctoAPI.hasPower(player, NyctoPowers.PYROPHOBIA)) {
					return 1.5F;
				}
			}
		}
		return 1;
	}
}
