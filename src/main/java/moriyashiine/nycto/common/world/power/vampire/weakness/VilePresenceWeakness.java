/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.Weakness;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.tag.NyctoEntityTypeTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public class VilePresenceWeakness extends Weakness {
	@Override
	public void tick(ServerPlayer player) {
		if (!player.isCreative()) {
			player.level().getEntities(EntityTypes.CAT, player.getBoundingBox().inflate(16), VilePresenceWeakness::canPanic).forEach(cat -> {
				if ((cat.tickCount + cat.getId()) % 100 == 0) {
					cat.hiss();
				}
			});
			player.level().getEntities(EntityTypes.WOLF, player.getBoundingBox().inflate(16), VilePresenceWeakness::canPanic).forEach(wolf -> {
				if ((wolf.tickCount + wolf.getId()) % 100 == 0) {
					wolf.startPersistentAngerTimer();
				}
			});
		}
	}

	public static boolean isAffected(Entity entity, Player player) {
		return canPanic(entity) && shouldApply(player);
	}

	public static boolean isAffected(Entity entity, double distance) {
		if (canPanic(entity)) {
			for (Player player : entity.level().players()) {
				if (player.distanceTo(entity) <= distance && shouldApply(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean shouldApply(Player player) {
		return player.slib$isSurvival() && NyctoAPI.hasPower(player, NyctoPowers.VILE_PRESENCE);
	}

	public static boolean canPanic(Entity entity) {
		if (entity.is(NyctoEntityTypeTags.CANNOT_PANIC)) {
			return false;
		}
		if (entity instanceof LivingEntity living && living.hasEffect(NyctoMobEffects.HYPNOTIZED)) {
			return false;
		}
		if (entity instanceof TamableAnimal tameable && tameable.isTame()) {
			return false;
		}
		VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		return vampiricThrall == null || !vampiricThrall.hasOwner();
	}
}
