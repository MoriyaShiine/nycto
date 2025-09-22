/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.OptionalInt;

public class BloodFlechettesClientEvent implements OutlineEntityEvent {
	private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x7F0000));

	@Override
	public OutlineData getOutlineData(Entity entity) {
		if (entity instanceof LivingEntity living && ModEntityComponents.HEAL_BLOCK.get(living).canStealLife(MinecraftClient.getInstance().player)) {
			return DATA;
		}
		return null;
	}
}
