/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

public class HypnotizePower extends VampireActivePower {
	public static final int RANGE = 8;

	private static final int THRESHOLD = 25;

	public HypnotizePower() {
		super(200);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return entity.isSneaking() ? 3 : 10;
	}

	@Override
	public SoundEvent getUseSound(PlayerEntity player) {
		return player.isSneaking() ? ModSoundEvents.POWER_HYPNOTIZE_USE_INVERSE : ModSoundEvents.POWER_HYPNOTIZE_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		world.getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(RANGE + 2), entity -> entity.distanceTo(player) <= RANGE && canUseOn(player, entity)).forEach(entity -> {
			SLibUtils.addAnchoredParticle(entity, ModParticleTypes.HYPNOTIZED, entity.getStandingEyeHeight(), 0, 0);
			if (player.isSneaking()) {
				entity.removeStatusEffect(ModStatusEffects.HYPNOTIZED);
				if (entity.isPlayer()) {
					entity.removeStatusEffect(ModStatusEffects.STUNNED);
				} else if (entity instanceof MobEntity mob) {
					forget(mob);
					ModEntityComponents.HYPNOTIZED.get(mob).setOwner(null);
				}
			} else {
				if (entity.isPlayer()) {
					entity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.HYPNOTIZED, 100));
					entity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STUNNED, 100));
				} else if (entity instanceof MobEntity mob) {
					mob.addStatusEffect(new StatusEffectInstance(ModStatusEffects.HYPNOTIZED, 12000));
					forget(mob);
					ModEntityComponents.HYPNOTIZED.get(mob).setOwner(player);
				}
			}
		});
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}

	public static boolean canUseOn(PlayerEntity player, LivingEntity target) {
		if (target.isInCreativeMode() || !target.isPartOfGame() || target.getType().isIn(ModEntityTypeTags.CANNOT_BE_HYPNOTIZED)) {
			return false;
		}
		if (target.isPlayer() || ModEntityComponents.HYPNOTIZED.isProvidedBy(target)) {
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrallComponent != null && vampiricThrallComponent.isThralled()) {
				return false;
			}
			if (player.isSneaking()) {
				if (target.isPlayer()) {
					return true;
				}
				@Nullable HypnotizedComponent hypnotizedComponent = ModEntityComponents.HYPNOTIZED.getNullable(target);
				return hypnotizedComponent != null && hypnotizedComponent.hasOwner();
			}
			return target.getHealth() <= THRESHOLD && SLibUtils.shouldHurt(player, target);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void forget(MobEntity mob) {
		ServerWorld serverWorld = (ServerWorld) mob.getEntityWorld();
		if (mob instanceof VillagerEntity villager) {
			villager.reinitializeBrain(serverWorld);
		} else {
			((Brain<MobEntity>) mob.getBrain()).stopAllTasks(serverWorld, mob);
		}
		mob.setTarget(null);
		mob.setAttacker(null);
		for (PrioritizedGoal goal : mob.targetSelector.getGoals()) {
			if (goal.getGoal() instanceof TrackTargetGoal) {
				goal.stop();
			}
		}
	}
}
