/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityAttributes;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class AttributeEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		if (phase == Phase.FINAL && source.getAttacker() instanceof LivingEntity attacker) {
			if (NyctoAPI.isVampire(attacker)) {
				return getReduction(victim, ModEntityAttributes.VAMPIRE_RESISTANCE);
			} else if (NyctoAPI.isWerewolf(attacker)) {
				return getReduction(victim, ModEntityAttributes.WEREWOLF_RESISTANCE);
			}
		}
		return 1;
	}

	private static float getReduction(LivingEntity victim, RegistryEntry<EntityAttribute> attribute) {
		return (float) (1 - (victim.getAttributeValue(attribute) / ((ClampedEntityAttribute) attribute.value()).getMaxValue()));
	}
}
