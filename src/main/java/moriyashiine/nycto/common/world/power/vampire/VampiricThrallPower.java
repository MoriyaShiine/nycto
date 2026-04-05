/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

public class VampiricThrallPower extends VampireActivePower {
	private static final int THRESHOLD = 15;

	public VampiricThrallPower() {
		super(200);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 20;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_HYPNOTIZE_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		if (ProjectileUtil.getHitResultOnViewVector(player, entity -> entity instanceof Mob, player.entityInteractionRange()) instanceof EntityHitResult hitResult && hitResult.getEntity() instanceof Mob mob && canBeThralled(player, mob)) {
			SLibUtils.addAnchoredParticle(mob, ModParticleTypes.THRALLED, mob.getEyeHeight(), 0, 0);
			SLibUtils.playSound(mob, ModSoundEvents.POWER_VAMPIRIC_THRALL_CONVERT);
			if (NyctoUtil.isVillager(mob)) {
				mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
			}
			if (mob instanceof AbstractPiglin piglin) {
				piglin.setImmuneToZombification(true);
			}
			mob.setPersistenceRequired();
			mob.removeEffect(ModMobEffects.HYPNOTIZED);
			level.getEntitiesOfClass(Mob.class, new AABB(mob.blockPosition()).inflate(32), entity -> ModEntityComponents.VAMPIRIC_THRALL.get(entity).isOwner(player)).forEach(HypnotizePower::forget);
			setThrall(mob, player);
		}
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}

	public static void setThrall(Mob mob, @Nullable Player owner) {
		VampireTransformation.setComponents(mob, owner != null);
		ModEntityComponents.VAMPIRIC_THRALL.get(mob).reset(owner);
		HypnotizePower.forget(mob);
		if (mob instanceof Raider raider) {
			Raid raid = raider.getCurrentRaid();
			if (raid != null) {
				if (raider.isPatrolLeader()) {
					raid.removeLeader(raider.getWave());
				}
				raid.removeFromRaid((ServerLevel) raider.level(), raider, true);
			}
		}
	}

	public static boolean canBeThralled(Player player, Mob target) {
		if (target.slib$exists() && target.is(ModEntityTypeTags.CAN_BE_THRALLED)) {
			if (target instanceof OwnableEntity ownable) {
				if (!isTamed(target)) {
					return false;
				}
				if (!player.level().isClientSide()) {
					if (ownable.getOwnerReference() == null || !ownable.getOwnerReference().matches(player)) {
						return false;
					}
				}
			}
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrallComponent != null && !vampiricThrallComponent.hasOwner()) {
				return target.getHealth() <= THRESHOLD || target instanceof OwnableEntity;
			}
		}
		return false;
	}

	private static boolean isTamed(Mob target) {
		if (target instanceof TamableAnimal tameable) {
			return tameable.isTame();
		}
		if (target instanceof AbstractHorse horse) {
			return horse.isTamed();
		}
		return false;
	}
}
