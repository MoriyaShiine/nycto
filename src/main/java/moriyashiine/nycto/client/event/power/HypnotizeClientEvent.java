/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.power.vampire.HypnotizePower;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.OptionalInt;

public class HypnotizeClientEvent {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private static boolean shouldHighlight(PlayerEntity player, LivingEntity living, int range) {
		return player != living && player != null && !player.isSpectator() && living.distanceTo(player) <= range && NyctoClientAPI.isHighlightingPower(player, ModPowers.HYPNOTIZE) && HypnotizePower.canBeHypnotized(player, living);
	}

	public static class Tick implements ClientTickEvents.EndWorldTick {
		@Override
		public void onEndTick(ClientWorld world) {
			for (Entity entity : world.getEntities()) {
				if (entity instanceof LivingEntity living && shouldHighlight(client.player, living, HypnotizePower.RANGE * 2)) {
					SLibClientUtils.addAnchoredParticle(living, ModParticleTypes.HYPNOSIS_INDICATOR, living.getHeight() + 0.5, 1 / 6F, 1 / 24F);
					if (living.age % 4 == 0) {
						SLibClientUtils.addParticles(living, ModParticleTypes.HYPNOSIS_STAR, 1, ParticleAnchor.BODY);
						if (living.age % 12 == 0) {
							SLibClientUtils.addParticles(living, ModParticleTypes.HYPNOSIS_SMALL, 1, ParticleAnchor.BODY);
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
			if (entity instanceof LivingEntity living && shouldHighlight(client.player, living, HypnotizePower.RANGE)) {
				return DATA;
			}
			return null;
		}
	}
}
