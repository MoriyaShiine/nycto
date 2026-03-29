/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Witch.class)
public class WitchMixin {
	@Unique
	private boolean reverseBecauseThrall = false;

	@ModifyArg(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionContents;createItemStack(Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"))
	private Holder<Potion> nycto$vampiricThrall(Holder<Potion> potion) {
		if (potion == Potions.HEALING && ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return Potions.HARMING;
		}
		return potion;
	}

	@WrapOperation(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionContents;createItemStack(Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack nycto$vampiricThrall(Item item, Holder<Potion> potion, Operation<ItemStack> original, LivingEntity target) {
		if (potion == Potions.HARMING || potion == Potions.POISON) {
			if (NyctoAPI.isVampire(target)) {
				potion = Potions.HEALING;
			}
		}
		return original.call(item, potion);
	}

	@WrapWithCondition(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Witch;setTarget(Lnet/minecraft/world/entity/LivingEntity;)V"))
	private boolean nycto$vampiricThrall(Witch instance, LivingEntity entity) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(instance).hasOwner()) {
			reverseBecauseThrall = true;
			return false;
		}
		return true;
	}

	@ModifyArg(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionContents;createItemStack(Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"))
	private Holder<Potion> nycto$vampiricThrallRevert(Holder<Potion> potion) {
		if (reverseBecauseThrall) {
			reverseBecauseThrall = false;
			if (potion == Potions.HEALING || potion == Potions.REGENERATION) {
				return Potions.HARMING;
			}
		}
		return potion;
	}
}
