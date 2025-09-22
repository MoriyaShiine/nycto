/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModSoundEventTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyVariable(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZJ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float nycto$keenSenses(float value, double x, double y, double z, SoundEvent event) {
		return getModifiedVolume(value, event);
	}

	@ModifyVariable(method = "playSoundFromEntity", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float nycto$keenSenses(float value, @Nullable Entity source, Entity entity, RegistryEntry<SoundEvent> sound) {
		return getModifiedVolume(value, sound.value());
	}

	@ModifyVariable(method = "playSoundFromEntityClient", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float nycto$keenSenses(float value, Entity entity, SoundEvent sound) {
		return getModifiedVolume(value, sound);
	}

	@Unique
	private float getModifiedVolume(float volume, SoundEvent sound) {
		if (!ModSoundEventTags.isIn(sound, ModSoundEventTags.NOT_MUFFLED)) {
			PlayerEntity player = client.player;
			if (player != null && !player.isSpectator() && ModEntityComponents.KEEN_SENSES.get(player).isEnabled()) {
				return volume / 6F;
			}
		}
		return volume;
	}
}
