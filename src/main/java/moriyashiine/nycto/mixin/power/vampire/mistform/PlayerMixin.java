/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.mistform;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin extends LivingEntityMixin {
	@Override
	protected boolean nycto$mistForm(boolean original) {
		return super.nycto$mistForm(original) || NyctoEntityComponents.MIST_FORM.get(this).isEnabled();
	}

	@Override
	protected float nycto$mistForm(float original) {
		if (NyctoEntityComponents.MIST_FORM.get(this).isEnabled()) {
			return 0;
		}
		return super.nycto$mistForm(original);
	}
}
