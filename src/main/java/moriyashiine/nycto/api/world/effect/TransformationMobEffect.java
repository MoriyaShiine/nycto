/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.effect;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class TransformationMobEffect extends MobEffect {
	private final Transformation to, from;
	private final SoundEvent sound;

	public TransformationMobEffect(MobEffectCategory category, int color, Transformation to, Transformation from, SoundEvent sound) {
		super(category, color);
		this.to = to;
		this.from = from;
		this.sound = sound;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
		return tickCount == 1;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
		if (mob instanceof ServerPlayer player && NyctoAPI.getTransformation(player) == to) {
			NyctoAPI.setTransformation(player, from);
			SLibUtils.playAnchoredSound(mob, sound);
		}
		return true;
	}
}
