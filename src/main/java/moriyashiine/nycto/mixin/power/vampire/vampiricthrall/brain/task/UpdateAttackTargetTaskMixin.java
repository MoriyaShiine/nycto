/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(UpdateAttackTargetTask.class)
public class UpdateAttackTargetTaskMixin {
	@ModifyExpressionValue(method = "method_47123", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;canTarget(Lnet/minecraft/entity/LivingEntity;)Z"))
	private static boolean nycto$vampiricThrall(boolean original, @Local(argsOnly = true) MobEntity mob) {
		if (original && !NyctoUtil.isSurvival(mob.getAttacker()) && ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner()) {
			return false;
		}
		return original;
	}
}
