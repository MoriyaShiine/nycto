/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.power.client;

import moriyashiine.nycto.client.NyctoClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AccessibilityOptionsScreen.class, ControlsOptionsScreen.class})
public abstract class PowerOptionsScreenMixin extends GameOptionsScreen {
	public PowerOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(method = "addOptions", at = @At("TAIL"))
	private void nycto$powerHotbarToggle(CallbackInfo ci) {
		body.addAll(NyctoClient.POWER_HOTBAR_TOGGLED);
	}
}
