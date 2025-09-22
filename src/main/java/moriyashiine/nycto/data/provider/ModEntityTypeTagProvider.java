/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public ModEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModEntityTypeTags.HAS_QUALITY_BLOOD)
				.forceAddTag(EntityTypeTags.ILLAGER)
				.add(EntityType.PIGLIN)
				.add(EntityType.PIGLIN_BRUTE)
				.add(EntityType.PLAYER)
				.add(EntityType.VILLAGER)
				.add(EntityType.WANDERING_TRADER)
				.add(EntityType.WITCH)
				.add(ModEntityTypes.HUNTER)
				.add(ModEntityTypes.VAMPIRE);
		valueLookupBuilder(ModEntityTypeTags.HAS_NO_BLOOD)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.forceAddTag(EntityTypeTags.ARTHROPOD)
				.forceAddTag(EntityTypeTags.FROG_FOOD)
				.forceAddTag(EntityTypeTags.UNDEAD)
				.add(EntityType.ALLAY)
				.add(EntityType.ARMOR_STAND)
				.add(EntityType.BLAZE)
				.add(EntityType.BREEZE)
				.add(EntityType.CREAKING)
				.add(EntityType.CREEPER)
				.add(EntityType.ENDERMAN)
				.add(EntityType.GHAST)
				.add(EntityType.HAPPY_GHAST)
				.add(EntityType.IRON_GOLEM)
				.add(EntityType.SHULKER)
				.add(EntityType.SNOW_GOLEM)
				.add(EntityType.VEX)
				.add(EntityType.WARDEN);

		valueLookupBuilder(ModEntityTypeTags.BYPASSES_BLOOD_VEIL)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityType.WARDEN);
		valueLookupBuilder(ModEntityTypeTags.VAMPIRES)
				.add(ModEntityTypes.VAMPIRE);

		valueLookupBuilder(ModEntityTypeTags.CAN_BE_THRALLED)
				.add(EntityType.EVOKER)
				.add(EntityType.HORSE)
				.add(EntityType.ILLUSIONER)
				.add(EntityType.PIGLIN)
				.add(EntityType.PIGLIN_BRUTE)
				.add(EntityType.PILLAGER)
				.add(EntityType.VILLAGER)
				.add(EntityType.VINDICATOR)
				.add(EntityType.WANDERING_TRADER)
				.add(EntityType.WITCH)
				.add(EntityType.WOLF);
		valueLookupBuilder(ModEntityTypeTags.CANNOT_BE_HYPNOTIZED)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityType.WARDEN);
		valueLookupBuilder(ModEntityTypeTags.VILE_PRESENCE_IMMUNE)
				.forceAddTag(EntityTypeTags.UNDEAD);
	}
}
