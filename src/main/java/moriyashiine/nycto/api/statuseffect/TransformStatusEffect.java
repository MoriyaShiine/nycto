/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.statuseffect;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;

public class TransformStatusEffect extends StatusEffect {
	private final Transformation to, from;
	private final SoundEvent soundEvent;

	public TransformStatusEffect(StatusEffectCategory category, int color, Transformation to, Transformation from, SoundEvent soundEvent) {
		super(category, color);
		this.to = to;
		this.from = from;
		this.soundEvent = soundEvent;
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return duration == 1;
	}

	@Override
	public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
		if (entity instanceof ServerPlayerEntity player && NyctoAPI.getTransformation(player) == to) {
			NyctoAPI.setTransformation(player, from);
			SLibUtils.playAnchoredSound(entity, soundEvent);
		}
		return true;
	}
}
