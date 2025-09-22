/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityNavigation.class)
public class EntityNavigationMixin {
	@Shadow
	@Final
	protected MobEntity entity;

	@ModifyReturnValue(method = "isIdle", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original) {
		if (!original && ModEntityComponents.VAMPIRIC_THRALL.get(entity).getFollowMode() == VampiricThrallComponent.FollowMode.STAY) {
			return !NyctoUtil.isSurvival(entity.getTarget());
		}
		return original;
	}
}
