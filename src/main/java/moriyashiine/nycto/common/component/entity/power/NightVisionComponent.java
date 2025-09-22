/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class NightVisionComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final PlayerEntity obj;
	private boolean enabled = false;
	private int strength = 0;

	public NightVisionComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		enabled = readView.getBoolean("Enabled", false);
		strength = readView.getInt("Strength", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("Enabled", enabled);
		writeView.putInt("Strength", strength);
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
