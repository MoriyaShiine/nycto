/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/World;)Z"))
	private boolean nycto$coffin(boolean original) {
		if (getSleepingPosition().isPresent() && getEntityWorld().getBlockState(getSleepingPosition().get()).isIn(ModBlockTags.COFFINS)) {
			return true;
		}
		return original;
	}
}
