/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.component.world.AuraComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModWorldComponents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

public class KeenSensesClientEvent {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private static int keenSensesDistance = 0;

	public static boolean shouldRenderShader() {
		PlayerEntity player = client.player;
		return player != null && ModEntityComponents.KEEN_SENSES.get(player).isEnabled();
	}

	public static class Tick implements ClientTickEvents.EndWorldTick {
		@Override
		public void onEndTick(ClientWorld world) {
			PlayerEntity player = client.player;
			keenSensesDistance = player == null ? 0 : ModEntityComponents.KEEN_SENSES.get(player).getDistance();
		}
	}

	public static class Outline implements OutlineEntityEvent {
		@Override
		public OutlineData getOutlineData(Entity entity) {
			if (shouldApply(entity)) {
				return new OutlineData(TriState.TRUE, OptionalInt.of(getKeenSensesColor(entity)));
			}
			return null;
		}

		private static boolean shouldApply(Entity entity) {
			if (keenSensesDistance > 0 && entity instanceof LivingEntity living && living.isPartOfGame()) {
				if (living.isPlayer() || living instanceof MobEntity) {
					int distance = keenSensesDistance;
					@Nullable MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.getNullable(living);
					if (mistFormComponent != null && mistFormComponent.isEnabled()) {
						distance /= 4;
					}
					return living.distanceTo(client.player) <= distance && !NyctoUtil.hasGarlicAura(living) && ModWorldComponents.AURA.get(living.getEntityWorld()).getGarlicWreaths().stream().noneMatch(pos -> pos.isWithinDistance(living.getEntityPos(), AuraComponent.RADIUS));
				}
			}
			return false;
		}

		private static int getKeenSensesColor(Entity entity) {
			if (entity.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD)) {
				return 0xFF0000;
			} else if (!entity.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD)) {
				return 0xFFFFFF;
			}
			return 0x3F3F3F;
		}
	}
}
