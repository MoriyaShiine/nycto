/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class BatFormLivingEntityMixin {
	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double nycto$batFormVisibility(double original, Entity targetingEntity) {
		if (targetingEntity != null && !targetingEntity.getType().is(Tags.EntityTypes.BOSSES) && (Object) this instanceof ServerPlayer player && NyctoData.isVampire(player) && NyctoPowers.isBatFormActive(player)) {
			return original / 2;
		}
		return original;
	}
}
