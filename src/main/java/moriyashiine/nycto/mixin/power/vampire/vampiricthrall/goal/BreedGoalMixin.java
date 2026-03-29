/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BreedGoal.class)
public class BreedGoalMixin {
	@Shadow
	@Final
	protected Animal animal;

	@Inject(method = {"canUse", "canContinueToUse"}, at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(animal).hasOwner()) {
			cir.setReturnValue(false);
		}
	}
}
