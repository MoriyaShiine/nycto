/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.misc;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModAttributes;
import moriyashiine.nycto.common.init.ModTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder nycto$vampireResistance(AttributeSupplier.Builder original) {
		return original.add(ModAttributes.VAMPIRE_RESISTANCE, 0).add(ModAttributes.WEREWOLF_RESISTANCE, 0);
	}

	@Inject(method = {"onEffectAdded", "onEffectUpdated"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;sendEffectToPassengers(Lnet/minecraft/world/effect/MobEffectInstance;)V"))
	private void nycto$playerAppliesEffectsCriterion(CallbackInfo ci, @Local(argsOnly = true) Entity source) {
		if (source instanceof ServerPlayer player) {
			ModTriggers.PLAYER_APPLIES_EFFECTS.trigger(player, (LivingEntity) (Object) this);
		}
	}
}
