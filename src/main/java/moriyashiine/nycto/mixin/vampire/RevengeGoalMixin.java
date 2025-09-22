/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RevengeGoal.class)
public abstract class RevengeGoalMixin extends TrackTargetGoal {
	public RevengeGoalMixin(MobEntity mob, boolean checkVisibility) {
		super(mob, checkVisibility);
	}

	@Inject(method = "callSameTypeForRevenge", at = @At("HEAD"), cancellable = true)
	private void nycto$vampire(CallbackInfo ci) {
		if (!mob.getType().isIn(ConventionalEntityTypeTags.BOSSES) && mob.getType().isIn(EntityTypeTags.UNDEAD) && NyctoAPI.isVampire(mob.getAttacker())) {
			ci.cancel();
		}
	}
}
