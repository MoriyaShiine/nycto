/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.client.NyctoClient;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(Options.class)
public class OptionsMixin {
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;resetMapping()V"))
	private void nycto$powerHotbarToggle(CallbackInfo ci, @Local(ordinal = 1) CompoundTag compound) {
		NyctoClient.POWER_HOTBAR_TOGGLED.set(Boolean.valueOf(compound.getStringOr("nycto.togglePowerHotbar", "false")));
	}

	@Inject(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void nycto$powerHotbarToggle(CallbackInfo ci, @Local PrintWriter printWriter) {
		printWriter.println("nycto.togglePowerHotbar:" + NyctoClient.POWER_HOTBAR_TOGGLED.get());
	}
}
