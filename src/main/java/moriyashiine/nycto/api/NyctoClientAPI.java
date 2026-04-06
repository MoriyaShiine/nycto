/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api;

import moriyashiine.nycto.api.renderer.entity.vampiricthrall.VampiricThrallRenderer;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricVexComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NyctoClientAPI {
	public static boolean isHighlightingPower(Player player, Power power) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		return PowerClientEvent.isActive(player, transformationComponent) && transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower() == power;
	}

	public static boolean hasVampiricThrallTexture(LivingEntity entity) {
		VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrallComponent != null && vampiricThrallComponent.hasOwner()) {
			return true;
		}
		VampiricVexComponent vampiricVexComponent = ModEntityComponents.VAMPIRIC_VEX.getNullable(entity);
		return vampiricVexComponent != null && vampiricVexComponent.hasOwner();
	}

	public static <T extends LivingEntity> void registerVampiricThrallRenderer(EntityType<T> type, VampiricThrallRenderer<T> renderer) {
		VampiricThrallRenderer.CUSTOM_RENDERERS.put(type, renderer);
	}
}
