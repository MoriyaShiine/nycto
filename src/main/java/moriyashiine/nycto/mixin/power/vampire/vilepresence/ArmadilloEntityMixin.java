/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.power.vampire.weakness.VilePresencePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ArmadilloEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmadilloEntity.class)
public abstract class ArmadilloEntityMixin extends AnimalEntity {
	protected ArmadilloEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(method = "isEntityThreatening", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;hasVehicle()Z"))
	private boolean nycto$vilePresence(boolean original, @Local PlayerEntity player) {
		return original || VilePresencePower.isAffected(this, player);
	}
}
