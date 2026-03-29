/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.woodenstake;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.world.item.WoodenStakeItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
	@Inject(method = "performShooting", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/stats/Stat;)V"))
	private void nycto$woodenStake(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float power, float uncertainty, LivingEntity targetOverride, CallbackInfo ci, @Local(name = "charged") ChargedProjectiles charged, @Local(name = "player") ServerPlayer player) {
		if (!shooter.hasInfiniteMaterials() && charged.contains(ModItems.WOODEN_STAKE)) {
			player.getCooldowns().addCooldown(weapon, WoodenStakeItem.getCrossbowCooldown(shooter));
			player.getCooldowns().addCooldown(ModItems.WOODEN_STAKE.getDefaultInstance(), WoodenStakeItem.getCooldown(shooter));
		}
	}
}
