/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.world.power.vampire.HypnotizePower;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.OptionalInt;

public class HypnotizeClientEvent {
	private static final Minecraft client = Minecraft.getInstance();

	private static boolean shouldHighlight(Player player, LivingEntity living, int range) {
		return player != living && player != null && !player.isSpectator() && living.distanceTo(player) <= range && NyctoClientAPI.isHighlightingPower(player, ModPowers.HYPNOTIZE) && HypnotizePower.canUseOn(player, living);
	}

	public static class Tick implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (level.isClientSide() && entity instanceof LivingEntity living && shouldHighlight(client.player, living, HypnotizePower.RANGE * 2)) {
				SLibClientUtils.addAnchoredParticle(living, client.player.isShiftKeyDown() ? ModParticleTypes.HYPNOSIS_INDICATOR_INVERSE : ModParticleTypes.HYPNOSIS_INDICATOR, living.getBbHeight() + 0.5, 1 / 6F, 1 / 24F);
				if (living.tickCount % 4 == 0) {
					SLibClientUtils.addParticles(living, ModParticleTypes.HYPNOSIS_STAR, 1, ParticleAnchor.BODY);
					if (living.tickCount % 12 == 0) {
						SLibClientUtils.addParticles(living, ModParticleTypes.HYPNOSIS_SMALL, 1, ParticleAnchor.BODY);
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
