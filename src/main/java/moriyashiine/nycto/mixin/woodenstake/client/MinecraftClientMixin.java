/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.woodenstake.client;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
	private void nycto$woodenStake(CallbackInfoReturnable<Boolean> cir) {
		if (player != null && player.getMainHandStack().isOf(ModItems.WOODEN_STAKE) && player.getItemCooldownManager().isCoolingDown(ModItems.WOODEN_STAKE.getDefaultStack())) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
	private void nycto$woodenStake(boolean breaking, CallbackInfo ci) {
		if (player != null && player.getMainHandStack().isOf(ModItems.WOODEN_STAKE) && player.getItemCooldownManager().isCoolingDown(ModItems.WOODEN_STAKE.getDefaultStack())) {
			ci.cancel();
		}
	}
}
