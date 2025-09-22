/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
	@ModifyReturnValue(method = "isInSameTeam", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original, Entity other) {
		if (!original) {
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(this);
			if (vampiricThrallComponent != null && vampiricThrallComponent.getOwnerUuid() != null) {
				@Nullable VampiricThrallComponent otherVampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(other);
				if (otherVampiricThrallComponent != null && vampiricThrallComponent.getOwnerUuid().equals(otherVampiricThrallComponent.getOwnerUuid())) {
					return true;
				}
			}
		}
		return original;
	}
}
