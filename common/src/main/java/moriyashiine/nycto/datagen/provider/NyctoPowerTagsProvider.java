package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

public class NyctoPowerTagsProvider extends FabricTagsProvider<Power> {
	public NyctoPowerTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, NyctoRegistries.POWER_KEY, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		add(NyctoPowerTags.VAMPIRE_CHOOSABLE,
				NyctoPowers.BAT_FORM,
				NyctoPowers.BAT_SWARM,
				NyctoPowers.BATSTEP,
				NyctoPowers.BLOOD_BARRIER,
				NyctoPowers.BLOOD_FLECHETTES,
				NyctoPowers.BLOODRUSH,
				NyctoPowers.CARNAGE,
				NyctoPowers.DARK_FORM,
				NyctoPowers.HAEMOGENESIS,
				NyctoPowers.HYPNOTIZE,
				NyctoPowers.KEEN_SENSES,
				NyctoPowers.MIST_FORM,
				NyctoPowers.VAMPIRIC_THRALL,
				NyctoPowers.HUMANITY,
				NyctoPowers.HYDROPHOBIA,
				NyctoPowers.PYROPHOBIA,
				NyctoPowers.RICH_TASTES,
				NyctoPowers.THIN_BLOOD,
				NyctoPowers.VILE_PRESENCE);
	}

	private void add(TagKey<Power> tagKey, Power... powers) {
		TagAppender<ResourceKey<Power>, Power> builder = builder(tagKey);
		for (Power power : powers) {
			builder.add(key(power));
		}
	}

	private static ResourceKey<Power> key(Power power) {
		return power.getHolder().unwrapKey().orElseThrow();
	}
}
