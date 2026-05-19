/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.client.NyctoConfig;
import moriyashiine.nycto.common.component.entity.SyncedConfigValuesComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.payload.SyncConfigValuesPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

public class ConfigSyncClientEvent implements ClientTickEvents.EndLevelTick {
	public static void init() {
		ClientTickEvents.END_LEVEL_TICK.register(new ConfigSyncClientEvent());
	}

	@Override
	public void onEndTick(ClientLevel level) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.slib$exists()) {
			SyncedConfigValuesComponent syncedConfigValues = NyctoEntityComponents.SYNCED_CONFIG_VALUES.get(player);
			boolean changed = false;
			{
				boolean current = syncedConfigValues.hasVampireChargeJump();
				if (current != NyctoConfig.vampireChargeJump) {
					syncedConfigValues.setVampireChargeJump(NyctoConfig.vampireChargeJump);
					changed = true;
				}
			}
			{
				boolean current = syncedConfigValues.hasVampireStepHeight();
				if (current != NyctoConfig.vampireStepHeight) {
					syncedConfigValues.setVampireStepHeight(NyctoConfig.vampireStepHeight);
					changed = true;
				}
			}
			if (changed) {
				SyncConfigValuesPayload.send(NyctoConfig.vampireChargeJump, NyctoConfig.vampireStepHeight);
			}
		}
	}
}
