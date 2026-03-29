/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class HaemogenesisComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final LivingEntity obj;
	private int toHeal = 0;

	public HaemogenesisComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		toHeal = input.getIntOr("ToHeal", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
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
