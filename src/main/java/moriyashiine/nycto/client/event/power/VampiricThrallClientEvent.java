/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.world.power.vampire.VampiricThrallPower;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.OptionalInt;

public class VampiricThrallClientEvent {
	private static final Minecraft client = Minecraft.getInstance();

	private static boolean shouldHighlight(Player player, Mob mob) {
		return player != null && !player.isSpectator() && NyctoClientAPI.isHighlightingPower(player, ModPowers.VAMPIRIC_THRALL) && VampiricThrallPower.canBeThralled(player, mob);
	}

	public static class Tick implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (level.isClientSide() && entity instanceof Mob mob && mob.distanceTo(client.player) < 16 && shouldHighlight(client.player, mob)) {
				SLibClientUtils.addAnchoredParticle(mob, ModParticleTypes.HYPNOSIS_INDICATOR, mob.getBbHeight() + 0.5, 1 / 6F, 1 / 24F);
				if (mob.tickCount % 4 == 0) {
					SLibClientUtils.addParticles(mob, ModParticleTypes.HYPNOSIS_STAR, 1, ParticleAnchor.BODY);
					if (mob.tickCount % 12 == 0) {
						SLibClientUtils.addParticles(mob, ModParticleTypes.HYPNOSIS_SMALL, 1, ParticleAnchor.BODY);
					}
				}
			}
		}
	}

	public static class Outline implements OutlineEntityEvent {
		private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x7F00FF));

		@Override
		public OutlineData getOutlineData(Entity entity) {
			if (client.crosshairPickEntity == entity && entity instanceof Mob mob && shouldHighlight(client.player, mob)) {
				return DATA;
			}
			return null;
		}
	}
}
