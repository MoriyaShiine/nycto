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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
	@ModifyReturnValue(method = "getNearestZombifiedPiglin", at = @At("RETURN"))
	private static boolean nycto$vampiricThrall(boolean original, PiglinEntity piglin) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(piglin).isThralled();
	}

	@Definition(id = "attacker", local = @Local(type = LivingEntity.class))
	@Definition(id = "PiglinEntity", type = PiglinEntity.class)
	@Expression("attacker instanceof PiglinEntity")
	@ModifyExpressionValue(method = "onAttacked", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
	private static boolean nycto$vampiricThrallInstanceof(boolean value, @Local(argsOnly = true) PiglinEntity piglin) {
		return !ModEntityComponents.VAMPIRIC_THRALL.get(piglin).isThralled();
	}
}
