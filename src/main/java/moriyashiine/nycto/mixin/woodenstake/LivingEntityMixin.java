/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.woodenstake;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.event.item.WoodenStakeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LivingEntity.class, priority = 1)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyExpressionValue(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
	private boolean nycto$woodenStakeArmorBypass(boolean original, DamageSource source) {
		if (NyctoAPI.isVampire(this) && WoodenStakeEvent.isWoodenStake(source)) {
			return true;
		}
		return original;
	}

	@ModifyExpressionValue(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 2))
	private boolean nycto$woodenStakeEnchantmentBypass(boolean original, DamageSource source) {
		if (NyctoAPI.isVampire(this) && WoodenStakeEvent.isWoodenStake(source)) {
			return true;
		}
		return original;
	}
}
