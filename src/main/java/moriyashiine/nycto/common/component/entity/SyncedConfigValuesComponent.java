/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class SyncedConfigValuesComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final Player obj;
	private boolean vampireChargeJump = true, vampireStepHeight = true;

	public SyncedConfigValuesComponent(Player obj) {
		this.obj = obj;
	}

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

	@Override
	public void serverTick() {
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.STEP_HEIGHT, VampireTransformation.STEP_HEIGHT_MODIFIER, hasVampireStepHeight() && !obj.hasEffect(ModMobEffects.VAMPIRE_WARD) && !NyctoAPI.isSunExposed(obj) && NyctoAPI.isVampire(obj));
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
