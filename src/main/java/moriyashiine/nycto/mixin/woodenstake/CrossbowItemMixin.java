/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.woodenstake;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.item.WoodenStakeItem;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
	@Inject(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V", shift = At.Shift.AFTER))
	private void nycto$woodenStake(World world, LivingEntity shooter, Hand hand, ItemStack stack, float speed, float divergence, LivingEntity target, CallbackInfo ci, @Local ChargedProjectilesComponent chargedProjectilesComponent, @Local ServerPlayerEntity serverPlayerEntity) {
		if (!shooter.isInCreativeMode() && chargedProjectilesComponent.contains(ModItems.WOODEN_STAKE)) {
			serverPlayerEntity.getItemCooldownManager().set(stack, WoodenStakeItem.getCrossbowCooldown(shooter));
			serverPlayerEntity.getItemCooldownManager().set(ModItems.WOODEN_STAKE.getDefaultStack(), WoodenStakeItem.getCooldown(shooter));
		}
	}
}
