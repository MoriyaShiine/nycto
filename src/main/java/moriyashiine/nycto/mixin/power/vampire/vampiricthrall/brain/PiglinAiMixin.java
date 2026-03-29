/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
	@ModifyReturnValue(method = "isNearZombified", at = @At("RETURN"))
	private static boolean nycto$vampiricThrall(boolean original, Piglin body) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(body).hasOwner();
	}

	@Definition(id = "attacker", local = @Local(type = LivingEntity.class))
	@Definition(id = "Piglin", type = Piglin.class)
	@Expression("attacker instanceof Piglin")
	@ModifyExpressionValue(method = "wasHurtBy", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
	private static boolean nycto$vampiricThrallInstanceof(boolean value, @Local(argsOnly = true) Piglin body) {
		return !ModEntityComponents.VAMPIRIC_THRALL.get(body).hasOwner();
	}
}
