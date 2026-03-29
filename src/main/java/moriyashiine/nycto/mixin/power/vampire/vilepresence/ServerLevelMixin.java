/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
	@ModifyVariable(method = "broadcastEntityEvent", at = @At("HEAD"), argsOnly = true)
	private byte nycto$vilePresence(byte event, Entity entity) {
		if (event == EntityEvent.TAMING_SUCCEEDED && VilePresenceWeakness.isAffected(entity, 8)) {
			return EntityEvent.TAMING_FAILED;
		}
		return event;
	}
}
