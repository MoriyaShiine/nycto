/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.power.vampire.VilePresencePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {
	@Shadow
	protected static void onAttacked(ServerWorld world, PiglinEntity piglin, LivingEntity attacker) {
	}

	@Inject(method = "tickActivities", at = @At("TAIL"))
	private static void nycto$vilePresence(PiglinEntity piglin, CallbackInfo ci) {
		if (piglin.age % 5 == 0) {
			piglin.getWorld().getEntitiesByType(EntityType.PLAYER, piglin.getBoundingBox().expand(piglin.getAttributeValue(EntityAttributes.FOLLOW_RANGE)), foundPlayer -> piglin.canSee(foundPlayer) && piglin.distanceTo(foundPlayer) < piglin.getAttributeValue(EntityAttributes.FOLLOW_RANGE) && VilePresencePower.isAffected(piglin, foundPlayer)).forEach(player -> onAttacked((ServerWorld) piglin.getWorld(), piglin, player));
		}
	}
}
