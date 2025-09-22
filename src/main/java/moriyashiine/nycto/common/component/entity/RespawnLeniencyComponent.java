/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class RespawnLeniencyComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final int MAX_TICKS = 600;

	private final LivingEntity obj;
	private int ticks = MAX_TICKS;

	public RespawnLeniencyComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		ticks = readView.getInt("Ticks", MAX_TICKS);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("Ticks", ticks);
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
