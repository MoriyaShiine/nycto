package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoDamageTypes;
import moriyashiine.nycto.common.tag.NyctoDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class NyctoDamageTypeTagsProvider extends FabricTagsProvider<DamageType> {
	public NyctoDamageTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.DAMAGE_TYPE, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoDamageTypeTags.BYPASSES_BLOOD_VEIL)
				.addTag(NyctoDamageTypeTags.HALTS_VAMPIRE_REGENERATION)
				.forceAddTag(DamageTypeTags.WITCH_RESISTANT_TO);
		builder(NyctoDamageTypeTags.HALTS_VAMPIRE_REGENERATION)
				.forceAddTag(DamageTypeTags.BYPASSES_INVULNERABILITY)
				.forceAddTag(DamageTypeTags.IS_FIRE)
				.add(NyctoDamageTypes.SUN)
				.add(NyctoDamageTypes.TOXIC_TOUCH)
				.add(NyctoDamageTypes.WOODEN_STAKE_FALL);

		builder(DamageTypeTags.BYPASSES_ARMOR)
				.add(NyctoDamageTypes.BLEED)
				.add(NyctoDamageTypes.SUN)
				.add(NyctoDamageTypes.TOXIC_TOUCH)
				.add(NyctoDamageTypes.WOODEN_STAKE_FALL);
		builder(DamageTypeTags.BYPASSES_COOLDOWN)
				.add(NyctoDamageTypes.BLEED)
				.add(NyctoDamageTypes.TOXIC_TOUCH);
		builder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
				.add(NyctoDamageTypes.BLEED)
				.add(NyctoDamageTypes.SUN)
				.add(NyctoDamageTypes.TOXIC_TOUCH);
		builder(DamageTypeTags.IS_FALL)
				.add(NyctoDamageTypes.WOODEN_STAKE_FALL);
		builder(DamageTypeTags.NO_IMPACT)
				.add(NyctoDamageTypes.BLEED);
		builder(DamageTypeTags.NO_KNOCKBACK)
				.add(NyctoDamageTypes.BLEED)
				.add(NyctoDamageTypes.SUN)
				.add(NyctoDamageTypes.TOXIC_TOUCH)
				.add(NyctoDamageTypes.WOODEN_STAKE_FALL);
	}
}
