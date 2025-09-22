/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.client;

import moriyashiine.nycto.client.render.entity.state.VampiricThrallRenderStateAddition;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements VampiricThrallRenderStateAddition {
	@Unique
	private boolean thralled = false;

	@Override
	public void nycto$setThralled(boolean thralled) {
		this.thralled = thralled;
	}

	@Override
	public boolean nycto$isThralled() {
		return thralled;
	}
}
