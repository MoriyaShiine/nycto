/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoAttributes;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AttributeEvent implements ModifyDamageTakenEvent {
	public static void init() {
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new AttributeEvent());
	}

	@Override
	public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
		if (phase == Phase.FINAL && source.getEntity() instanceof LivingEntity attacker) {
			if (NyctoAPI.isVampire(attacker)) {
				return getReduction(victim, NyctoAttributes.VAMPIRE_RESISTANCE);
			} else if (NyctoAPI.isWerewolf(attacker)) {
				return getReduction(victim, NyctoAttributes.WEREWOLF_RESISTANCE);
			}
		}
		return 1;
	}

	private static float getReduction(LivingEntity victim, Holder<Attribute> attribute) {
		return (float) (1 - (victim.getAttributeValue(attribute) / ((RangedAttribute) attribute.value()).getMaxValue()));
	}
}
