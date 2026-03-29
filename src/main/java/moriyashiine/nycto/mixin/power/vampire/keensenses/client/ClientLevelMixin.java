/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModSoundEventTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyVariable(method = "playSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZJ)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSenses(float volume, double x, double y, double z, SoundEvent sound) {
		return getModifiedVolume(volume, sound);
	}

	@ModifyVariable(method = "playSeededSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSenses(float volume, @Nullable Entity except, Entity sourceEntity, Holder<SoundEvent> sound) {
		return getModifiedVolume(volume, sound.value());
	}

	@ModifyVariable(method = "playLocalSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private float nycto$keenSenses(float volume, Entity sourceEntity, SoundEvent sound) {
		return getModifiedVolume(volume, sound);
	}

	@Unique
	private float getModifiedVolume(float volume, SoundEvent sound) {
		if (!ModSoundEventTags.is(sound, ModSoundEventTags.NOT_MUFFLED)) {
			Player player = minecraft.player;
			if (player != null && !player.isSpectator() && ModEntityComponents.KEEN_SENSES.get(player).isEnabled()) {
				return volume / 6F;
			}
		}
		return volume;
	}
}
