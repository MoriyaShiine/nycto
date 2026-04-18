/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class CarnageComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final AttributeModifier ATTACK_DAMAGE_MODIFIER = new AttributeModifier(Nycto.id("carnage_damage"), 2, AttributeModifier.Operation.ADD_VALUE);

	private static final int MAX_TICKS = 300;

	private final LivingEntity obj;
	private int ticks = 0;

	public CarnageComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		ticks = input.getIntOr("Ticks", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("Ticks", ticks);
	}

	@Override
	public void tick() {
		if (ticks > 0) {
			if (ticks > 20 && NyctoAPI.hasSunDebuff(obj)) {
				ticks = 20;
			}
			if (--ticks == 0 && !obj.level().isClientSide()) {
				obj.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER);
			}
		}
	}

	public void sync() {
		ModEntityComponents.CARNAGE.sync(obj);
	}

	public boolean isActive() {
		return ticks > 0;
	}

	public float getOverlayOpacity(float maxOpacity) {
		float inverseTicks = MAX_TICKS - ticks;
		if (ticks < 20) {
			return Mth.lerp(ticks / 20F, 0, maxOpacity);
		} else if (inverseTicks < 20) {
			return Mth.lerp(inverseTicks / 20, 0, maxOpacity);
		}
		return maxOpacity;
	}

	public void use() {
		ticks = MAX_TICKS;
		obj.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(ATTACK_DAMAGE_MODIFIER);
		sync();
	}
}
