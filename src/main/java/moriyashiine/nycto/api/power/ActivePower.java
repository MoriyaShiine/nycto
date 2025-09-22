/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.power;

import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public abstract class ActivePower extends Power {
	private final int cooldown;

	public ActivePower(int cooldown) {
		this.cooldown = cooldown;
	}

	public final int getCooldown() {
		return cooldown;
	}

	public boolean canUse(PlayerEntity player) {
		return true;
	}

	public boolean shouldApplyCooldown(PlayerEntity player) {
		return true;
	}

	public boolean shouldBroadcastUseSound() {
		return true;
	}

	public void sendUseSoundPacket(ServerPlayerEntity origin, SoundEvent soundEvent, ServerPlayerEntity target) {
		PlayAnchoredSoundPayload.send(target, origin, soundEvent);
	}

	@Nullable
	public abstract SoundEvent getUseSound(PlayerEntity player);

	public abstract void use(ServerWorld world, ServerPlayerEntity player);

	public final void playUseSound(ServerPlayerEntity player) {
		@Nullable SoundEvent useSound = getUseSound(player);
		if (useSound != null) {
			sendUseSoundPacket(player, useSound, player);
			if (shouldBroadcastUseSound()) {
				PlayerLookup.tracking(player).forEach(receiver -> sendUseSoundPacket(player, useSound, receiver));
			}
		}
	}
}
