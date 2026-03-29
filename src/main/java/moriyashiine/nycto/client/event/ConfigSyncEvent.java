/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.client.ModConfig;
import moriyashiine.nycto.common.component.entity.SyncedConfigValuesComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.payload.SyncVampireChargeJumpStatusPayload;
import moriyashiine.nycto.common.payload.SyncVampireStepHeightStatusPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

public class ConfigSyncEvent implements ClientTickEvents.EndLevelTick {
	@Override
	public void onEndTick(ClientLevel level) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.slib$exists()) {
			SyncedConfigValuesComponent syncedConfigValuesComponent = ModEntityComponents.SYNCED_CONFIG_VALUES.get(player);
			{
				boolean current = syncedConfigValuesComponent.hasVampireChargeJump();
				if (current != ModConfig.vampireChargeJump) {
					syncedConfigValuesComponent.setVampireChargeJump(ModConfig.vampireChargeJump);
					SyncVampireChargeJumpStatusPayload.send(ModConfig.vampireChargeJump);
				}
			}
			{
				boolean current = syncedConfigValuesComponent.hasVampireStepHeight();
				if (current != ModConfig.vampireStepHeight) {
					syncedConfigValuesComponent.setVampireStepHeight(ModConfig.vampireStepHeight);
					SyncVampireStepHeightStatusPayload.send(ModConfig.vampireStepHeight);
				}
			}
		}
	}
}
