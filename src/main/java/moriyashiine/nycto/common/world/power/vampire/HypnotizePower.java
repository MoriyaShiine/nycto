/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.NyctoEntityTypeTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

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
		return player.isShiftKeyDown() ? NyctoSoundEvents.HYPNOTIZE_USE_INVERSE : NyctoSoundEvents.HYPNOTIZE_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(RANGE + 2), entity -> entity.distanceTo(player) <= RANGE && canUseOn(player, entity)).forEach(entity -> {
			SLibUtils.addAnchoredParticle(entity, NyctoParticleTypes.HYPNOTIZED, entity.getEyeHeight(), 0, 0);
			if (player.isShiftKeyDown()) {
				entity.removeEffect(NyctoMobEffects.HYPNOTIZED);
				if (entity.slib$isPlayer()) {
					entity.removeEffect(NyctoMobEffects.STUNNED);
				} else if (entity instanceof Mob mob) {
					forget(mob);
					NyctoEntityComponents.HYPNOTIZED.get(mob).setOwner(null);
				}
			} else {
				if (entity.slib$isPlayer()) {
					entity.addEffect(new MobEffectInstance(NyctoMobEffects.HYPNOTIZED, 100));
					entity.addEffect(new MobEffectInstance(NyctoMobEffects.STUNNED, 100));
				} else if (entity instanceof Mob mob) {
					mob.addEffect(new MobEffectInstance(NyctoMobEffects.HYPNOTIZED, 12000));
					forget(mob);
					NyctoEntityComponents.HYPNOTIZED.get(mob).setOwner(player);
				}
			}
		});
		NyctoEntityComponents.BLOOD.get(player).drain(getCost(player));
	}

	public static boolean canUseOn(Player player, LivingEntity target) {
		if (target.hasInfiniteMaterials() || !target.slib$exists() || target.is(NyctoEntityTypeTags.CANNOT_BE_HYPNOTIZED)) {
			return false;
		}
		if (target instanceof Player other && NyctoAPI.hasPower(other, NyctoPowers.HYPNOTIZE)) {
			return false;
		}
		if (target instanceof OwnableEntity ownable && ownable.getOwnerReference() != null) {
			return false;
		}
		if (target.slib$isPlayer() || NyctoEntityComponents.HYPNOTIZED.isProvidedBy(target)) {
			VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrall != null && vampiricThrall.hasOwner()) {
				return false;
			}
			if (player.isShiftKeyDown()) {
				if (target.slib$isPlayer()) {
					return true;
				}
				HypnotizedComponent hypnotized = NyctoEntityComponents.HYPNOTIZED.getNullable(target);
				return hypnotized != null && hypnotized.hasOwner();
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
