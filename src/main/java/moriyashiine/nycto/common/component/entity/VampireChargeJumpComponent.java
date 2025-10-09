/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class VampireChargeJumpComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final PlayerEntity obj;
	private boolean enabled = false;
	private int jumpStrength = 0;

	public VampireChargeJumpComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		enabled = readView.getBoolean("Enabled", false);
		jumpStrength = readView.getInt("JumpStrength", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("Enabled", enabled);
		writeView.putInt("JumpStrength", jumpStrength);
	}

	@Override
	public void tick() {
		if (enabled) {
			if (obj.isOnGround() && obj.isSneaking() && ModEntityComponents.SYNCED_CONFIG_VALUES.get(obj).hasVampireChargeJump()) {
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
		return MathHelper.lerp(jumpStrength / 20F, 0F, 1);
	}
}
