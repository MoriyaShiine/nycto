/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class RespawnLeniencyComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final int MAX_TICKS = 600;

	private final Player obj;
	private int ticks = MAX_TICKS;

	public RespawnLeniencyComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		ticks = input.getIntOr("Ticks", MAX_TICKS);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("Ticks", ticks);
	}

	@Override
	public void tick() {
		if (ticks > 0) {
			ticks--;
		}
	}

	public void sync() {
		ModEntityComponents.RESPAWN_LENIENCY.sync(obj);
	}

	public boolean hasLeniency() {
		return ticks > 0;
	}

	public void giveLeniency() {
		ticks = MAX_TICKS;
		sync();
	}
}
