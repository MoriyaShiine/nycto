/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import org.jetbrains.annotations.Nullable;
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
	public void readData(ReadView readView) {
		lifeStealer = readView.read("LifeStealer", Uuids.CODEC).orElse(null);
		ticksToBlock = readView.getInt("TicksToBlock", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		if (lifeStealer != null) {
			writeView.put("LifeStealer", Uuids.CODEC, lifeStealer);
		}
		writeView.putInt("TicksToBlock", ticksToBlock);
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
			this.lifeStealer = lifeStealer.getUuid();
		}
	}

	public boolean isHealingBlocked() {
		return ticksToBlock > 0;
	}

	public boolean canStealLife(Entity attacker) {
		return attacker != null && attacker.getUuid().equals(lifeStealer);
	}
}
