/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {
	@Unique
	private static boolean vampire = false;

	@Shadow
	public abstract boolean needsFood();

	@Shadow
	public abstract void addExhaustion(float amount);

	@Shadow
	public abstract void setFoodLevel(int food);

	@Shadow
	public abstract void setSaturation(float saturation);

	@Shadow
	private float exhaustionLevel;

	@Inject(method = "tick", at = @At("HEAD"))
	private void nycto$vampire(ServerPlayer player, CallbackInfo ci) {
		vampire = NyctoAPI.isVampire(player);
		if (vampire) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(player);
			if (bloodComponent.getBlood() > 0) {
				if (needsFood()) {
					setFoodLevel(20);
					setSaturation(20);
					exhaustionLevel = 0;
					bloodComponent.drain(1);
				}
				if (player.level().getGameTime() % 15 == 0 && player.isHurt() && player.level().getGameRules().get(GameRules.NATURAL_HEALTH_REGENERATION)) {
					player.heal(1);
					addExhaustion(2 / VampireTransformation.VAMPIRE_EXHAUSTION_MULTIPLIER);
				}
			} else {
				setFoodLevel(0);
				setSaturation(0);
				exhaustionLevel = 0;
			}
		}
	}

	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isHurt()Z"))
	private boolean nycto$vampire(boolean original) {
		return original && !vampire;
	}
}
