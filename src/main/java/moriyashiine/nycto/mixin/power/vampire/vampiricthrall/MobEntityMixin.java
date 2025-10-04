/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public class MobEntityMixin {
	@ModifyReturnValue(method = "createMobAttributes", at = @At("RETURN"))
	private static DefaultAttributeContainer.Builder nycto$vampiricThrall(DefaultAttributeContainer.Builder original) {
		return original.add(EntityAttributes.ATTACK_DAMAGE, 0);
	}

	@WrapOperation(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D"))
	private double nycto$vampiricThrall(MobEntity instance, RegistryEntry<EntityAttribute> entry, Operation<Double> original) {
		double value = original.call(instance, entry);
		if (instance.getAttributeBaseValue(entry) == 0) {
			value += 5;
		}
		return value;
	}

	@ModifyExpressionValue(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isAllowedInPeaceful()Z"))
	private boolean nycto$vampiricThrall(boolean original) {
		return original || ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled();
	}
}
