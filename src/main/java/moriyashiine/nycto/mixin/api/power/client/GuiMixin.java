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
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Shadow
	protected abstract Player getCameraPlayer();

	@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack nycto$powerHotbar(ItemStack original) {
		if (PowerClientEvent.isActive(getCameraPlayer(), ModEntityComponents.TRANSFORMATION.get(getCameraPlayer()))) {
			return ItemStack.EMPTY;
		}
		return original;
	}

	@WrapOperation(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0))
	private void nycto$powerHotbarTexture(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			Identifier hotbarTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarTexture();
			if (hotbarTexture != null) {
				location = hotbarTexture;
			}
		}
		original.call(instance, renderPipeline, location, x, y, width, height);
	}

	@WrapOperation(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 1))
	private void nycto$powerHotbarSelectionTexture(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			Identifier hotbarSelectionTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarSelectionTexture();
			if (hotbarSelectionTexture != null) {
				location = hotbarSelectionTexture;
			}
		}
		original.call(instance, renderPipeline, location, x, y, width, height);
	}

	@ModifyArg(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 1), index = 2)
	private int nycto$powerHotbar(int x) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(getCameraPlayer());
		if (PowerClientEvent.isActive(getCameraPlayer(), transformationComponent)) {
			return x - getCameraPlayer().getInventory().getSelectedSlot() * 20 + PowerClientEvent.getActivePowersIndex(transformationComponent) * 20;
		}
		return x;
	}

	@Inject(method = "extractSlot", at = @At("HEAD"), cancellable = true)
	private void nycto$powerHotbar(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, CallbackInfo ci) {
		if (PowerClientEvent.isActive(player, ModEntityComponents.TRANSFORMATION.get(player))) {
			ci.cancel();
		}
	}
}
