/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.level.power.BatSwarmComponent;
import moriyashiine.nycto.common.init.ModLevelComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class BatSwarmEvent implements ServerLivingEntityEvents.AfterDamage {
	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (source.getEntity() instanceof LivingEntity living) {
			addTarget(living, entity);
		} else if (source.getEntity() instanceof LivingEntity attacker) {
			addTarget(entity, attacker);
		}
	}

	private static void addTarget(LivingEntity player, LivingEntity target) {
		BatSwarmComponent batSwarmComponent = ModLevelComponents.BAT_SWARM.get(player.level());
		batSwarmComponent.addTarget(player, target);
		batSwarmComponent.sync();
	}
}
