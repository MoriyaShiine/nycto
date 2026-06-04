/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.sound;

import moriyashiine.nycto.neoforge.network.BloodrushClientState;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public final class BloodrushSoundInstance extends AbstractTickableSoundInstance {
	private final Entity entity;

	public BloodrushSoundInstance(Entity entity, SoundEvent sound) {
		super(sound, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
		this.entity = entity;
		looping = true;
		delay = 0;
		volume = 1.0F;
		pitch = 1.0F;
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
	}

	@Override
	public void tick() {
		if (entity.isRemoved() || !BloodrushClientState.isActive(entity)) {
			stop();
			return;
		}
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
	}
}
