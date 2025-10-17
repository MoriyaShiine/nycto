/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.item.HunterContractItem;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModGameRules;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HunterHeatComponent implements ServerTickingComponent {
	private static final int DECAY_TIMER = 6000, MAXIMUM_HEAT = 5, MAXIMUM_SPAWNS = 3;

	private final PlayerEntity obj;
	private int heatLevel = 0, decayTicks = 0;
	private int timesSpawned = 0, spawnDecayTicks = 0;

	public HunterHeatComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		heatLevel = readView.getInt("HeatLevel", 0);
		decayTicks = readView.getInt("DecayTicks", 0);
		timesSpawned = readView.getInt("TimesSpawned", 0);
		spawnDecayTicks = readView.getInt("SpawnDecayTicks", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("HeatLevel", heatLevel);
		writeView.putInt("DecayTicks", decayTicks);
		writeView.putInt("TimesSpawned", timesSpawned);
		writeView.putInt("SpawnDecayTicks", spawnDecayTicks);
	}

	@Override
	public void serverTick() {
		if (heatLevel > 0 && --decayTicks == 0 && --heatLevel > 0) {
			decayTicks = DECAY_TIMER;
		}
		if (timesSpawned > 0 && --spawnDecayTicks == 0 && --timesSpawned > 0) {
			spawnDecayTicks = DECAY_TIMER;
		}
	}

	public void maybeIncreaseHeat(LivingEntity target, boolean maximize) {
		if (!obj.isCreative() && target.getType().isIn(ModEntityTypeTags.CALLS_HUNTERS) && obj.getEntityWorld() instanceof ServerWorld world && world.getGameRules().getBoolean(ModGameRules.DO_HUNTER_SPAWNING)) {
			if (target instanceof Monster && target.getRandom().nextBoolean()) {
				return;
			}
			if (target instanceof RaiderEntity raider && raider.getRaid() != null) {
				return;
			}
			@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			if (vampiricThrallComponent != null && vampiricThrallComponent.isThralled()) {
				return;
			}
			if (maximize) {
				maximizeHeat();
			} else {
				increaseHeat();
			}
		}
	}

	public void increaseHeat() {
		if (heatLevel + 1 >= MAXIMUM_HEAT) {
			if (timesSpawned < MAXIMUM_SPAWNS) {
				if (HunterContractItem.spawnHunter(obj.getEntityWorld(), obj, NyctoAPI.isVampire(obj) ? HunterEntity.HunterType.VAMPIRE : HunterEntity.HunterType.WEREWOLF, obj.getRandom().nextBetween(1, 3)).isAccepted()) {
					heatLevel = decayTicks = 0;
					timesSpawned++;
					spawnDecayTicks = DECAY_TIMER;
				}
			}
		} else {
			heatLevel++;
			decayTicks = DECAY_TIMER;
		}
	}

	public void maximizeHeat() {
		for (int i = heatLevel; i < MAXIMUM_HEAT; i++) {
			increaseHeat();
		}
	}
}
