/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.util;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public abstract class VampireFormChangeComponent implements AutoSyncedComponent, ServerTickingComponent {
	protected static final int FORM_DRAIN_TICKS = 300;

	protected final PlayerEntity obj;
	protected boolean enabled = false;
	protected int drainTicks = 0;

	public VampireFormChangeComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		enabled = readView.getBoolean("Enabled", false);
		drainTicks = readView.getInt("DrainTicks", drainTicks);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("Enabled", enabled);
		writeView.putInt("DrainTicks", drainTicks);
	}

	@Override
	public void serverTick() {
		if (enabled && NyctoUtil.isSurvival(obj) && --drainTicks == 0) {
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
