/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformationEvent implements ServerPlayerEvents.CopyFrom {
	@Override
	public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
		NyctoAPI.getTransformation(oldPlayer).getAttributeModifiers(oldPlayer).attributeModifiers().forEach(pair -> newPlayer.getAttributeInstance(pair.getLeft()).addPersistentModifier(pair.getRight()));
	}
}
