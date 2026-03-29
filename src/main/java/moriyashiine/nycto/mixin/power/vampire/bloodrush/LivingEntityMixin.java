/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.bloodrush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodrushComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyExpressionValue(method = "checkAutoSpinAttack", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
	private boolean nycto$bloodrush(boolean original) {
		if (!original) {
			@Nullable BloodrushComponent bloodrushComponent = ModEntityComponents.BLOODRUSH.getNullable(this);
			if (bloodrushComponent != null && bloodrushComponent.isActive(false)) {
				return true;
			}
		}
		return original;
	}
}
