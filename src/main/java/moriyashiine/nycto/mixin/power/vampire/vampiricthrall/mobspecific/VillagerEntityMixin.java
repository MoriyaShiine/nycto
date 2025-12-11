/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModEnvironmentAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyArg(method = "initBrain", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/brain/Brain;setSchedule(Lnet/minecraft/world/attribute/EnvironmentAttribute;)V", ordinal = 0))
	private EnvironmentAttribute<Activity> nycto$vampiricThrallBabySchedule(EnvironmentAttribute<Activity> schedule) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModEnvironmentAttributes.BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY;
		}
		return schedule;
	}

	@ModifyArg(method = "initBrain", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/brain/Brain;setSchedule(Lnet/minecraft/world/attribute/EnvironmentAttribute;)V", ordinal = 1))
	private EnvironmentAttribute<Activity> nycto$vampiricThrallSchedule(EnvironmentAttribute<Activity> schedule) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModEnvironmentAttributes.VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY;
		}
		return schedule;
	}

	@ModifyReturnValue(method = "isReadyToBreed", at = @At("RETURN"))
	private boolean nycto$vampiricThrallBreed(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}

	@ModifyReturnValue(method = "canEatFood", at = @At("RETURN"))
	private boolean nycto$vampiricThrallFood(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}

	@ModifyReturnValue(method = "canSummonGolem", at = @At("RETURN"))
	private boolean nycto$vampiricThrallGolem(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
