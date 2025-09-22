/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.statuseffect;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.statuseffect.EntityRemovableStatusEffect;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class VampireWardStatusEffect extends EntityRemovableStatusEffect {
	private static final EntityAttributeModifier NON_PLAYER_ATTACK_MODIFIER = new EntityAttributeModifier(Nycto.id("vampire_ward"), -3, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier NON_PLAYER_SPEED_MODIFIER = new EntityAttributeModifier(Nycto.id("vampire_ward"), -0.3, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	public VampireWardStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void onRemoved(LivingEntity entity) {
		applyAttributes(entity, false);
	}

	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		if (entity instanceof ServerPlayerEntity player && NyctoAPI.isVampire(player)) {
			NyctoAPI.applyHealBlock(player, 100);
			NyctoUtil.disableFormChangePowers(player.getWorld(), player, null);
		}
	}

	@Override
	public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
		applyAttributes(entity, true);
		return true;
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	private static void applyAttributes(LivingEntity entity, boolean shouldRemove) {
		if (NyctoAPI.isVampire(entity)) {
			if (entity instanceof ServerPlayerEntity player) {
				NyctoAPI.getTransformation(player).applyModifiers(player, !shouldRemove);
			} else {
				SLibUtils.conditionallyApplyAttributeModifier(entity, EntityAttributes.ATTACK_DAMAGE, NON_PLAYER_ATTACK_MODIFIER, !shouldRemove);
				SLibUtils.conditionallyApplyAttributeModifier(entity, EntityAttributes.MOVEMENT_SPEED, NON_PLAYER_SPEED_MODIFIER, !shouldRemove);
			}
		}
	}
}
