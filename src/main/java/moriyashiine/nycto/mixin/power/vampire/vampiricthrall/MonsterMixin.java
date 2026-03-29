/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Monster.class)
public class MonsterMixin {
	@ModifyReturnValue(method = "isPreventingPlayerRest", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
