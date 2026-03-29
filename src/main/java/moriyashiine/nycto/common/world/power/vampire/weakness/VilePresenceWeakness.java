/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.Weakness;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public class VilePresenceWeakness extends Weakness {
	@Override
	public void tick(ServerPlayer player) {
		if (!player.isCreative()) {
			player.level().getEntities(EntityType.CAT, player.getBoundingBox().inflate(16), VilePresenceWeakness::canBeAffected).forEach(cat -> {
				if ((cat.tickCount + cat.getId()) % 100 == 0) {
					cat.hiss();
				}
			});
			player.level().getEntities(EntityType.WOLF, player.getBoundingBox().inflate(16), VilePresenceWeakness::canBeAffected).forEach(wolf -> {
				if ((wolf.tickCount + wolf.getId()) % 100 == 0) {
					wolf.startPersistentAngerTimer();
				}
			});
		}
	}

	public static boolean isAffected(Entity entity, Player player) {
		return canBeAffected(entity) && shouldApply(player);
	}

	public static boolean isAffected(Entity entity, double distance) {
		if (canBeAffected(entity)) {
			for (Player player : entity.level().players()) {
				if (player.distanceTo(entity) <= distance && shouldApply(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean shouldApply(Player player) {
		return player.slib$isSurvival() && NyctoAPI.hasPower(player, ModPowers.VILE_PRESENCE);
	}

	private static boolean canBeAffected(Entity entity) {
		if (entity.is(ModEntityTypeTags.VILE_PRESENCE_IMMUNE)) {
			return false;
		}
		if (entity instanceof LivingEntity living && living.hasEffect(ModMobEffects.HYPNOTIZED)) {
			return false;
		}
		if (entity instanceof TamableAnimal tameable && tameable.isTame()) {
			return false;
		}
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		return vampiricThrallComponent == null || !vampiricThrallComponent.hasOwner();
	}
}
