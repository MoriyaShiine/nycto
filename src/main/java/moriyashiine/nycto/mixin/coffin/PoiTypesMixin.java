/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Optional;

@Mixin(PoiTypes.class)
public class PoiTypesMixin {
	@Shadow
	@Final
	private static Map<BlockState, Holder<PoiType>> TYPE_BY_STATE;

	@ModifyReturnValue(method = "forState", at = @At("RETURN"))
	private static Optional<Holder<PoiType>> nycto$coffin(Optional<Holder<PoiType>> original, BlockState state) {
		if (state.is(ModBlockTags.COFFINS)) {
			return TYPE_BY_STATE.values().stream().filter(entry -> entry.is(PoiTypes.HOME)).findFirst();
		}
		return original;
	}

	@ModifyReturnValue(method = "hasPoi", at = @At("RETURN"))
	private static boolean nycto$coffin(boolean original, BlockState state) {
		return original || state.is(ModBlockTags.COFFINS);
	}
}
