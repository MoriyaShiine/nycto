/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.power.vampire.weakness.VilePresencePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@ModifyVariable(method = "sendEntityStatus", at = @At("HEAD"), argsOnly = true)
	private byte nycto$vilePresence(byte value, Entity entity) {
		if (value == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES && VilePresencePower.isAffected(entity, 8)) {
			return EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES;
		}
		return value;
	}
}
