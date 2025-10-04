/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.power.NegativePower;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class VilePresencePower extends NegativePower {
	@Override
	public void tick(ServerPlayerEntity player) {
		if (!player.isCreative()) {
			player.getEntityWorld().getEntitiesByType(EntityType.CAT, player.getBoundingBox().expand(16), VilePresencePower::canBeAffected).forEach(cat -> {
				if ((cat.age + cat.getId()) % 100 == 0) {
					cat.hiss();
				}
			});
			player.getEntityWorld().getEntitiesByType(EntityType.WOLF, player.getBoundingBox().expand(16), VilePresencePower::canBeAffected).forEach(wolf -> {
				if ((wolf.age + wolf.getId()) % 100 == 0) {
					wolf.chooseRandomAngerTime();
				}
			});
		}
	}

	public static boolean isAffected(Entity entity, PlayerEntity player) {
		return canBeAffected(entity) && shouldApply(player);
	}

	public static boolean isAffected(Entity entity, double distance) {
		if (canBeAffected(entity)) {
			for (PlayerEntity player : entity.getEntityWorld().getPlayers()) {
				if (player.distanceTo(entity) <= distance && shouldApply(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean shouldApply(PlayerEntity player) {
		return NyctoUtil.isSurvival(player) && NyctoAPI.hasPower(player, ModPowers.VILE_PRESENCE);
	}

	private static boolean canBeAffected(Entity entity) {
		if (entity.getType().isIn(ModEntityTypeTags.VILE_PRESENCE_IMMUNE)) {
			return false;
		}
		if (entity instanceof LivingEntity living && living.hasStatusEffect(ModStatusEffects.HYPNOTIZED)) {
			return false;
		}
		if (entity instanceof TameableEntity tameable && tameable.isTamed()) {
			return false;
		}
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		return vampiricThrallComponent == null || !vampiricThrallComponent.isThralled();
	}
}
