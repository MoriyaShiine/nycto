/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	protected abstract PlayerEntity getCameraPlayer();

	@ModifyExpressionValue(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
	private ItemStack nycto$powerHotbar(ItemStack original) {
		if (PowerClientEvent.isActive(getCameraPlayer(), ModEntityComponents.TRANSFORMATION.get(getCameraPlayer()))) {
			return ItemStack.EMPTY;
		}
		return original;
	}

	@WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
	private void nycto$powerHotbarTexture(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			Identifier hotbarTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarTexture();
			if (hotbarTexture != null) {
				sprite = hotbarTexture;
			}
		}
		original.call(instance, pipeline, sprite, x, y, width, height);
	}

	@WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
	private void nycto$powerHotbarSelectionTexture(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			Identifier hotbarSelectionTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarSelectionTexture();
			if (hotbarSelectionTexture != null) {
				sprite = hotbarSelectionTexture;
			}
		}
		original.call(instance, pipeline, sprite, x, y, width, height);
	}

	@ModifyArg(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), index = 2)
	private int nycto$powerHotbar(int value) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			return value - getCameraPlayer().getInventory().getSelectedSlot() * 20 + PowerClientEvent.getActivePowersIndex(transformationComponent) * 20;
		}
		return value;
	}

	@Inject(method = "renderHotbarItem", at = @At("HEAD"), cancellable = true)
	private void nycto$powerHotbar(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed, CallbackInfo ci) {
		if (PowerClientEvent.isActive(player, ModEntityComponents.TRANSFORMATION.get(player))) {
			ci.cancel();
		}
	}
}
