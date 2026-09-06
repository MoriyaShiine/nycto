package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
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
	private int ticks = 0;

	public HealBlockComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		lifeStealer = input.read("LifeStealer", UUIDUtil.AUTHLIB_CODEC).orElse(null);
		ticks = input.getIntOr("Ticks", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.storeNullable("LifeStealer", UUIDUtil.AUTHLIB_CODEC, lifeStealer);
		output.putInt("Ticks", ticks);
	}

	@Override
	public void tick() {
		if (ticks > 0 && --ticks == 0) {
			lifeStealer = null;
		}
	}

	public void sync() {
		NyctoEntityComponents.HEAL_BLOCK.sync(obj);
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public void setLifeStealer(@Nullable Entity lifeStealer) {
		if (lifeStealer != null) {
			this.lifeStealer = lifeStealer.getUUID();
		}
	}

	public boolean canStealLife(Entity attacker) {
		return attacker != null && attacker.getUUID().equals(lifeStealer);
	}
}
