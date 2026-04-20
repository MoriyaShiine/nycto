/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.batform.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
	@ModifyReturnValue(method = "isSprintingPossible", at = @At("RETURN"))
	private boolean nycto$batForm(boolean original) {
		return original && !ModEntityComponents.BAT_FORM.get(this).isEnabled();
	}

	@ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isSpectator()Z"))
	private boolean nycto$batFormFly(boolean original) {
		return original || ModEntityComponents.BAT_FORM.get(this).isEnabled();
	}
}
