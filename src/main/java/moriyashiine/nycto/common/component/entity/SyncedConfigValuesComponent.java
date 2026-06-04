/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public class SyncedConfigValuesComponent implements moriyashiine.nycto.common.component.NyctoServerTickingComponent {
	private final Player obj;
	private boolean vampireChargeJump = true, vampireStepHeight = true;

	public SyncedConfigValuesComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		vampireChargeJump = input.getBooleanOr("VampireChargeJump", true);
		vampireStepHeight = input.getBooleanOr("VampireStepHeight", true);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putBoolean("VampireChargeJump", vampireChargeJump);
		output.putBoolean("VampireStepHeight", vampireStepHeight);
	}

	@Override
	public void serverTick() {
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.STEP_HEIGHT, VampireTransformation.STEP_HEIGHT_MODIFIER, hasVampireStepHeight() && !obj.hasEffect(ModMobEffects.VAMPIRE_WARD) && !NyctoAPI.hasSunDebuff(obj) && !NyctoAPI.hasPower(obj, ModPowers.HUMANITY) && NyctoAPI.isVampire(obj));
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
