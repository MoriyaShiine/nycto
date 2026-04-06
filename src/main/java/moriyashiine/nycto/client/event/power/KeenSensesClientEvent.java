/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModLevelComponents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.OptionalInt;

public class KeenSensesClientEvent {
	private static final Minecraft client = Minecraft.getInstance();

	private static int keenSensesDistance = 0;

	public static boolean shouldRenderShader() {
		return !client.options.hideGui && client.player != null && ModEntityComponents.KEEN_SENSES.get(client.player).isEnabled();
	}

	public static class Tick implements ClientTickEvents.EndLevelTick {
		@Override
		public void onEndTick(ClientLevel level) {
			keenSensesDistance = client.player == null ? 0 : ModEntityComponents.KEEN_SENSES.get(client.player).getDistance();
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

		public static boolean shouldApply(Entity entity) {
			if (keenSensesDistance > 0 && entity instanceof LivingEntity living && living.slib$exists()) {
				if (living.slib$isPlayer() || living instanceof Mob) {
					int distance = keenSensesDistance;
					MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.getNullable(living);
					if (mistFormComponent != null && mistFormComponent.isEnabled()) {
						distance /= 4;
					}
					return living.distanceTo(client.player) <= distance && !NyctoUtil.hasGarlicAura(living) && ModLevelComponents.AURA.get(living.level()).getGarlicWreaths().stream().noneMatch(pos -> pos.closerToCenterThan(living.position(), AuraComponent.RADIUS));
				}
			}
			return false;
		}

		private static int getKeenSensesColor(Entity entity) {
			if (entity.is(ModEntityTypeTags.HAS_QUALITY_BLOOD)) {
				return 0xFF0000;
			} else if (!entity.is(ModEntityTypeTags.HAS_NO_BLOOD)) {
				return 0xFFFFFF;
			}
			return 0x3F3F3F;
		}
	}
}
