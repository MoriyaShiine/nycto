/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.sound;

import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public final class BloodrushClientSound {
	private BloodrushClientSound() {
	}

	public static void play(Entity entity) {
		Minecraft.getInstance().getSoundManager().play(new BloodrushSoundInstance(entity, NyctoSoundEvents.BLOODRUSH_USE.get()));
	}
}
