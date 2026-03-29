/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class NightVisionComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final Player obj;
	private boolean enabled = false;
	private int strength = 0;

	public NightVisionComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		strength = input.getIntOr("Strength", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("Strength", strength);
	}

	@Override
	public void tick() {
		int targetStrength = enabled ? 10 : 0;
		if (strength < targetStrength) {
			strength++;
		} else if (strength > targetStrength) {
			strength--;
		}
	}

	public void sync() {
		ModEntityComponents.NIGHT_VISION.sync(obj);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public float getStrengthPercentage() {
		return strength / 10F;
	}

	public void toggle() {
		enabled = !enabled;
		sync();
	}
}
