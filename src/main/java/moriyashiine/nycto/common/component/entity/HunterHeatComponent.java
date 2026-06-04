/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.entity.huntertype.HunterType;
import moriyashiine.nycto.api.world.item.HunterContractItem;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModGameRules;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;
import net.minecraft.world.phys.AABB;

public class HunterHeatComponent implements moriyashiine.nycto.common.component.NyctoServerTickingComponent {
	private static final int DECAY_TIMER = 6000, MAXIMUM_HEAT = 5, MAXIMUM_SPAWNS = 3;

	private final Player obj;
	private int heatLevel = 0, decayTicks = 0;
	private int timesSpawned = 0, spawnDecayTicks = 0;

	public HunterHeatComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		heatLevel = input.getIntOr("HeatLevel", 0);
		decayTicks = input.getIntOr("DecayTicks", 0);
		timesSpawned = input.getIntOr("TimesSpawned", 0);
		spawnDecayTicks = input.getIntOr("SpawnDecayTicks", 0);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putInt("HeatLevel", heatLevel);
		output.putInt("DecayTicks", decayTicks);
		output.putInt("TimesSpawned", timesSpawned);
		output.putInt("SpawnDecayTicks", spawnDecayTicks);
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
		if (!obj.isCreative() && obj.level() instanceof ServerLevel level && level.getGameRules().get(ModGameRules.SPAWN_HUNTERS) && canIncreaseHeat(target)) {
			if (target instanceof Enemy && target.getRandom().nextBoolean()) {
				return;
			}
			if (!obj.level().getEntitiesOfClass(LivingEntity.class, new AABB(target.blockPosition()).inflate(16), foundEntity -> target != foundEntity && canIncreaseHeat(foundEntity)).isEmpty()) {
				if (maximize) {
					maximizeHeat();
				} else {
					increaseHeat();
				}
			}
		}
	}

	public void increaseHeat() {
		if (heatLevel + 1 >= MAXIMUM_HEAT) {
			if (timesSpawned < MAXIMUM_SPAWNS) {
				HunterType hunterType = NyctoRegistries.HUNTER_TYPE.stream().filter(type -> type.shouldTarget(obj)).findFirst().orElse(null);
				if (hunterType != null && HunterContractItem.spawnHunter(obj.level(), obj, hunterType, obj.getRandom().nextIntBetweenInclusive(1, 3)).consumesAction()) {
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

	private static boolean canIncreaseHeat(LivingEntity target) {
		if (target.is(ModEntityTypeTags.CALLS_HUNTERS)) {
			if (target instanceof Raider raider && raider.getCurrentRaid() != null) {
				return false;
			}
			VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(target);
			return vampiricThrallComponent == null || !vampiricThrallComponent.hasOwner();
		}
		return false;
	}
}
