/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

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
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_HYPNOTIZE_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		if (ProjectileUtil.getCollision(player, entity -> entity instanceof MobEntity, player.getEntityInteractionRange()) instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof MobEntity mob && canBeThralled(player, mob)) {
			SLibUtils.addAnchoredParticle(mob, ModParticleTypes.THRALLED, mob.getStandingEyeHeight(), 0, 0);
			SLibUtils.playSound(mob, ModSoundEvents.POWER_VAMPIRIC_THRALL_CONVERT);
			if (NyctoUtil.isVillager(mob)) {
				mob.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
			}
			if (mob instanceof AbstractPiglinEntity piglin) {
				piglin.setImmuneToZombification(true);
			}
			mob.setPersistent();
			mob.removeStatusEffect(ModStatusEffects.HYPNOTIZED);
			world.getEntitiesByClass(MobEntity.class, new Box(mob.getBlockPos()).expand(32), entity -> ModEntityComponents.VAMPIRIC_THRALL.get(entity).isOwner(player)).forEach(HypnotizePower::forget);
			VampireTransformation.setComponents(mob, true);
			VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.get(mob);
			vampiricThrallComponent.setOwner(player);
			vampiricThrallComponent.sync();
			HypnotizePower.forget(mob);
		}
		ModEntityComponents.BLOOD.get(player).drain(getCost(player));
	}

	public static boolean canBeThralled(PlayerEntity player, MobEntity target) {
		if (target.isPartOfGame() && target.getType().isIn(ModEntityTypeTags.CAN_BE_THRALLED)) {
			if (target instanceof Tameable tameable) {
				if (!isTamed(target)) {
					return false;
				}
				if (!player.getWorld().isClient) {
					if (tameable.getOwnerReference() == null || !tameable.getOwnerReference().uuidEquals(player)) {
						return false;
					}
				}
			}
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrallComponent != null && !vampiricThrallComponent.isThralled()) {
				return target.getHealth() <= THRESHOLD;
			}
		}
		return false;
	}

	private static boolean isTamed(MobEntity target) {
		if (target instanceof TameableEntity tameable) {
			return tameable.isTamed();
		}
		if (target instanceof AbstractHorseEntity horse) {
			return horse.isTame();
		}
		return false;
	}
}
