/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"))
	private void nycto$power(PlayerInventory instance, int slot, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		if (PowerClientEvent.isActive(player, transformationComponent)) {
			if (slot < transformationComponent.getPowers().stream().filter(powerInstance -> powerInstance.getPower() instanceof ActivePower).count()) {
				PowerClientEvent.scrollPowerIndex(player, slot - PowerClientEvent.getActivePowersIndex(transformationComponent));
			}
			return;
		}
		original.call(instance, slot);
	}

	@ModifyExpressionValue(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 1))
	private boolean nycto$power(boolean original) {
		return original && !PowerClientEvent.isActive(player, ModEntityComponents.TRANSFORMATION.get(player));
	}
}
