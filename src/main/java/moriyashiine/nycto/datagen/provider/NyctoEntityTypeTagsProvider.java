/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.references.NyctoEntityTypeIds;
import moriyashiine.nycto.common.tag.NyctoEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityTypeIds;

import java.util.concurrent.CompletableFuture;

public class NyctoEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {
	public NyctoEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoEntityTypeTags.HAS_QUALITY_BLOOD)
				.forceAddTag(EntityTypeTags.ILLAGER)
				.add(EntityTypeIds.MANNEQUIN)
				.add(EntityTypeIds.PIGLIN)
				.add(EntityTypeIds.PIGLIN_BRUTE)
				.add(EntityTypeIds.PLAYER)
				.add(EntityTypeIds.VILLAGER)
				.add(EntityTypeIds.WANDERING_TRADER)
				.add(EntityTypeIds.WITCH)
				.add(NyctoEntityTypeIds.VAMPIRE)
				.add(NyctoEntityTypeIds.HUNTER);
		builder(NyctoEntityTypeTags.HAS_NO_BLOOD)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.forceAddTag(EntityTypeTags.ARTHROPOD)
				.forceAddTag(EntityTypeTags.FROG_FOOD)
				.forceAddTag(EntityTypeTags.UNDEAD)
				.add(EntityTypeIds.ALLAY)
				.add(EntityTypeIds.ARMOR_STAND)
				.add(EntityTypeIds.BLAZE)
				.add(EntityTypeIds.BREEZE)
				.add(EntityTypeIds.COPPER_GOLEM)
				.add(EntityTypeIds.CREAKING)
				.add(EntityTypeIds.CREEPER)
				.add(EntityTypeIds.ENDERMAN)
				.add(EntityTypeIds.GHAST)
				.add(EntityTypeIds.HAPPY_GHAST)
				.add(EntityTypeIds.IRON_GOLEM)
				.add(EntityTypeIds.SHULKER)
				.add(EntityTypeIds.SNOW_GOLEM)
				.add(EntityTypeIds.VEX)
				.add(EntityTypeIds.WARDEN);

		builder(NyctoEntityTypeTags.BYPASSES_BLOOD_VEIL)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityTypeIds.WARDEN);
		builder(NyctoEntityTypeTags.CALLS_HUNTERS)
				.forceAddTag(EntityTypeTags.ILLAGER)
				.add(EntityTypeIds.IRON_GOLEM)
				.add(EntityTypeIds.VILLAGER)
				.add(EntityTypeIds.WANDERING_TRADER)
				.add(EntityTypeIds.WITCH);
		builder(NyctoEntityTypeTags.CAN_BE_THRALLED)
				.add(EntityTypeIds.EVOKER)
				.add(EntityTypeIds.HORSE)
				.add(EntityTypeIds.ILLUSIONER)
				.add(EntityTypeIds.PIGLIN)
				.add(EntityTypeIds.PIGLIN_BRUTE)
				.add(EntityTypeIds.PILLAGER)
				.add(EntityTypeIds.VILLAGER)
				.add(EntityTypeIds.VINDICATOR)
				.add(EntityTypeIds.WANDERING_TRADER)
				.add(EntityTypeIds.WITCH)
				.add(EntityTypeIds.WOLF);
		builder(NyctoEntityTypeTags.CANNOT_BE_HYPNOTIZED)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.add(EntityTypeIds.WARDEN);
		builder(NyctoEntityTypeTags.CANNOT_BE_TARGETED_BY_THRALLS)
				.add(EntityTypeIds.CREAKING)
				.add(EntityTypeIds.CREEPER);
		builder(NyctoEntityTypeTags.CANNOT_PANIC)
				.forceAddTag(ConventionalEntityTypeTags.BOSSES)
				.forceAddTag(EntityTypeTags.UNDEAD);

		builder(TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("enchancement", "cannot_disarm")))
				.add(NyctoEntityTypeIds.HUNTER);
	}
}
