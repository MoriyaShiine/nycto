/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public class HaemogenesisComponent implements moriyashiine.nycto.common.component.NyctoCommonTickingComponent {
	private final LivingEntity obj;
	private int toHeal = 0;

	public HaemogenesisComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		toHeal = input.getIntOr("ToHeal", 0);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putInt("ToHeal", toHeal);
	}

	@Override
	public void tick() {
		if (isHealing() && obj.tickCount % 2 == 0) {
			obj.heal(1);
			toHeal--;
		}
	}

	public void sync() {
		ModEntityComponents.HAEMOGENESIS.sync(obj);
	}

	public boolean isHealing() {
		return toHeal > 0;
	}

	public void startHealing() {
		NyctoAPI.applyHealBlock(obj, 0);
		obj.extinguishFire();
		toHeal = (int) (obj.getMaxHealth() / 3);
		sync();
	}
}
