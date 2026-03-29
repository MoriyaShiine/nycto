/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {
	@ModifyReturnValue(method = "canEatGrass", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
