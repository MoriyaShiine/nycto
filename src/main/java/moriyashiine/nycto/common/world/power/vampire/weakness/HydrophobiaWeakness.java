/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire.weakness;

import moriyashiine.nycto.api.world.power.Weakness;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HydrophobiaWeakness extends Weakness {
	public static final float MULTIPLIER = 0.5F;

	private static final AttributeModifier JUMP_STRENGTH_MODIFIER = new AttributeModifier(Nycto.id("hydrophobia_jump_strength"), -(1 - MULTIPLIER), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final AttributeModifier EMERGENCY_STEP_HEIGHT_MODIFIER = new AttributeModifier(Nycto.id("hydrophobia_emergency_step_height"), 1, AttributeModifier.Operation.ADD_VALUE);

	@Override
	public void onRemoved(ServerPlayer player) {
		player.getAttribute(Attributes.JUMP_STRENGTH).removeModifier(JUMP_STRENGTH_MODIFIER);
		player.getAttribute(Attributes.STEP_HEIGHT).removeModifier(EMERGENCY_STEP_HEIGHT_MODIFIER);
	}

	@Override
	public void tick(ServerPlayer player) {
		SLibUtils.conditionallyApplyAttributeModifier(player, Attributes.JUMP_STRENGTH, JUMP_STRENGTH_MODIFIER, player.isInWaterOrRain() && !player.isCreative());
		SLibUtils.conditionallyApplyAttributeModifier(player, Attributes.STEP_HEIGHT, EMERGENCY_STEP_HEIGHT_MODIFIER, player.isInWaterOrRain() && !player.isCreative() && player.getAttribute(Attributes.STEP_HEIGHT).getValue() < 1);
	}
}
