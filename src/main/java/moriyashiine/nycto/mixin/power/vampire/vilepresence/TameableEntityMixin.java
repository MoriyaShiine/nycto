/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.power.vampire.VilePresencePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin extends AnimalEntity {
	protected TameableEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "setOwner(Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vilePresence(LivingEntity owner, CallbackInfo ci) {
		if (owner instanceof PlayerEntity player && VilePresencePower.isAffected(this, player)) {
			ci.cancel();
		}
	}

	@Inject(method = "setOwner(Lnet/minecraft/entity/LazyEntityReference;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vilePresence(@Nullable LazyEntityReference<LivingEntity> owner, CallbackInfo ci) {
		if (owner != null && LazyEntityReference.resolve(owner, getEntityWorld(), LivingEntity.class) instanceof PlayerEntity player && VilePresencePower.isAffected(this, player)) {
			ci.cancel();
		}
	}

	@ModifyVariable(method = "setSitting", at = @At("HEAD"), argsOnly = true)
	private boolean nycto$vilePresence(boolean value) {
		return value && !VilePresencePower.isAffected(this, 8);
	}
}
