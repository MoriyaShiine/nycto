/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class BloodBarrierArmorHudMixin {
	@Unique
	private static final ResourceLocation NYCTO_BLOOD_BARRIER = NyctoNeoForge.id("hud/blood_barrier");

	@Redirect(method = "renderArmorLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getArmorValue()I"))
	private int nycto$showArmorRowForBloodBarrier(Player player) {
		return Math.max(player.getArmorValue(), NyctoData.getBloodBarrierLayers(player));
	}

	@Redirect(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getArmorValue()I"))
	private static int nycto$reserveArmorSlotsForBloodBarrier(Player player) {
		return Math.max(player.getArmorValue(), NyctoData.getBloodBarrierLayers(player));
	}

	@Inject(method = "renderArmor", at = @At("TAIL"))
	private static void nycto$renderBloodBarrierArmorOverlay(GuiGraphics guiGraphics, Player player, int y, int rows, int rowHeight, int left, CallbackInfo ci) {
		int layers = Math.min(10, NyctoData.getBloodBarrierLayers(player));
		if (layers <= 0) {
			return;
		}
		int rowY = y - (rows - 1) * rowHeight - 10;
		for (int i = 0; i < layers; i++) {
			guiGraphics.blitSprite(NYCTO_BLOOD_BARRIER, left + i * 8, rowY, 9, 9);
		}
	}
}
