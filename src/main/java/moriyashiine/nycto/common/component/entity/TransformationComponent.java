/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.init.ModTransformations;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class TransformationComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final Player obj;
	private Transformation transformation = ModTransformations.HUMAN;
	private final List<PowerInstance> powers = new ArrayList<>();
	private int powerIndex = 0;
	private int upgradeCostSeed = 0;

	public TransformationComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		transformation = input.read("Transformation", NyctoRegistries.TRANSFORMATION.byNameCodec()).orElse(ModTransformations.HUMAN);
		powers.clear();
		powers.addAll(input.read("Powers", PowerInstance.CODEC.listOf()).orElse(List.of()));
		powerIndex = input.getIntOr("PowerIndex", 0);
		upgradeCostSeed = input.getIntOr("UpgradeCostSeed", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("Transformation", NyctoRegistries.TRANSFORMATION.byNameCodec(), transformation);
		output.store("Powers", PowerInstance.CODEC.listOf(), powers);
		output.putInt("PowerIndex", powerIndex);
		output.putInt("UpgradeCostSeed", upgradeCostSeed);
	}

	@Override
	public void tick() {
		if (obj.slib$exists()) {
			if (obj instanceof ServerPlayer player) {
				transformation.tick(player);
			}
			powers.forEach(instance -> {
				if (obj instanceof ServerPlayer player) {
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
		if (obj instanceof ServerPlayer player) {
			instance.getPower().onAdded(player);
		}
	}

	public void removePower(Power power) {
		for (int i = getPowers().size() - 1; i >= 0; i--) {
			if (powers.get(i).getPower() == power) {
				if (obj instanceof ServerPlayer player) {
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
			upgradeCostSeed = obj.getUUID().hashCode() + NyctoUtil.truncatedWorldSeed;
		}
		int index = upgradeCostSeed % list.size();
		if (index < 0) {
			index += list.size();
		}
		return index;
	}

	public void updateUpgradeCostSeed() {
		upgradeCostSeed = RandomSource.create(upgradeCostSeed).nextInt();
	}
}
