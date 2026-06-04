/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class VampireFoodHudMixin {
	@Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
	private void nycto$hideFoodForVampires(GuiGraphics guiGraphics, Player player, int y, int x, CallbackInfo ci) {
		if (NyctoData.isVampire(player)) {
			ci.cancel();
		}
	}
}
