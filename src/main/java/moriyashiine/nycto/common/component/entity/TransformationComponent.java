/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.common.init.ModTransformations;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.random.Random;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class TransformationComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final PlayerEntity obj;
	private Transformation transformation = ModTransformations.HUMAN;
	private final List<PowerInstance> powers = new ArrayList<>();
	private int powerIndex = 0;
	private int upgradeCostSeed = 0;

	public TransformationComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		transformation = readView.read("Transformation", NyctoRegistries.TRANSFORMATION.getCodec()).orElse(ModTransformations.HUMAN);
		powers.clear();
		powers.addAll(readView.read("Powers", PowerInstance.CODEC.listOf()).orElse(List.of()));
		powerIndex = readView.getInt("PowerIndex", 0);
		upgradeCostSeed = readView.getInt("UpgradeCostSeed", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("Transformation", NyctoRegistries.TRANSFORMATION.getCodec(), transformation);
		writeView.put("Powers", PowerInstance.CODEC.listOf(), powers);
		writeView.putInt("PowerIndex", powerIndex);
		writeView.putInt("UpgradeCostSeed", upgradeCostSeed);
	}

	@Override
	public void tick() {
		if (obj.isPartOfGame()) {
			if (obj instanceof ServerPlayerEntity player) {
				transformation.tick(player);
			}
			powers.forEach(instance -> {
				if (obj instanceof ServerPlayerEntity player) {
					instance.getPower().tick(player);
				}
				if (instance.getCooldown() > 0) {
					instance.setCooldown(instance.getCooldown() - 1);
				}
			});
		}
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	public List<PowerInstance> getPowers() {
		return powers;
	}

	public void addPower(PowerInstance instance) {
		powers.add(instance);
		if (obj instanceof ServerPlayerEntity player) {
			instance.getPower().onAdded(player);
		}
	}

	public void removePower(Power power) {
		for (int i = getPowers().size() - 1; i >= 0; i--) {
			if (powers.get(i).getPower() == power) {
				if (obj instanceof ServerPlayerEntity player) {
					power.onRemoved(player);
				}
				powers.remove(i);
			}
		}
		powerIndex = 0;
	}

	public boolean hasPower(Power power) {
		for (PowerInstance powerInstance : powers) {
			if (powerInstance.getPower() == power) {
				return true;
			}
		}
		return false;
	}

	public int getPowerIndex() {
		return powerIndex;
	}

	public void setPowerIndex(int powerIndex) {
		this.powerIndex = powerIndex;
	}

	public boolean hasActivePower() {
		return getPowers().stream().anyMatch(powerInstance -> powerInstance.getPower() instanceof ActivePower);
	}

	public int getRandomUpgradeCostIndex(List<?> list) {
		if (upgradeCostSeed == 0) {
			upgradeCostSeed = obj.getUuid().hashCode() + NyctoUtil.truncatedWorldSeed;
		}
		int index = upgradeCostSeed % list.size();
		if (index < 0) {
			index += list.size();
		}
		return index;
	}

	public void updateUpgradeCostSeed() {
		upgradeCostSeed = Random.create(upgradeCostSeed).nextInt();
	}
}
