/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import moriyashiine.nycto.client.event.power.KeenSensesClientEvent;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	@Final
	private CrossFrameResourcePool resourcePool;

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V"))
	private void nycto$keenSenses(DeltaTracker deltaTracker, boolean advanceGameTime, CallbackInfo ci) {
		if (KeenSensesClientEvent.shouldRenderShader()) {
			PostChain postChain = minecraft.getShaderManager().getPostChain(Nycto.id("keen_senses"), LevelTargetBundle.MAIN_TARGETS);
			if (postChain != null) {
				postChain.process(minecraft.getMainRenderTarget(), resourcePool);
			}
		}
	}
}
