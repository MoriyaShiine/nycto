/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.tag.ModStatusEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModStatusEffectTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<StatusEffect> {
	public ModStatusEffectTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.STATUS_EFFECT, registriesFuture, statusEffect -> RegistryKey.of(RegistryKeys.STATUS_EFFECT, Registries.STATUS_EFFECT.getId(statusEffect)));
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModStatusEffectTags.INFECTION)
				.add(ModStatusEffects.VAMPIRISM.value());
		valueLookupBuilder(TagKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of("enchancement", "chaos_unchoosable")))
				.addTag(ModStatusEffectTags.INFECTION);
	}
}
