/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModEnvironmentAttributes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
	public VillagerMixin(EntityType<? extends AbstractVillager> type, Level level) {
		super(type, level);
	}

	@ModifyArg(method = "registerBrainGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;setSchedule(Lnet/minecraft/world/attribute/EnvironmentAttribute;)V", ordinal = 0))
	private EnvironmentAttribute<Activity> nycto$vampiricThrallBabySchedule(EnvironmentAttribute<Activity> schedule) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModEnvironmentAttributes.BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY;
		}
		return schedule;
	}

	@ModifyArg(method = "registerBrainGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;setSchedule(Lnet/minecraft/world/attribute/EnvironmentAttribute;)V", ordinal = 1))
	private EnvironmentAttribute<Activity> nycto$vampiricThrallSchedule(EnvironmentAttribute<Activity> schedule) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModEnvironmentAttributes.VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY;
		}
		return schedule;
	}

	@ModifyReturnValue(method = "canBreed", at = @At("RETURN"))
	private boolean nycto$vampiricThrallBreed(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}

	@ModifyReturnValue(method = "hungry", at = @At("RETURN"))
	private boolean nycto$vampiricThrallFood(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}

	@ModifyReturnValue(method = "wantsToSpawnGolem", at = @At("RETURN"))
	private boolean nycto$vampiricThrallGolem(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
