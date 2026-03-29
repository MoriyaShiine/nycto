/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.util;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public abstract class VampireFormChangeComponent implements AutoSyncedComponent, ServerTickingComponent {
	protected static final int FORM_DRAIN_TICKS = 300;

	protected final Player obj;
	protected boolean enabled = false;
	protected int drainTicks = 0;

	public VampireFormChangeComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		drainTicks = input.getIntOr("DrainTicks", drainTicks);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("DrainTicks", drainTicks);
	}

	@Override
	public void serverTick() {
		if (enabled && obj.slib$isSurvival() && --drainTicks == 0) {
			if (ModEntityComponents.BLOOD.get(obj).drain(1)) {
				drainTicks = FORM_DRAIN_TICKS;
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
