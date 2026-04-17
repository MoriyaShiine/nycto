/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.UUID;

public class HealBlockComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final LivingEntity obj;
	private UUID lifeStealer = null;
	private int ticksToBlock = 0;

	public HealBlockComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		lifeStealer = input.read("LifeStealer", UUIDUtil.AUTHLIB_CODEC).orElse(null);
		ticksToBlock = input.getIntOr("TicksToBlock", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.storeNullable("LifeStealer", UUIDUtil.AUTHLIB_CODEC, lifeStealer);
		output.putInt("TicksToBlock", ticksToBlock);
	}

	@Override
	public void tick() {
		if (ticksToBlock > 0 && --ticksToBlock == 0) {
			lifeStealer = null;
		}
	}

	public void sync() {
		ModEntityComponents.HEAL_BLOCK.sync(obj);
	}

	public void setTicksToBlock(int ticksToBlock) {
		this.ticksToBlock = ticksToBlock;
	}

	public void setLifeStealer(@Nullable Entity lifeStealer) {
		if (lifeStealer != null) {
			this.lifeStealer = lifeStealer.getUUID();
		}
	}

	public boolean isHealingBlocked() {
		return ticksToBlock > 0;
	}

	public boolean canStealLife(Entity attacker) {
		return attacker != null && attacker.getUUID().equals(lifeStealer);
	}
}
