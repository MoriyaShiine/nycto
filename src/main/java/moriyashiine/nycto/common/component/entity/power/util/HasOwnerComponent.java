/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.UUID;

public abstract class HasOwnerComponent implements AutoSyncedComponent {
	protected final MobEntity obj;
	@Nullable
	protected UUID ownerUuid = null;

	public HasOwnerComponent(MobEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		ownerUuid = readView.read("OwnerUUID", Uuids.CODEC).orElse(null);
	}

	@Override
	public void writeData(WriteView writeView) {
		if (ownerUuid != null) {
			writeView.put("OwnerUUID", Uuids.CODEC, ownerUuid);
		}
	}

	public boolean isOwner(Entity entity) {
		return entity != null && entity.getUuid().equals(ownerUuid);
	}

	public void setOwner(PlayerEntity player) {
		if (player == null) {
			ownerUuid = null;
		} else {
			ownerUuid = player.getUuid();
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
