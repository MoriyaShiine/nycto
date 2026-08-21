package moriyashiine.nycto.common.component.entity;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class SyncedConfigValuesComponent implements AutoSyncedComponent {
	private boolean vampireChargeJump = true, vampireStepHeight = true;

	@Override
	public void readData(ValueInput input) {
		vampireChargeJump = input.getBooleanOr("VampireChargeJump", true);
		vampireStepHeight = input.getBooleanOr("VampireStepHeight", true);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("VampireChargeJump", vampireChargeJump);
		output.putBoolean("VampireStepHeight", vampireStepHeight);
	}

	public boolean hasVampireChargeJump() {
		return vampireChargeJump;
	}

	public void setVampireChargeJump(boolean vampireChargeJump) {
		this.vampireChargeJump = vampireChargeJump;
	}

	public boolean hasVampireStepHeight() {
		return vampireStepHeight;
	}

	public void setVampireStepHeight(boolean vampireStepHeight) {
		this.vampireStepHeight = vampireStepHeight;
	}
}
