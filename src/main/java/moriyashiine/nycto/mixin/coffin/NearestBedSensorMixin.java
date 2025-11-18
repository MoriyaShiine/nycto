/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.entity.ai.brain.sensor.NearestBedSensor;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

@Mixin(NearestBedSensor.class)
public class NearestBedSensorMixin {
	@ModifyArg(method = "sense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/MobEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/brain/task/FindPointOfInterestTask;findPathToPoi(Lnet/minecraft/entity/mob/MobEntity;Ljava/util/Set;)Lnet/minecraft/entity/ai/pathing/Path;"), index = 1)
	private Set<Pair<RegistryEntry<PointOfInterestType>, BlockPos>> nycto$coffin(Set<Pair<RegistryEntry<PointOfInterestType>, BlockPos>> pois, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) MobEntity mob) {
		pois.removeIf(pair -> {
			BlockPos pos = pair.getSecond();
			boolean isCoffin = world.getBlockState(pos).isIn(ModBlockTags.COFFINS);
			return NyctoAPI.isVampire(mob) != isCoffin;
		});
		return pois;
	}
}
