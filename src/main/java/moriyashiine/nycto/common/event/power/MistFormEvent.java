/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.power.vampire.MistFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;

public class MistFormEvent {
	public static class AllowDamage implements ServerLivingEntityEvents.AllowDamage {
		@Override
		public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
			if (entity instanceof ServerPlayerEntity player) {
				MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
				if (mistFormComponent.isEnabled()) {
					if (source.isIn(DamageTypeTags.IS_FALL)) {
						return false;
					}
					player.hurtTime = player.maxHurtTime = 10;
					disable(player, mistFormComponent);
					if (!NyctoUtil.bypassesBloodVeil(source)) {
						return false;
					}
				}
			}
			if (source.getAttacker() instanceof ServerPlayerEntity player) {
				MistFormComponent mistFormComponent = ModEntityComponents.MIST_FORM.get(player);
				if (mistFormComponent.isEnabled()) {
					disable(player, mistFormComponent);
				}
			}
			return true;
		}

		private static void disable(ServerPlayerEntity player, MistFormComponent mistFormComponent) {
			NyctoAPI.setPowerCooldown(player, ModPowers.MIST_FORM, ModPowers.MIST_FORM.getCooldown());
			mistFormComponent.toggle();
		}
	}

	public static class ForceCritical implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress) {
			return ModEntityComponents.MIST_FORM.get(attacker).isEnabled() ? TriState.TRUE : TriState.DEFAULT;
		}
	}
}
