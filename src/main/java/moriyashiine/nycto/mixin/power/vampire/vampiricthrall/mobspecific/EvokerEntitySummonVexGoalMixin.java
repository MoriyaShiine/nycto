/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.VexEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvokerEntity.SummonVexGoal.class)
public class EvokerEntitySummonVexGoalMixin {
	@Inject(method = "castSpell", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V"))
	private void nycto$vampiricThrall(CallbackInfo ci, @Local VexEntity vex) {
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(vex.getOwner());
		if (vampiricThrallComponent != null && vampiricThrallComponent.isThralled()) {
			ModEntityComponents.VAMPIRIC_VEX.get(vex).setThralled();
		}
	}
}
