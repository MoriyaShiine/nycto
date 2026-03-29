/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NeutralMob.class)
public interface NeutralMobMixin {
	@ModifyReturnValue(method = "isAngryAt", at = @At(value = "RETURN", ordinal = 2))
	private boolean nycto$vilePresence(boolean original, LivingEntity entity) {
		return original || ((Object) this instanceof Entity self && entity instanceof Player player && VilePresenceWeakness.isAffected(self, player));
	}
}
