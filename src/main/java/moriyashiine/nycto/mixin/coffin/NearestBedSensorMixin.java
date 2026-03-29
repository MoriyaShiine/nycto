/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.NearestBedSensor;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

@Mixin(NearestBedSensor.class)
public class NearestBedSensorMixin {
	@ModifyArg(method = "doTick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/behavior/AcquirePoi;findPathToPois(Lnet/minecraft/world/entity/Mob;Ljava/util/Set;)Lnet/minecraft/world/level/pathfinder/Path;"), index = 1)
	private Set<Pair<Holder<PoiType>, BlockPos>> nycto$coffin(Set<Pair<Holder<PoiType>, BlockPos>> pois, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) Mob body) {
		pois.removeIf(pair -> {
			BlockPos pos = pair.getSecond();
			boolean isCoffin = level.getBlockState(pos).is(ModBlockTags.COFFINS);
			return NyctoAPI.isVampire(body) != isCoffin;
		});
		return pois;
	}
}
