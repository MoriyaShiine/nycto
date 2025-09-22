/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.client.NyctoClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;updateKeysByCode()V"))
	private void nycto$powerHotbarToggle(CallbackInfo ci, @Local(ordinal = 1) NbtCompound compound) {
		NyctoClient.POWER_HOTBAR_TOGGLED.setValue(Boolean.valueOf(compound.getString("nycto.togglePowerHotbar", "false")));
	}

	@Inject(method = "write", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void nycto$powerHotbarToggle(CallbackInfo ci, @Local PrintWriter printWriter) {
		printWriter.println("nycto.togglePowerHotbar:" + NyctoClient.POWER_HOTBAR_TOGGLED.getValue());
	}
}
