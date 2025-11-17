/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.world.power.BatSwarmComponent;
import moriyashiine.nycto.common.init.ModWorldComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class BatSwarmEvent implements ServerLivingEntityEvents.AfterDamage {
	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (source.getAttacker() instanceof LivingEntity living) {
			addTarget(living, entity);
		} else if (source.getAttacker() instanceof LivingEntity attacker) {
			addTarget(entity, attacker);
		}
	}

	private static void addTarget(LivingEntity player, LivingEntity target) {
		BatSwarmComponent batSwarmComponent = ModWorldComponents.BAT_SWARM.get(player.getEntityWorld());
		batSwarmComponent.addTarget(player, target);
		batSwarmComponent.sync();
	}
}
