/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoLevelComponents;
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
	public static void init() {
		ClientTickEvents.END_LEVEL_TICK.register(new Tick());
		OutlineEntityEvent.EVENT.register(new Outline());
	}

	private static final Minecraft client = Minecraft.getInstance();

	private static int keenSensesDistance = 0;

	public static boolean shouldRenderShader() {
		return client.player != null && NyctoEntityComponents.KEEN_SENSES.get(client.player).isEnabled();
	}

	public static boolean shouldHighlight(Entity entity) {
		if (keenSensesDistance > 0 && entity instanceof LivingEntity living && living.slib$exists()) {
			if (living.slib$isPlayer() || living instanceof Mob) {
				int distance = keenSensesDistance;
				MistFormComponent mistForm = NyctoEntityComponents.MIST_FORM.getNullable(living);
				if (mistForm != null && mistForm.isEnabled()) {
					distance /= 4;
				}
				return living.distanceTo(client.player) <= distance && !NyctoUtil.hasGarlicAura(living) && NyctoLevelComponents.AURA.get(living.level()).getGarlicWreaths().stream().noneMatch(pos -> pos.closerToCenterThan(living.position(), AuraComponent.RADIUS));
			}
		}
		return false;
	}

	private static class Tick implements ClientTickEvents.EndLevelTick {
		@Override
		public void onEndTick(ClientLevel level) {
			keenSensesDistance = client.player == null ? 0 : NyctoEntityComponents.KEEN_SENSES.get(client.player).getDistance();
		}
	}

	private static class Outline implements OutlineEntityEvent {
		@Override
		public OutlineData getOutlineData(Entity entity) {
			if (shouldHighlight(entity)) {
				return new OutlineData(TriState.TRUE, OptionalInt.of(getKeenSensesColor(entity)));
			}
			return null;
		}

		private static int getKeenSensesColor(Entity entity) {
			if (NyctoAPI.hasQualityBlood(entity)) {
				return 0xFF0000;
			} else if (NyctoAPI.hasBlood(entity)) {
				return 0xFFFFFF;
			}
			return 0x3F3F3F;
		}
	}
}
