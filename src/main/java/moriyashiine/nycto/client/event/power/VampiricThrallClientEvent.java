/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.power.vampire.VampiricThrallPower;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.OptionalInt;

public class VampiricThrallClientEvent {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private static boolean shouldHighlight(PlayerEntity player, MobEntity mob) {
		return player != null && !player.isSpectator() && NyctoClientAPI.isHighlightingPower(player, ModPowers.VAMPIRIC_THRALL) && VampiricThrallPower.canBeThralled(player, mob);
	}

	public static class Tick implements ClientTickEvents.EndWorldTick {
		@Override
		public void onEndTick(ClientWorld world) {
			for (Entity entity : world.getEntities()) {
				if (entity instanceof MobEntity mob && mob.distanceTo(client.player) < 16 && shouldHighlight(client.player, mob)) {
					SLibClientUtils.addAnchoredParticle(mob, ModParticleTypes.HYPNOSIS_INDICATOR, mob.getHeight() + 0.5, 1 / 6F, 1 / 24F);
					if (mob.age % 4 == 0) {
						SLibClientUtils.addParticles(mob, ModParticleTypes.HYPNOSIS_STAR, 1, ParticleAnchor.BODY);
						if (mob.age % 12 == 0) {
							SLibClientUtils.addParticles(mob, ModParticleTypes.HYPNOSIS_SMALL, 1, ParticleAnchor.BODY);
						}
					}
				}
			}
		}
	}

	public static class Outline implements OutlineEntityEvent {
		private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x7F00FF));

		@Override
		public OutlineData getOutlineData(Entity entity) {
			if (client.targetedEntity == entity && entity instanceof MobEntity mob && shouldHighlight(client.player, mob)) {
				return DATA;
			}
			return null;
		}
	}
}
