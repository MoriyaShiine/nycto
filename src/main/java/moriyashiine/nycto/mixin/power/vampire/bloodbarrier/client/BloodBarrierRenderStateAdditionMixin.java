/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodbarrier.client;

import moriyashiine.nycto.client.render.entity.state.BloodBarrierRenderStateAddition;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class BloodBarrierRenderStateAdditionMixin implements BloodBarrierRenderStateAddition {
	@Unique
	private int bloodBarriers = 0;

	@Override
	public int nycto$getBloodBarriers() {
		return bloodBarriers;
	}

	@Override
	public void nycto$setBloodBarriers(int barriers) {
		bloodBarriers = barriers;
	}
}
