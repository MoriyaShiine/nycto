/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	@Unique
	private static final Schedule VAMPIRE_BABY = register("vampire_baby")
			.withActivity(12000, Activity.IDLE)
			.withActivity(15000, Activity.PLAY)
			.withActivity(18000, Activity.IDLE)
			.withActivity(22000, Activity.PLAY)
			.withActivity(10, Activity.REST)
			.build();
	@Unique
	private static final Schedule VAMPIRE_DEFAULT = register("vampire_default")
			.withActivity(12000, Activity.IDLE)
			.withActivity(14000, Activity.WORK)
			.withActivity(21000, Activity.MEET)
			.withActivity(23000, Activity.IDLE)
			.withActivity(10, Activity.REST)
			.build();

	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyArg(method = "initBrain", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/brain/Brain;setSchedule(Lnet/minecraft/entity/ai/brain/Schedule;)V"))
	private Schedule nycto$vampiricThrall(Schedule schedule) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled()) {
			return isBaby() ? VAMPIRE_BABY : VAMPIRE_DEFAULT;
		}
		return schedule;
	}

	@ModifyReturnValue(method = "isReadyToBreed", at = @At("RETURN"))
	private boolean nycto$vampiricThrallBreed(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled();
	}

	@ModifyReturnValue(method = "canEatFood", at = @At("RETURN"))
	private boolean nycto$vampiricThrallFood(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled();
	}

	@ModifyReturnValue(method = "canSummonGolem", at = @At("RETURN"))
	private boolean nycto$vampiricThrallGolem(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled();
	}

	@Unique
	private static ScheduleBuilder register(String id) {
		Schedule schedule = Registry.register(Registries.SCHEDULE, Nycto.id(id), new Schedule());
		return new ScheduleBuilder(schedule);
	}
}
