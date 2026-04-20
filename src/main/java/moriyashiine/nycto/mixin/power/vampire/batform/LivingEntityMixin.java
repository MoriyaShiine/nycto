/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.batform;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyExpressionValue(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 5))
	private boolean nycto$batForm(boolean original, ServerLevel level, DamageSource source) {
		return original || (source.getDirectEntity() instanceof Player player && ModEntityComponents.BAT_FORM.get(player).isEnabled());
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double nycto$batForm(double original, Entity targetingEntity) {
		if (targetingEntity != null && !targetingEntity.is(ConventionalEntityTypeTags.BOSSES) && (Object) this instanceof Player player && ModEntityComponents.BAT_FORM.get(player).isEnabled()) {
			return original / 2;
		}
		return original;
	}
}
