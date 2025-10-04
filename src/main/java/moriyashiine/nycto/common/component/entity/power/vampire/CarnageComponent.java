/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class CarnageComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER = new EntityAttributeModifier(Nycto.id("carnage_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE);

	private static final int MAX_TICKS = 300;

	private final LivingEntity obj;
	private int ticks = 0;

	public CarnageComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		ticks = readView.getInt("Ticks", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("Ticks", ticks);
	}

	@Override
	public void tick() {
		if (ticks > 0 && --ticks == 0 && !obj.getEntityWorld().isClient()) {
			obj.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER);
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
			return MathHelper.lerp(ticks / 20F, 0, maxOpacity);
		} else if (inverseTicks < 20) {
			return MathHelper.lerp(inverseTicks / 20, 0, maxOpacity);
		}
		return maxOpacity;
	}

	public void use() {
		ticks = MAX_TICKS;
		obj.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).addPersistentModifier(ATTACK_DAMAGE_MODIFIER);
		sync();
	}
}
