/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.batform.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
	@ModifyReturnValue(method = "canSprint()Z", at = @At("RETURN"))
	private boolean nycto$batForm(boolean original) {
		return original && !ModEntityComponents.BAT_FORM.get(this).isEnabled();
	}
}
