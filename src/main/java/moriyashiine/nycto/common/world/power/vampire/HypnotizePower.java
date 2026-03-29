/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public class HypnotizePower extends VampireActivePower {
	public static final int RANGE = 8;

	private static final int THRESHOLD = 25;

	public HypnotizePower() {
		super(200);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return entity.isShiftKeyDown() ? 3 : 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return player.isShiftKeyDown() ? ModSoundEvents.POWER_HYPNOTIZE_USE_INVERSE : ModSoundEvents.POWER_HYPNOTIZE_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(RANGE + 2), entity -> entity.distanceTo(player) <= RANGE && canUseOn(player, entity)).forEach(entity -> {
			SLibUtils.addAnchoredParticle(entity, ModParticleTypes.HYPNOTIZED, entity.getEyeHeight(), 0, 0);
			if (player.isShiftKeyDown()) {
				entity.removeEffect(ModMobEffects.HYPNOTIZED);
				if (entity.slib$isPlayer()) {
					entity.removeEffect(ModMobEffects.STUNNED);
				} else if (entity instanceof Mob mob) {
					forget(mob);
					ModEntityComponents.HYPNOTIZED.get(mob).setOwner(null);
				}
			} else {
				if (entity.slib$isPlayer()) {
					entity.addEffect(new MobEffectInstance(ModMobEffects.HYPNOTIZED, 100));
					entity.addEffect(new MobEffectInstance(ModMobEffects.STUNNED, 100));
				} else if (entity instanceof Mob mob) {
					mob.addEffect(new MobEffectInstance(ModMobEffects.HYPNOTIZED, 12000));
					forget(mob);
					ModEntityComponents.HYPNOTIZED.get(mob).setOwner(player);
				}
			}
		});
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}

	public static boolean canUseOn(Player player, LivingEntity target) {
		if (target.hasInfiniteMaterials() || !target.slib$exists() || target.is(ModEntityTypeTags.CANNOT_BE_HYPNOTIZED)) {
			return false;
		}
		if (target.slib$isPlayer() || ModEntityComponents.HYPNOTIZED.isProvidedBy(target)) {
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrallComponent != null && vampiricThrallComponent.hasOwner()) {
				return false;
			}
			if (player.isShiftKeyDown()) {
				if (target.slib$isPlayer()) {
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
	public static void forget(Mob mob) {
		ServerLevel level = (ServerLevel) mob.level();
		if (mob instanceof Villager villager) {
			villager.refreshBrain(level);
		} else {
			((Brain<Mob>) mob.getBrain()).stopAll(level, mob);
		}
		mob.setTarget(null);
		mob.setLastHurtByMob(null);
		for (WrappedGoal goal : mob.targetSelector.getAvailableGoals()) {
			if (goal.getGoal() instanceof TargetGoal) {
				goal.stop();
			}
		}
	}
}
