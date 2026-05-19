/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.nycto.common.world.power.vampire.HypnotizePower;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Mob;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HypnotizedComponent extends HasOwnerComponent implements ServerTickingComponent {
	public HypnotizedComponent(Mob obj) {
		super(obj);
	}

	@Override
	public void serverTick() {
		if (obj.hasEffect(NyctoMobEffects.HYPNOTIZED)) {
			if (ownerUuid == null && obj.tickCount % 20 == 0 && obj.getType() != EntityTypes.PLAYER) {
				setOwner(obj.level().getNearestPlayer(obj, 16));
			}
		} else if (ownerUuid != null) {
			HypnotizePower.forget(obj);
			setOwner(null);
		}
	}

	@Override
	public void sync() {
		NyctoEntityComponents.HYPNOTIZED.sync(obj);
	}
}
