/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.mistform;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntityMixin {
	@Override
	protected boolean nycto$mistForm(boolean original) {
		return super.nycto$mistForm(original) || ModEntityComponents.MIST_FORM.get(this).isEnabled();
	}

	@Override
	protected float nycto$mistForm(float original) {
		if (ModEntityComponents.MIST_FORM.get(this).isEnabled()) {
			return 0;
		}
		return super.nycto$mistForm(original);
	}
}
