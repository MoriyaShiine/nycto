/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.util;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.player.Player;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public abstract class VampireFormChangeComponent implements moriyashiine.nycto.common.component.NyctoServerTickingComponent {
	protected static final int POWER_DRAIN_TICKS = 300;

	protected final Player obj;
	protected boolean enabled = false;
	protected int drainTicks = 0;

	public VampireFormChangeComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		drainTicks = input.getIntOr("DrainTicks", drainTicks);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("DrainTicks", drainTicks);
	}

	@Override
	public void serverTick() {
		if (enabled && obj.slib$isSurvival() && --drainTicks == 0) {
			if (ModEntityComponents.BLOOD.get(obj).drain(1)) {
				drainTicks = POWER_DRAIN_TICKS;
			} else {
				toggle();
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public abstract void toggle();
}
