/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.api.power.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V"))
	private void nycto$power(Inventory instance, int selected, Operation<Void> original) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		if (PowerClientEvent.isActive(player, transformationComponent)) {
			if (selected < transformationComponent.getPowers().stream().filter(powerInstance -> powerInstance.getPower() instanceof ActivePower).count()) {
				PowerClientEvent.scrollPowerIndex(player, selected - PowerClientEvent.getActivePowersIndex(transformationComponent));
			}
			return;
		}
		original.call(instance, selected);
	}

	@ModifyExpressionValue(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1))
	private boolean nycto$power(boolean original) {
		return original && !PowerClientEvent.isActive(player, ModEntityComponents.TRANSFORMATION.get(player));
	}
}
