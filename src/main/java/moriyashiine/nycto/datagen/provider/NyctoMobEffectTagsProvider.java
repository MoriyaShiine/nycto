package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.nycto.common.tag.NyctoMobEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

import java.util.concurrent.CompletableFuture;

public class NyctoMobEffectTagsProvider extends FabricTagsProvider<MobEffect> {
	public NyctoMobEffectTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.MOB_EFFECT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		add(NyctoMobEffectTags.INFECTION,
				NyctoMobEffects.VAMPIRISM);
		builder(TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath("enchancement", "chaos_unchoosable")))
				.addTag(NyctoMobEffectTags.INFECTION);
	}

	@SafeVarargs
	private void add(TagKey<MobEffect> tagKey, Holder<MobEffect>... effects) {
		TagAppender<MobEffect> builder = builder(tagKey);
		for (Holder<MobEffect> effect : effects) {
			builder.add(effect.unwrapKey().orElseThrow());
		}
	}
}
