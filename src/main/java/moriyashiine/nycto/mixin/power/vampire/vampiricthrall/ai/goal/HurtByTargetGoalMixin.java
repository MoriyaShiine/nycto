/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.ai.goal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
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
		if (original) {
			if (NyctoEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner()) {
				return false;
			}
			VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(lastHurtByMob);
			if (vampiricThrall != null && vampiricThrall.hasOwner()) {
				return false;
			}
		}
		return original;
	}

	@WrapWithCondition(method = "alertOthers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/target/HurtByTargetGoal;alertOther(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/entity/LivingEntity;)V"))
	private boolean nycto$vampiricThrall(HurtByTargetGoal instance, Mob other, LivingEntity hurtByMob) {
		return !NyctoEntityComponents.VAMPIRIC_THRALL.get(other).hasOwner();
	}
}
