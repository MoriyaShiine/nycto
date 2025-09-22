/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class VampireEntityRenderState extends BipedEntityRenderState implements CarnageRenderStateAddition {
	public boolean attacking = false;
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
