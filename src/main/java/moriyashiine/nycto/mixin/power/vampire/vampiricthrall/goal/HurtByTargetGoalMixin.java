/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HurtByTargetGoal.class)
public abstract class HurtByTargetGoalMixin extends TargetGoal {
	public HurtByTargetGoalMixin(Mob mob, boolean mustSee) {
		super(mob, mustSee);
	}

	@ModifyExpressionValue(method = "canUse", at = @At(value = "INVOKE", target = "Ljava/lang/Class;isAssignableFrom(Ljava/lang/Class;)Z"))
	private boolean nycto$vampiricThrall(boolean original, @Local(name = "lastHurtByMob") LivingEntity lastHurtByMob) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && !ModEntityComponents.VAMPIRIC_THRALL.get(lastHurtByMob).hasOwner();
	}

	@WrapWithCondition(method = "alertOthers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/target/HurtByTargetGoal;alertOther(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/entity/LivingEntity;)V"))
	private boolean nycto$vampiricThrall(HurtByTargetGoal instance, Mob other, LivingEntity hurtByMob) {
		return !ModEntityComponents.VAMPIRIC_THRALL.get(other).hasOwner();
	}
}
