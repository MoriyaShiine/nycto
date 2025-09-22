/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.power.vampire.HypnotizePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HypnotizedComponent extends HasOwnerComponent implements ServerTickingComponent {
	public HypnotizedComponent(MobEntity obj) {
		super(obj);
	}

	@Override
	public void serverTick() {
		if (obj.hasStatusEffect(ModStatusEffects.HYPNOTIZED)) {
			if (ownerUuid == null && obj.age % 20 == 0 && obj.getType() != EntityType.PLAYER) {
				setOwner(obj.getWorld().getClosestPlayer(obj, 16));
			}
		} else if (ownerUuid != null) {
			HypnotizePower.forget(obj);
			setOwner(null);
		}
	}
}
