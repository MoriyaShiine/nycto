/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.batform;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyExpressionValue(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 5))
	private boolean nycto$batForm(boolean original, ServerWorld world, DamageSource source) {
		return original || (source.getSource() instanceof PlayerEntity player && ModEntityComponents.BAT_FORM.get(player).isEnabled());
	}
}
