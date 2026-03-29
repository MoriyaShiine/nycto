/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StartAttacking.class)
public class StartAttackingMixin {
	@ModifyExpressionValue(method = "lambda$create$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z"))
	private static boolean nycto$vampiricThrall(boolean original, @Local(argsOnly = true) Mob body) {
		if (original && !NyctoUtil.isSurvivalNullable(body.getLastHurtByMob()) && ModEntityComponents.VAMPIRIC_THRALL.get(body).hasOwner()) {
			return false;
		}
		return original;
	}
}
