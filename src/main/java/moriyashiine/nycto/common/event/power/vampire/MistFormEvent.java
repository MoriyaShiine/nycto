/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MistFormEvent {
	public static class AllowDamage implements ServerLivingEntityEvents.AllowDamage {
		@Override
		public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
			if (entity instanceof ServerPlayer player) {
				MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
				if (mistFormComponent.isEnabled()) {
					if (source.is(DamageTypeTags.IS_FALL)) {
						return false;
					}
					player.hurtTime = player.hurtDuration = 10;
					disable(player, mistFormComponent);
					if (!NyctoUtil.bypassesBloodVeil(source)) {
						return false;
					}
				}
			}
			if (source.getEntity() instanceof ServerPlayer player) {
				MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
				if (mistFormComponent.isEnabled()) {
					disable(player, mistFormComponent);
				}
			}
			return true;
		}

		private static void disable(ServerPlayer player, MistFormComponent mistFormComponent) {
			NyctoAPI.setPowerCooldown(player, ModPowers.MIST_FORM, ModPowers.MIST_FORM.getCooldown());
			mistFormComponent.toggle();
		}
	}

	public static class ForceCritical implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(Player attacker, Entity target, float attackCooldownProgress) {
			return ModEntityComponents.MIST_FORM.get(attacker).isEnabled() ? TriState.TRUE : TriState.DEFAULT;
		}
	}
}
