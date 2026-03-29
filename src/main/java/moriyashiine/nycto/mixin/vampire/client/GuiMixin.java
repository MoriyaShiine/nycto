/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.vampire.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.client.gui.hud.VampireHudElement;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 500)
public class GuiMixin {
	@Inject(method = "extractFood", at = @At("HEAD"), cancellable = true)
	private void nycto$vampire(GuiGraphicsExtractor graphics, Player player, int yLineBase, int xRight, CallbackInfo ci) {
		if (NyctoAPI.isVampire(player)) {
			VampireHudElement.extractBlood(graphics, player, xRight - 9, yLineBase, 10);
			ci.cancel();
		}
	}

	@WrapOperation(method = "extractAirBubbles", at = @At(value = "INVOKE", target = "Ljava/lang/Math;clamp(JII)I"))
	private int nycto$vampire(long value, int min, int max, Operation<Integer> original, @Local(argsOnly = true) Player player) {
		return original.call(player.isEyeInFluid(FluidTags.WATER) && NyctoAPI.isVampire(player) ? 0 : value, min, max);
	}

	@WrapOperation(method = "extractAirBubbles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getCurrentAirSupplyBubble(III)I", ordinal = 2))
	private int nycto$vampire(int currentAirSupplyTicks, int maxAirSupplyTicks, int tickOffset, Operation<Integer> original, @Local(argsOnly = true) Player player) {
		if (NyctoAPI.isVampire(player)) {
			return -1;
		}
		return original.call(currentAirSupplyTicks, maxAirSupplyTicks, tickOffset);
	}
}
