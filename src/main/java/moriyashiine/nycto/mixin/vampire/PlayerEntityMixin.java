/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyVariable(method = "addExhaustion", at = @At("HEAD"), argsOnly = true)
	private float nycto$vampire(float value) {
		if (NyctoAPI.isVampire(this)) {
			return value * VampireTransformation.VAMPIRE_EXHAUSTION_MULTIPLIER;
		}
		return value;
	}

	@ModifyExpressionValue(method = "canConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;isNotFull()Z"))
	private boolean nycto$vampire(boolean original) {
		return original || NyctoAPI.isVampire(this);
	}
}
