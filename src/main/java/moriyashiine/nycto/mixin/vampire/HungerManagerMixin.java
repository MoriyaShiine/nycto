/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
	@Unique
	private static boolean vampire = false;

	@Shadow
	public abstract boolean isNotFull();

	@Shadow
	public abstract void add(int food, float saturationModifier);

	@Shadow
	public abstract void addExhaustion(float exhaustion);

	@Shadow
	public abstract void setFoodLevel(int foodLevel);

	@Shadow
	public abstract void setSaturationLevel(float saturationLevel);

	@Shadow
	private float exhaustion;

	@Inject(method = "update", at = @At("HEAD"))
	private void nycto$vampire(ServerPlayerEntity player, CallbackInfo ci) {
		vampire = NyctoAPI.isVampire(player);
		if (vampire) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(player);
			if (bloodComponent.getBlood() > 0) {
				if (isNotFull()) {
					setFoodLevel(20);
					setSaturationLevel(20);
					exhaustion = 0;
					bloodComponent.drain(1);
				}
				if (player.getEntityWorld().getTime() % 15 == 0 && player.canFoodHeal() && player.getEntityWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
					player.heal(1);
					addExhaustion(2 / VampireTransformation.VAMPIRE_EXHAUSTION_MULTIPLIER);
				}
			} else {
				setFoodLevel(0);
				setSaturationLevel(0);
				exhaustion = 0;
			}
		}
	}

	@ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;canFoodHeal()Z"))
	private boolean nycto$vampire(boolean original) {
		return original && !vampire;
	}
}
