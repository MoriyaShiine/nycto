/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.tag.ModDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends FabricTagsProvider<DamageType> {
	public ModDamageTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.DAMAGE_TYPE, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(ModDamageTypeTags.BYPASSES_BLOOD_VEIL)
				.addTag(ModDamageTypeTags.HALTS_VAMPIRE_REGENERATION)
				.forceAddTag(DamageTypeTags.WITCH_RESISTANT_TO);
		builder(ModDamageTypeTags.HALTS_VAMPIRE_REGENERATION)
				.forceAddTag(DamageTypeTags.BYPASSES_INVULNERABILITY)
				.forceAddTag(DamageTypeTags.IS_FIRE)
				.addOptional(ModDamageTypes.SUN)
				.addOptional(ModDamageTypes.TOXIC_TOUCH);

		builder(DamageTypeTags.BYPASSES_ARMOR)
				.addOptional(ModDamageTypes.BLEED)
				.addOptional(ModDamageTypes.SUN)
				.addOptional(ModDamageTypes.TOXIC_TOUCH);
		builder(DamageTypeTags.BYPASSES_COOLDOWN)
				.addOptional(ModDamageTypes.BLEED)
				.addOptional(ModDamageTypes.TOXIC_TOUCH);
		builder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
				.addOptional(ModDamageTypes.BLEED)
				.addOptional(ModDamageTypes.SUN)
				.addOptional(ModDamageTypes.TOXIC_TOUCH);
		builder(DamageTypeTags.NO_IMPACT)
				.addOptional(ModDamageTypes.BLEED);
		builder(DamageTypeTags.NO_KNOCKBACK)
				.addOptional(ModDamageTypes.BLEED)
				.addOptional(ModDamageTypes.SUN)
				.addOptional(ModDamageTypes.TOXIC_TOUCH);
	}
}
