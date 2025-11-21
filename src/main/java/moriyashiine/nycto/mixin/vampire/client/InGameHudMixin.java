/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.client.hud.VampireHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = 500)
public class InGameHudMixin {
	@Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
	private void nycto$vampire(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
		if (NyctoAPI.isVampire(player)) {
			VampireHudElement.drawBlood(context, player, right - 9, top, 10);
			ci.cancel();
		}
	}

	@WrapOperation(method = "renderAirBubbles", at = @At(value = "INVOKE", target = "Ljava/lang/Math;clamp(JII)I"))
	private int nycto$vampire(long value, int min, int max, Operation<Integer> original, DrawContext context, PlayerEntity player) {
		return original.call(player.isSubmergedIn(FluidTags.WATER) && NyctoAPI.isVampire(player) ? 0 : value, min, max);
	}

	@WrapOperation(method = "renderAirBubbles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;getAirBubbles(III)I", ordinal = 2))
	private int nycto$vampire(int air, int maxAir, int delay, Operation<Integer> original, DrawContext context, PlayerEntity player) {
		if (NyctoAPI.isVampire(player)) {
			return -1;
		}
		return original.call(air, maxAir, delay);
	}
}
