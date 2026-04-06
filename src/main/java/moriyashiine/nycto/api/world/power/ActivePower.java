/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.power;

import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public abstract class ActivePower extends Power {
	private final int cooldown;

	public ActivePower(int cooldown) {
		this.cooldown = cooldown;
	}

	public final int getCooldown() {
		return cooldown;
	}

	public boolean canUse(Player player) {
		return true;
	}

	public boolean shouldApplyCooldown(Player player) {
		return true;
	}

	public boolean shouldBroadcastUseSound() {
		return true;
	}

	public void sendUseSoundPayload(ServerPlayer receiver, ServerPlayer origin, SoundEvent sound) {
		PlayAnchoredSoundPayload.send(receiver, origin, sound);
	}

	public @Nullable
	abstract SoundEvent getUseSound(Player player);

	public abstract void use(ServerLevel level, ServerPlayer player);

	public final void playUseSound(ServerPlayer player) {
		SoundEvent useSound = getUseSound(player);
		if (useSound != null) {
			sendUseSoundPayload(player, player, useSound);
			if (shouldBroadcastUseSound()) {
				PlayerLookup.tracking(player).forEach(receiver -> sendUseSoundPayload(receiver, player, useSound));
			}
		}
	}
}
