/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.hasowner;

import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.ai.goal.thrall.ThralledFleeSunGoal;
import moriyashiine.nycto.common.world.entity.ai.goal.thrall.ThralledFollowOwnerGoal;
import moriyashiine.nycto.common.world.entity.ai.goal.thrall.ThralledHurtByTargetGoal;
import moriyashiine.nycto.common.world.entity.ai.goal.thrall.ThralledMeleeAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {
	@Shadow
	@Final
	protected GoalSelector goalSelector;

	@Shadow
	@Final
	public GoalSelector targetSelector;

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;registerGoals()V", shift = At.Shift.AFTER))
	private void nycto$hasOwner(EntityType<?> type, Level level, CallbackInfo ci) {
		if ((Object) this instanceof PathfinderMob mob) {
			goalSelector.addGoal(0, new ThralledFleeSunGoal(mob, 1));
			goalSelector.addGoal(1, new ThralledFollowOwnerGoal(mob, 1));
			if (NyctoUtil.isVillager(mob)) {
				goalSelector.addGoal(9, new ThralledMeleeAttackGoal(mob, 1, false));
				targetSelector.addGoal(1, new ThralledHurtByTargetGoal(mob));
			}
		}
	}
}
