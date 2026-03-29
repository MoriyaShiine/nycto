/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.brew;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModPotions;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PotionContents.class)
public abstract class PotionContentsMixin {
	@Shadow
	public abstract Optional<Holder<Potion>> potion();

	@Inject(method = "applyToLivingEntity", at = @At("TAIL"))
	private void nycto$brew(LivingEntity entity, float durationScale, CallbackInfo ci) {
		if (entity instanceof ServerPlayer player) {
			if (NyctoAPI.isVampire(player) && potion().stream().anyMatch(potion -> potion == ModPotions.GARLIC || potion == ModPotions.LONG_GARLIC || potion == ModPotions.STRONG_GARLIC)) {
				NyctoAPI.partiallyCureTransformation(player, ModPowerTags.VAMPIRE_CHOOSABLE);
			}
		}
	}
}
