/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RevengeGoal.class)
public abstract class RevengeGoalMixin extends TrackTargetGoal {
	public RevengeGoalMixin(MobEntity mob, boolean checkVisibility) {
		super(mob, checkVisibility);
	}

	@ModifyExpressionValue(method = "canStart", at = @At(value = "INVOKE", target = "Ljava/lang/Class;isAssignableFrom(Ljava/lang/Class;)Z"))
	private boolean nycto$vampiricThrall(boolean original, @Local LivingEntity attacker) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && !ModEntityComponents.VAMPIRIC_THRALL.get(attacker).hasOwner();
	}

	@WrapWithCondition(method = "callSameTypeForRevenge", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/RevengeGoal;setMobEntityTarget(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/entity/LivingEntity;)V"))
	private boolean nycto$vampiricThrall(RevengeGoal instance, MobEntity mob, LivingEntity target) {
		return !ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner();
	}
}
