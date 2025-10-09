/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class SyncedConfigValuesComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity obj;
	private boolean vampireChargeJump = true, vampireStepHeight = true;

	public SyncedConfigValuesComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		vampireChargeJump = readView.getBoolean("VampireChargeJump", true);
		vampireStepHeight = readView.getBoolean("VampireStepHeight", true);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("VampireChargeJump", vampireChargeJump);
		writeView.putBoolean("VampireStepHeight", vampireStepHeight);
	}

	@Override
	public void serverTick() {
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.STEP_HEIGHT, VampireTransformation.STEP_HEIGHT_MODIFIER, hasVampireStepHeight() && !obj.hasStatusEffect(ModStatusEffects.VAMPIRE_WARD) && NyctoAPI.isVampire(obj));
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
