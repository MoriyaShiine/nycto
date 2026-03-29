/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PanicGoal.class)
public class PanicGoalMixin {
	@Shadow
	@Final
	protected PathfinderMob mob;

	@ModifyReturnValue(method = "shouldPanic", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner();
	}
}
