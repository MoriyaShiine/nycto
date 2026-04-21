/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class VampireChargeJumpComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final Player obj;
	private boolean enabled = false;
	private int jumpStrength = 0;

	public VampireChargeJumpComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		jumpStrength = input.getIntOr("JumpStrength", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("JumpStrength", jumpStrength);
	}

	@Override
	public void tick() {
		if (enabled) {
			if (obj.onGround() && obj.isShiftKeyDown() && ModEntityComponents.SYNCED_CONFIG_VALUES.get(obj).hasVampireChargeJump() && !NyctoAPI.hasSunDebuff(obj)) {
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
