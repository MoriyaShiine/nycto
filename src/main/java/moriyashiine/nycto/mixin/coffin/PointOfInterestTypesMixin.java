/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Optional;

@Mixin(PointOfInterestTypes.class)
public class PointOfInterestTypesMixin {
	@Shadow
	@Final
	private static Map<BlockState, RegistryEntry<PointOfInterestType>> POI_STATES_TO_TYPE;

	@ModifyReturnValue(method = "getTypeForState", at = @At("RETURN"))
	private static Optional<RegistryEntry<PointOfInterestType>> nycto$coffin(Optional<RegistryEntry<PointOfInterestType>> original, BlockState state) {
		if (state.isIn(ModBlockTags.COFFINS)) {
			return POI_STATES_TO_TYPE.values().stream().filter(entry -> entry.matchesKey(PointOfInterestTypes.HOME)).findFirst();
		}
		return original;
	}

	@ModifyReturnValue(method = "isPointOfInterest", at = @At("RETURN"))
	private static boolean nycto$coffin(boolean original, BlockState state) {
		return original || state.isIn(ModBlockTags.COFFINS);
	}
}
