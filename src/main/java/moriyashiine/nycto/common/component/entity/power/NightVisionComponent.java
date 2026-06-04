/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.player.Player;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public class NightVisionComponent implements moriyashiine.nycto.common.component.NyctoCommonTickingComponent {
	private final Player obj;
	private boolean enabled = false;
	private int strength = 0;

	public NightVisionComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		strength = input.getIntOr("Strength", 0);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
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
