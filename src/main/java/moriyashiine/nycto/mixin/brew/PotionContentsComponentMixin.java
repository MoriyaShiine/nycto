/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.brew;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModPotions;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PotionContentsComponent.class)
public abstract class PotionContentsComponentMixin {
	@Shadow
	public abstract Optional<RegistryEntry<Potion>> potion();

	@Inject(method = "apply", at = @At("TAIL"))
	private void nycto$brew(LivingEntity user, float durationMultiplier, CallbackInfo ci) {
		if (user instanceof ServerPlayerEntity player) {
			if (NyctoAPI.isVampire(player) && potion().stream().anyMatch(potion -> potion == ModPotions.GARLIC || potion == ModPotions.LONG_GARLIC || potion == ModPotions.STRONG_GARLIC)) {
				NyctoAPI.partiallyCureTransformation(player, ModPowerTags.VAMPIRE_CHOOSABLE);
			}
		}
	}
}
