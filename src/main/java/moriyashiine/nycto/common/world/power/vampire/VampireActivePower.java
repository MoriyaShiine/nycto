/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class VampireActivePower extends ActivePower {
	public VampireActivePower(int cooldown) {
		super(cooldown);
	}

	@Override
	public boolean canUse(Player player) {
		return ModEntityComponents.BLOOD.get(player).getBlood() >= getCost(player);
	}

	public int getCost(LivingEntity entity) {
		int cost = getBaseCost(entity);
		if (cost > 0 && NyctoUtil.hasReducedPowerCost(entity)) {
			cost = Math.max(1, (int) (cost * 0.75F));
		}
		return cost;
	}

	protected abstract int getBaseCost(LivingEntity entity);
}
