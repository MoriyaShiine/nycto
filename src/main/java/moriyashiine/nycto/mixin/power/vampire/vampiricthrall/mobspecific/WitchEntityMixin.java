/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WitchEntity.class)
public class WitchEntityMixin {
	@Unique
	private boolean reverseBecauseThrall = false;

	@ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/PotionContentsComponent;createStack(Lnet/minecraft/item/Item;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/item/ItemStack;"))
	private RegistryEntry<Potion> nycto$vampiricThrall(RegistryEntry<Potion> potion) {
		if (potion == Potions.HEALING && ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return Potions.HARMING;
		}
		return potion;
	}

	@WrapOperation(method = "shootAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/PotionContentsComponent;createStack(Lnet/minecraft/item/Item;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/item/ItemStack;"))
	private ItemStack nycto$vampiricThrall(Item item, RegistryEntry<Potion> potion, Operation<ItemStack> original, LivingEntity target) {
		if (potion == Potions.HARMING || potion == Potions.POISON) {
			if (NyctoAPI.isVampire(target)) {
				potion = Potions.HEALING;
			}
		}
		return original.call(item, potion);
	}

	@WrapWithCondition(method = "shootAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/WitchEntity;setTarget(Lnet/minecraft/entity/LivingEntity;)V"))
	private boolean nycto$vampiricThrall(WitchEntity instance, LivingEntity entity) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(instance).hasOwner()) {
			reverseBecauseThrall = true;
			return false;
		}
		return true;
	}

	@ModifyArg(method = "shootAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/PotionContentsComponent;createStack(Lnet/minecraft/item/Item;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/item/ItemStack;"))
	private RegistryEntry<Potion> nycto$vampiricThrallRevert(RegistryEntry<Potion> potion) {
		if (reverseBecauseThrall) {
			reverseBecauseThrall = false;
			if (potion == Potions.HEALING || potion == Potions.REGENERATION) {
				return Potions.HARMING;
			}
		}
		return potion;
	}
}
