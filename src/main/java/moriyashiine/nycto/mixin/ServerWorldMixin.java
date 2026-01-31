/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// todo move to slib in 26.1
@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@ModifyReturnValue(method = "isPvpEnabled", at = @At("RETURN"))
	private boolean nycto$bypassPvp(boolean original) {
		return original || NyctoUtil.bypassPvp;
	}
}
