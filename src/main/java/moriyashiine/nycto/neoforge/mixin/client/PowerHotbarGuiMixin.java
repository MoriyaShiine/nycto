/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.client.PowerHotbarClientState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class PowerHotbarGuiMixin {
	@Shadow
	protected abstract Player getCameraPlayer();

	@Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
	private void nycto$renderPowerHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		Player player = getCameraPlayer();
		if (PowerHotbarClientState.isActive(player)) {
			PowerHotbarClientState.render(guiGraphics, player);
			ci.cancel();
		}
	}
}
