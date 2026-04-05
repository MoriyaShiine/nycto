/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {
	public ModEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModEntityTypeTags.HAS_QUALITY_BLOOD)
				.forceAddTag(EntityTypeTags.ILLAGER)
				.add(EntityType.MANNEQUIN)
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
				.add(EntityType.COPPER_GOLEM)
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
		valueLookupBuilder(ModEntityTypeTags.CALLS_HUNTERS)
				.forceAddTag(EntityTypeTags.ILLAGER)
				.add(EntityType.IRON_GOLEM)
				.add(EntityType.VILLAGER)
				.add(EntityType.WANDERING_TRADER)
				.add(EntityType.WITCH);
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
		valueLookupBuilder(ModEntityTypeTags.CANNOT_BE_TARGETED_BY_THRALLS)
				.add(EntityType.CREAKING)
				.add(EntityType.CREEPER);
		valueLookupBuilder(ModEntityTypeTags.VILE_PRESENCE_IMMUNE)
				.forceAddTag(EntityTypeTags.UNDEAD);

		valueLookupBuilder(TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("enchancement", "cannot_disarm")))
				.add(ModEntityTypes.HUNTER);
	}
}
