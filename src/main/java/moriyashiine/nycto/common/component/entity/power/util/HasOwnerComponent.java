/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.util;

import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.UUID;

public abstract class HasOwnerComponent implements AutoSyncedComponent {
	protected final Mob obj;
	@Nullable
	protected UUID ownerUuid = null;

	public HasOwnerComponent(Mob obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		ownerUuid = input.read("Owner", UUIDUtil.AUTHLIB_CODEC).orElse(null);
	}

	@Override
	public void writeData(ValueOutput output) {
		if (ownerUuid != null) {
			output.store("Owner", UUIDUtil.AUTHLIB_CODEC, ownerUuid);
		}
	}

	public boolean hasOwner() {
		return ownerUuid != null;
	}

	public boolean isOwner(Entity entity) {
		return entity != null && entity.getUUID().equals(ownerUuid);
	}

	public void setOwner(Player player) {
		if (player == null) {
			ownerUuid = null;
		} else {
			ownerUuid = player.getUUID();
		}
		sync();
	}

	public static boolean isOwner(Entity entity, Entity potentialOwner) {
		for (ComponentKey<?> key : entity.asComponentProvider().getComponentContainer().keys()) {
			if (entity.getComponent(key) instanceof HasOwnerComponent hasOwnerComponent && hasOwnerComponent.isOwner(potentialOwner)) {
				return true;
			}
		}
		return false;
	}

	public abstract void sync();
}
