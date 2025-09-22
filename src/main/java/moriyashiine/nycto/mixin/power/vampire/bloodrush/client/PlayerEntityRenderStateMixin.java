/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodrush.client;

import moriyashiine.nycto.client.render.entity.state.BloodrushRenderStateAddition;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements BloodrushRenderStateAddition {
	@Unique
	private boolean usingBloodrush = false, usingBloodrushLenient = false;

	@Override
	public void nycto$setUsingBloodrush(boolean usingBloodrush) {
		this.usingBloodrush = usingBloodrush;
	}

	@Override
	public boolean nycto$usingBloodrush() {
		return usingBloodrush;
	}

	@Override
	public void nycto$setUsingBloodrushLenient(boolean usingBloodrushLenient) {
		this.usingBloodrushLenient = usingBloodrushLenient;
	}

	@Override
	public boolean nycto$usingBloodrushLenient() {
		return usingBloodrushLenient;
	}
}
