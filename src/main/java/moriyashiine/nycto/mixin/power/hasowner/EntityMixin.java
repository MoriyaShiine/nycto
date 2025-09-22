/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.hasowner;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "isInSameTeam", at = @At("RETURN"))
	private boolean nycto$hasOwner(boolean original, Entity other) {
		return original || HasOwnerComponent.isOwner((Entity) (Object) this, other);
	}
}
