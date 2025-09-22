/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.hasowner;

import moriyashiine.nycto.common.entity.ai.goal.thrall.ThralledEscapeSunlightGoal;
import moriyashiine.nycto.common.entity.ai.goal.thrall.ThralledFollowOwnerGoal;
import moriyashiine.nycto.common.entity.ai.goal.thrall.ThralledMeleeAttackGoal;
import moriyashiine.nycto.common.entity.ai.goal.thrall.ThralledRevengeGoal;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
	@Shadow
	@Final
	protected GoalSelector goalSelector;

	@Shadow
	@Final
	public GoalSelector targetSelector;

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;initGoals()V", shift = At.Shift.AFTER))
	private void nycto$hasOwner(EntityType<?> entityType, World world, CallbackInfo ci) {
		if ((Object) this instanceof PathAwareEntity entity) {
			goalSelector.add(0, new ThralledEscapeSunlightGoal(entity, 1));
			goalSelector.add(1, new ThralledFollowOwnerGoal(entity, 1));
			if (NyctoUtil.isVillager(entity)) {
				goalSelector.add(9, new ThralledMeleeAttackGoal(entity, 1, false));
				targetSelector.add(1, new ThralledRevengeGoal(entity));
			}
		}
	}
}
