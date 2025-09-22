/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.nycto.client.event.power.KeenSensesClientEvent;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Pool;
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
	private MinecraftClient client;

	@Shadow
	@Final
	private Pool pool;

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V"))
	private void nycto$keenSenses(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		if (KeenSensesClientEvent.shouldRenderShader()) {
			RenderSystem.resetTextureMatrix();
			PostEffectProcessor postEffectProcessor = client.getShaderLoader().loadPostEffect(Nycto.id("keen_senses"), DefaultFramebufferSet.MAIN_ONLY);
			if (postEffectProcessor != null) {
				postEffectProcessor.render(client.getFramebuffer(), pool);
			}
		}
	}
}
