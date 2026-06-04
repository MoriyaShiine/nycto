/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public class VampireChargeJumpComponent implements moriyashiine.nycto.common.component.NyctoCommonTickingComponent {
	private final Player obj;
	private boolean enabled = false;
	private int jumpStrength = 0;

	public VampireChargeJumpComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		jumpStrength = input.getIntOr("JumpStrength", 0);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("JumpStrength", jumpStrength);
	}

	@Override
	public void tick() {
		if (enabled) {
			if (obj.onGround() && obj.isShiftKeyDown() && ModEntityComponents.SYNCED_CONFIG_VALUES.get(obj).hasVampireChargeJump() && !NyctoAPI.hasSunDebuff(obj) && !NyctoAPI.hasPower(obj, ModPowers.HUMANITY)) {
				if (jumpStrength < 20) {
					jumpStrength++;
				}
			} else {
				jumpStrength = 0;
			}
		} else {
			jumpStrength = 0;
		}
	}

	public void sync() {
		ModEntityComponents.VAMPIRE_CHARGE_JUMP.sync(obj);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public float getBoostProgress() {
		return Mth.lerp(jumpStrength / 20F, 0F, 1);
	}
}
