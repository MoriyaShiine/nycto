/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.power.vampire.VilePresencePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends LivingEntity {
	protected VillagerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyReturnValue(method = "getReputation", at = @At("RETURN"))
	private int nycto$vilePresence(int original, PlayerEntity player) {
		if (VilePresencePower.isAffected(this, player)) {
			return original - (ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled() ? 128 : 256);
		}
		return original;
	}
}
