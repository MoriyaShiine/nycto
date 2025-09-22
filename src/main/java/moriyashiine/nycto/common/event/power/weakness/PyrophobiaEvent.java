/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;

public class PyrophobiaEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		if (phase == Phase.BASE && !source.isOf(DamageTypes.LAVA)) {
			if (source.isIn(DamageTypeTags.IS_FIRE) || source.isOf(ModDamageTypes.SUN)) {
				if (victim instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.PYROPHOBIA)) {
					return 1.5F;
				}
			}
		}
		return 1;
	}
}
