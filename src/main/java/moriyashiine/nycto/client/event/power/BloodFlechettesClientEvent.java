/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.OptionalInt;

public class BloodFlechettesClientEvent implements OutlineEntityEvent {
	public static void init() {
		OutlineEntityEvent.EVENT.register(new BloodFlechettesClientEvent());
	}

	private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x7F0000));

	@Override
	public OutlineData getOutlineData(Entity entity) {
		if (entity instanceof LivingEntity living && NyctoEntityComponents.HEAL_BLOCK.get(living).canStealLife(Minecraft.getInstance().player)) {
			return DATA;
		}
		return null;
	}
}
