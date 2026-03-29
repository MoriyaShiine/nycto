/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
	private boolean nycto$vilePresence(boolean original) {
		return original || (mob.tickCount % 5 == 0 && !(mob instanceof NeutralMob) && VilePresenceWeakness.isAffected(mob, mob.getAttributeValue(Attributes.FOLLOW_RANGE)));
	}
}
