/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.carnage.client;

import moriyashiine.nycto.client.render.entity.state.CarnageRenderStateAddition;
import net.minecraft.client.render.entity.state.BatEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({PlayerEntityRenderState.class, BatEntityRenderState.class})
public class CarnageRenderStateAdditionMixin implements CarnageRenderStateAddition {
	@Unique
	private float carnageOpacity = 0;

	@Override
	public float nycto$getCarnageOpacity() {
		return carnageOpacity;
	}

	@Override
	public void nycto$setCarnageOpacity(float opacity) {
		carnageOpacity = opacity;
	}
}
