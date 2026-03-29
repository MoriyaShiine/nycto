/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class KeenSensesComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final AttributeModifier SPEED_BONUS = new AttributeModifier(Nycto.id("keen_senses_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private final Player obj;
	private boolean enabled = false;
	private int distance = 0, renderTicks = 0;

	public KeenSensesComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		enabled = input.getBooleanOr("Enabled", false);
		distance = input.getIntOr("Distance", 0);
		renderTicks = input.getIntOr("RenderTicks", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("Enabled", enabled);
		output.putInt("Distance", distance);
		output.putInt("RenderTicks", renderTicks);
	}

	@Override
	public void tick() {
		final int actualMax = 24;
		int maxDistance = enabled ? actualMax : 0;
		if (distance < maxDistance) {
			distance += 2;
		} else if (distance > maxDistance) {
			distance -= 4;
		}
		distance = Mth.clamp(distance, 0, actualMax);
		if (renderTicks > 0) {
			renderTicks--;
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (enabled && obj.slib$isSurvival() && (obj.getId() + obj.tickCount) % 200 == 0 && !ModEntityComponents.BLOOD.get(obj).drain(1)) {
			toggle();
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (enabled && obj.tickCount % 20 == 0) {
			int frequency = 60;
			for (Entity entity : obj.level().getEntities(obj, obj.getBoundingBox().inflate(12), EntitySelector.NO_SPECTATORS.and(entity -> entity instanceof LivingEntity && entity.isAlive() && !entity.is(ModEntityTypeTags.HAS_NO_BLOOD)))) {
				float distance = obj.distanceTo(entity);
				if (frequency != 40 && distance <= 10) {
					frequency = 40;
				}
				if (distance <= 4) {
					frequency = 20;
					break;
				}
			}
			if (obj.tickCount % frequency == 0) {
				obj.makeSound(ModSoundEvents.POWER_KEEN_SENSES_HEARTBEAT);
			}
		}
	}

	public void sync() {
		ModEntityComponents.KEEN_SENSES.sync(obj);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getDistance() {
		return distance;
	}

	public int getRenderTicks() {
		return renderTicks;
	}

	public void toggle() {
		if (!enabled) {
			ModEntityComponents.BLOOD.get(obj).drain(ModPowers.KEEN_SENSES.getCost(obj));
		}
		enabled = !enabled;
		renderTicks = 20;
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.MOVEMENT_SPEED, SPEED_BONUS, enabled);
		sync();
	}
}
