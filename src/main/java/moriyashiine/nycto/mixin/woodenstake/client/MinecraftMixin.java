/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.woodenstake.client;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
	private void nycto$woodenStake(CallbackInfoReturnable<Boolean> cir) {
		if (player != null && player.getMainHandItem().is(ModItems.WOODEN_STAKE) && player.getCooldowns().isOnCooldown(ModItems.WOODEN_STAKE.getDefaultInstance())) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
	private void nycto$woodenStake(boolean down, CallbackInfo ci) {
		if (player != null && player.getMainHandItem().is(ModItems.WOODEN_STAKE) && player.getCooldowns().isOnCooldown(ModItems.WOODEN_STAKE.getDefaultInstance())) {
			ci.cancel();
		}
	}
}
