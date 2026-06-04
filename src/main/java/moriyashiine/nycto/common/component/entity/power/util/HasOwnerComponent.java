/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.util;

import moriyashiine.nycto.common.component.NyctoEntityComponentKey;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public abstract class HasOwnerComponent implements moriyashiine.nycto.common.component.NyctoPersistedComponent {
	protected final Mob obj;
	@Nullable
	protected UUID ownerUuid = null;

	public HasOwnerComponent(Mob obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		ownerUuid = input.read("Owner", UUIDUtil.AUTHLIB_CODEC).orElse(null);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.storeNullable("Owner", UUIDUtil.AUTHLIB_CODEC, ownerUuid);
	}

	public boolean hasOwner() {
		return ownerUuid != null;
	}

	public boolean isOwner(Entity entity) {
		return entity != null && entity.getUUID().equals(ownerUuid);
	}

	public void setOwner(Entity entity) {
		if (entity == null) {
			ownerUuid = null;
		} else {
			ownerUuid = entity.getUUID();
		}
		sync();
	}

	public static boolean isOwner(Entity entity, Entity potentialOwner) {
		for (NyctoEntityComponentKey<? extends HasOwnerComponent> key : ModEntityComponents.ownerComponents()) {
			HasOwnerComponent hasOwnerComponent = key.getNullable(entity);
			if (hasOwnerComponent != null && hasOwnerComponent.isOwner(potentialOwner)) {
				return true;
			}
		}
		return false;
	}

	public abstract void sync();
}
