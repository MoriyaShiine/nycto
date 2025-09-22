/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.tag.ModDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagProvider extends FabricTagProvider<DamageType> {
	public ModDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
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
