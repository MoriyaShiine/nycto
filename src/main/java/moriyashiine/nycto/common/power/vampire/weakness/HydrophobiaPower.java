/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire.weakness;

import moriyashiine.nycto.api.power.NegativePower;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class HydrophobiaPower extends NegativePower {
	public static final float MULTIPLIER = 0.5F;

	private static final EntityAttributeModifier JUMP_STRENGTH_MODIFIER = new EntityAttributeModifier(Nycto.id("hydrophobia_jump_strength"), -(1 - MULTIPLIER), EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final EntityAttributeModifier EMERGENCY_STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(Nycto.id("hydrophobia_emergency_step_height"), 1, EntityAttributeModifier.Operation.ADD_VALUE);

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH).removeModifier(JUMP_STRENGTH_MODIFIER);
		player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).removeModifier(EMERGENCY_STEP_HEIGHT_MODIFIER);
	}

	@Override
	public void tick(ServerPlayerEntity player) {
		SLibUtils.conditionallyApplyAttributeModifier(player, EntityAttributes.JUMP_STRENGTH, JUMP_STRENGTH_MODIFIER, player.isTouchingWaterOrRain() && !player.isCreative());
		SLibUtils.conditionallyApplyAttributeModifier(player, EntityAttributes.STEP_HEIGHT, EMERGENCY_STEP_HEIGHT_MODIFIER, player.isTouchingWaterOrRain() && !player.isCreative() && player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).getValue() < 1);
	}
}
