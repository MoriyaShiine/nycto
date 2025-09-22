/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.item.HunterContractItem;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HunterHeatComponent implements ServerTickingComponent {
	private static final int DECAY_TIMER = 1200, MAXIMUM_HEAT = 8;

	private final PlayerEntity obj;
	private int heatLevel = 0, decayTicks = 0;

	public HunterHeatComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		heatLevel = readView.getInt("HeatLevel", 0);
		decayTicks = readView.getInt("DecayTicks", decayTicks);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("HeatLevel", heatLevel);
		writeView.putInt("DecayTicks", decayTicks);
	}

	@Override
	public void serverTick() {
		if (heatLevel > 0 && --decayTicks == 0 && --heatLevel > 0) {
			decayTicks = DECAY_TIMER;
		}
	}

	public void increaseHeat() {
		if (heatLevel + 1 == MAXIMUM_HEAT) {
			if (HunterContractItem.spawnHunter(obj.getWorld(), obj, NyctoAPI.isVampire(obj) ? HunterEntity.HunterType.VAMPIRE : HunterEntity.HunterType.WEREWOLF, obj.getRandom().nextBetween(1, 3)).isAccepted()) {
				heatLevel = decayTicks = 0;
			}
		} else {
			heatLevel++;
			decayTicks = DECAY_TIMER;
		}
	}
}
