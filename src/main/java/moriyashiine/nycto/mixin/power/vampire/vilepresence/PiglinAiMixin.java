/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {
	@Shadow
	protected static void wasHurtBy(ServerLevel level, Piglin body, LivingEntity attacker) {
	}

	@Inject(method = "updateActivity", at = @At("TAIL"))
	private static void nycto$vilePresence(Piglin body, CallbackInfo ci) {
		if (body.tickCount % 5 == 0) {
			body.level().getEntities(EntityType.PLAYER, body.getBoundingBox().inflate(body.getAttributeValue(Attributes.FOLLOW_RANGE)), foundPlayer -> body.hasLineOfSight(foundPlayer) && body.distanceTo(foundPlayer) < body.getAttributeValue(Attributes.FOLLOW_RANGE) && VilePresenceWeakness.isAffected(body, foundPlayer)).forEach(player -> wasHurtBy((ServerLevel) body.level(), body, player));
		}
	}
}
