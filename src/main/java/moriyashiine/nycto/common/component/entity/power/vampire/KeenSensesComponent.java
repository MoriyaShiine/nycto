/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class KeenSensesComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final EntityAttributeModifier SPEED_BONUS = new EntityAttributeModifier(Nycto.id("keen_senses_speed"), 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private final PlayerEntity obj;
	private boolean enabled = false;
	private int distance = 0, renderTicks = 0;

	public KeenSensesComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		enabled = readView.getBoolean("Enabled", false);
		distance = readView.getInt("Distance", 0);
		renderTicks = readView.getInt("RenderTicks", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("Enabled", enabled);
		writeView.putInt("Distance", distance);
		writeView.putInt("RenderTicks", renderTicks);
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
		distance = MathHelper.clamp(distance, 0, actualMax);
		if (renderTicks > 0) {
			renderTicks--;
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (enabled && NyctoUtil.isSurvival(obj) && (obj.getId() + obj.age) % 200 == 0 && !ModEntityComponents.BLOOD.get(obj).drain(1)) {
			toggle();
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (enabled && obj.age % 20 == 0) {
			int time = 60;
			for (Entity entity : obj.getWorld().getOtherEntities(obj, obj.getBoundingBox().expand(12), EntityPredicates.EXCEPT_SPECTATOR.and(entity -> entity instanceof LivingEntity && entity.isAlive() && !entity.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD)))) {
				float distance = obj.distanceTo(entity);
				if (time != 40 && distance <= 10) {
					time = 40;
				}
				if (distance <= 4) {
					time = 20;
					break;
				}
			}
			if (obj.age % time == 0) {
				obj.playSound(ModSoundEvents.POWER_KEEN_SENSES_HEARTBEAT);
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
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.MOVEMENT_SPEED, SPEED_BONUS, enabled);
		sync();
	}
}
