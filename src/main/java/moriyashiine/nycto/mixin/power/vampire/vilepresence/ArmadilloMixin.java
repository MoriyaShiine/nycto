/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Armadillo.class)
public abstract class ArmadilloMixin extends Animal {
	protected ArmadilloMixin(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@ModifyExpressionValue(method = "isScaredBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isPassenger()Z"))
	private boolean nycto$vilePresence(boolean original, @Local(name = "player") Player player) {
		return original || VilePresenceWeakness.isAffected(this, player);
	}
}
