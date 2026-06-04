/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.KeenSensesClientState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class KeenSensesGameRendererMixin {
	@Unique
	private static final ResourceLocation NYCTO$KEEN_SENSES_SHADER = NyctoNeoForge.id("shaders/post/keen_senses.json");

	@Shadow
	@Final
	Minecraft minecraft;

	@Shadow
	@Final
	private ResourceManager resourceManager;

	@Unique
	private PostChain nycto$keenSensesPostChain;
	@Unique
	private int nycto$keenSensesWidth = -1;
	@Unique
	private int nycto$keenSensesHeight = -1;
	@Unique
	private boolean nycto$keenSensesShaderLoadFailed = false;

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V"))
	private void nycto$renderKeenSensesShader(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
		if (!nycto$shouldRenderKeenSensesShader()) {
			nycto$closeKeenSensesPostChain();
			return;
		}
		PostChain postChain = nycto$getKeenSensesPostChain();
		if (postChain == null) {
			return;
		}
		RenderSystem.disableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.resetTextureMatrix();
		postChain.process(deltaTracker.getGameTimeDeltaTicks());
		minecraft.getMainRenderTarget().bindWrite(true);
	}

	@Inject(method = "close", at = @At("HEAD"))
	private void nycto$closeKeenSensesShader(CallbackInfo ci) {
		nycto$closeKeenSensesPostChain();
	}

	@Inject(method = "reloadShaders", at = @At("HEAD"))
	private void nycto$reloadKeenSensesShader(ResourceProvider resourceProvider, CallbackInfo ci) {
		nycto$closeKeenSensesPostChain();
		nycto$keenSensesShaderLoadFailed = false;
	}

	@Inject(method = "resize", at = @At("TAIL"))
	private void nycto$resizeKeenSensesShader(int width, int height, CallbackInfo ci) {
		if (nycto$keenSensesPostChain != null) {
			nycto$keenSensesPostChain.resize(width, height);
			nycto$keenSensesWidth = width;
			nycto$keenSensesHeight = height;
		}
	}

	@Unique
	private boolean nycto$shouldRenderKeenSensesShader() {
		Player player = minecraft.player;
		return !minecraft.options.hideGui && player != null && KeenSensesClientState.isActive(player);
	}

	@Unique
	private PostChain nycto$getKeenSensesPostChain() {
		if (nycto$keenSensesShaderLoadFailed) {
			return null;
		}
		RenderTarget target = minecraft.getMainRenderTarget();
		int width = minecraft.getWindow().getWidth();
		int height = minecraft.getWindow().getHeight();
		if (nycto$keenSensesPostChain != null && (nycto$keenSensesWidth != width || nycto$keenSensesHeight != height)) {
			nycto$keenSensesPostChain.resize(width, height);
			nycto$keenSensesWidth = width;
			nycto$keenSensesHeight = height;
		}
		if (nycto$keenSensesPostChain == null) {
			try {
				nycto$keenSensesPostChain = new PostChain(minecraft.getTextureManager(), resourceManager, target, NYCTO$KEEN_SENSES_SHADER);
				nycto$keenSensesPostChain.resize(width, height);
				nycto$keenSensesWidth = width;
				nycto$keenSensesHeight = height;
			} catch (IOException | JsonSyntaxException exception) {
				NyctoNeoForge.LOGGER.warn("Failed to load Keen Senses shader: {}", NYCTO$KEEN_SENSES_SHADER, exception);
				nycto$closeKeenSensesPostChain();
				nycto$keenSensesShaderLoadFailed = true;
			}
		}
		return nycto$keenSensesPostChain;
	}

	@Unique
	private void nycto$closeKeenSensesPostChain() {
		if (nycto$keenSensesPostChain != null) {
			nycto$keenSensesPostChain.close();
			nycto$keenSensesPostChain = null;
		}
		nycto$keenSensesWidth = -1;
		nycto$keenSensesHeight = -1;
	}
}
