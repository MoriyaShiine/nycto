/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
public abstract class TamableAnimalMixin extends Animal {
	protected TamableAnimalMixin(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Inject(method = "setOwner(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vilePresence(LivingEntity owner, CallbackInfo ci) {
		if (owner instanceof Player player && VilePresenceWeakness.isAffected(this, player)) {
			ci.cancel();
		}
	}

	@Inject(method = "setOwnerReference(Lnet/minecraft/world/entity/EntityReference;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vilePresence(@Nullable EntityReference<LivingEntity> owner, CallbackInfo ci) {
		if (owner != null && EntityReference.get(owner, level(), LivingEntity.class) instanceof Player player && VilePresenceWeakness.isAffected(this, player)) {
			ci.cancel();
		}
	}

	@ModifyVariable(method = "setOrderedToSit", at = @At("HEAD"), argsOnly = true)
	private boolean nycto$vilePresence(boolean orderedToSit) {
		return orderedToSit && !VilePresenceWeakness.isAffected(this, 8);
	}
}
