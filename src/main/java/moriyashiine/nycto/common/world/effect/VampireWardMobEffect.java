/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.effect;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.effect.EntityRemovableMobEffect;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class VampireWardMobEffect extends EntityRemovableMobEffect {
	private static final AttributeModifier NON_PLAYER_ATTACK_MODIFIER = new AttributeModifier(Nycto.id("vampire_ward"), -3, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier NON_PLAYER_SPEED_MODIFIER = new AttributeModifier(Nycto.id("vampire_ward"), -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	public VampireWardMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void onEffectRemoved(LivingEntity mob) {
		applyAttributes(mob, false);
	}

	@Override
	public void onEffectStarted(LivingEntity mob, int amplifier) {
		if (mob instanceof ServerPlayer player && NyctoAPI.isVampire(player)) {
			NyctoAPI.applyHealBlock(player, 100);
			NyctoUtil.disableFormChangePowers(player.level(), player, null);
		}
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
		applyAttributes(mob, true);
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
		return true;
	}

	private static void applyAttributes(LivingEntity entity, boolean shouldRemove) {
		if (NyctoAPI.isVampire(entity)) {
			if (entity instanceof ServerPlayer player) {
				NyctoAPI.getTransformation(player).applyModifiers(player, !shouldRemove);
			} else {
				SLibUtils.conditionallyApplyAttributeModifier(entity, Attributes.ATTACK_DAMAGE, NON_PLAYER_ATTACK_MODIFIER, !shouldRemove);
				SLibUtils.conditionallyApplyAttributeModifier(entity, Attributes.MOVEMENT_SPEED, NON_PLAYER_SPEED_MODIFIER, !shouldRemove);
			}
		}
	}
}
