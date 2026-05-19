/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.Weakness;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public class RichTastesWeakness extends Weakness {
	@Override
	public void tick(ServerPlayer player) {
		if (!player.isCreative() && (player.getId() + player.tickCount) % 10 == 0 && NyctoEntityComponents.BLOOD.get(player).getTicksSinceLastFill() > 320) {
			for (Entity entity : player.level().getEntities(player, new AABB(player.blockPosition()).inflate(8), foundEntity -> NyctoAPI.hasQualityBlood(foundEntity) && player.distanceTo(foundEntity) <= 8 && !NyctoAPI.isVampire(foundEntity))) {
				if (player.hasLineOfSight(entity)) {
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 30, 0, true, false));
					break;
				}
			}
		}
	}
}
