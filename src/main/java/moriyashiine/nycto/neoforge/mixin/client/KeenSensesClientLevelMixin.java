/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.client.KeenSensesClientState;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientLevel.class)
public class KeenSensesClientLevelMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyVariable(method = "playSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZJ)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSensesMufflePositionedSound(float volume, double x, double y, double z, SoundEvent sound) {
		return nycto$modifiedVolume(volume, sound);
	}

	@ModifyVariable(method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSensesMuffleSeededSound(float volume, @Nullable Player except, Entity sourceEntity, Holder<SoundEvent> sound) {
		return nycto$modifiedVolume(volume, sound.value());
	}

	@ModifyVariable(method = "playLocalSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSensesMuffleLocalSound(float volume, Entity sourceEntity, SoundEvent sound) {
		return nycto$modifiedVolume(volume, sound);
	}

	@Unique
	private float nycto$modifiedVolume(float volume, SoundEvent sound) {
		Player player = minecraft.player;
		if (player != null && !player.isSpectator() && KeenSensesClientState.isActive(player) && !BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound).is(NyctoTags.NOT_MUFFLED)) {
			return volume / 6F;
		}
		return volume;
	}
}
