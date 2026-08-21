package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.tag.NyctoEnchantmentTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class NyctoEnchantmentTagsProvider extends FabricTagsProvider<Enchantment> {
	public NyctoEnchantmentTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.ENCHANTMENT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoEnchantmentTags.BYPASSES_BLOOD_VEIL)
				.add(Enchantments.SMITE);
	}
}
