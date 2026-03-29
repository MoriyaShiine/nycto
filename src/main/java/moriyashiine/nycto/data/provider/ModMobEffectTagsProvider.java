/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.tag.ModMobEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

import java.util.concurrent.CompletableFuture;

public class ModMobEffectTagsProvider extends FabricTagsProvider.FabricIntrinsicHolderTagsProvider<MobEffect> {
	public ModMobEffectTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.MOB_EFFECT, registriesFuture, effect -> ResourceKey.create(Registries.MOB_EFFECT, BuiltInRegistries.MOB_EFFECT.getKey(effect)));
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModMobEffectTags.INFECTION)
				.add(ModMobEffects.VAMPIRISM.value());
		valueLookupBuilder(TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath("enchancement", "chaos_unchoosable")))
				.addTag(ModMobEffectTags.INFECTION);
	}
}
