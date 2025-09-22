/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class VampireActivePower extends ActivePower {
	public VampireActivePower(int cooldown) {
		super(cooldown);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
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
