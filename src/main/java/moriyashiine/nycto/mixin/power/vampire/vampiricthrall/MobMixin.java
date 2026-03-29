/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {
	@ModifyReturnValue(method = "createMobAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder nycto$vampiricThrall(AttributeSupplier.Builder original) {
		return original.add(Attributes.ATTACK_DAMAGE, 0);
	}

	@WrapOperation(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
	private double nycto$vampiricThrall(Mob instance, Holder<Attribute> entry, Operation<Double> original) {
		double value = original.call(instance, entry);
		if (instance.getAttributeBaseValue(entry) == 0) {
			value += 5;
		}
		return value;
	}

	@ModifyExpressionValue(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;isAllowedInPeaceful()Z"))
	private boolean nycto$vampiricThrall(boolean original) {
		return original || ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
