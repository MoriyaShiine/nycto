/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.misc;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModCriteria;
import moriyashiine.nycto.common.init.ModEntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
	private static DefaultAttributeContainer.Builder nycto$vampireResistance(DefaultAttributeContainer.Builder original) {
		return original.add(ModEntityAttributes.VAMPIRE_RESISTANCE, 0).add(ModEntityAttributes.WEREWOLF_RESISTANCE, 0);
	}

	@Inject(method = {"onStatusEffectApplied", "onStatusEffectUpgraded"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;sendEffectToControllingPlayer(Lnet/minecraft/entity/effect/StatusEffectInstance;)V"))
	private void nycto$playerAppliesEffectsCriterion(CallbackInfo ci, @Local(argsOnly = true) Entity source) {
		if (source instanceof ServerPlayerEntity player) {
			ModCriteria.PLAYER_APPLIES_EFFECTS.trigger(player, (LivingEntity) (Object) this);
		}
	}
}
