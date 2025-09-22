/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public class MouseMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@WrapOperation(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"))
	private void nycto$power(PlayerInventory instance, int slot, Operation<Void> original, @Local int i) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(client.player);
		if (PowerClientEvent.isActive(client.player, transformationComponent) && PowerClientEvent.scrollPowerIndex(client.player, -i)) {
			return;
		}
		original.call(instance, slot);
	}
}
