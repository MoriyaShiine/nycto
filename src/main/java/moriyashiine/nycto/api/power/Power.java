/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.power;

import moriyashiine.nycto.api.init.NyctoRegistries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class Power {
	@Nullable
	protected String translationKey;

	private RegistryEntry<Power> entry;
	private Identifier textureLocation;

	public void onAdded(ServerPlayerEntity player) {
	}

	public void onRemoved(ServerPlayerEntity player) {
	}

	public void tick(ServerPlayerEntity player) {
	}

	public boolean isNegative() {
		return false;
	}

	public final RegistryEntry<Power> getEntry() {
		if (entry == null) {
			entry = NyctoRegistries.POWER.getEntry(this);
		}
		return entry;
	}

	public final Identifier getTextureLocation() {
		if (textureLocation == null) {
			Identifier id = NyctoRegistries.POWER.getId(this);
			textureLocation = Identifier.of(id.getNamespace(), "textures/power/" + id.getPath() + ".png");
		}
		return textureLocation;
	}

	public final String getTranslationKey() {
		if (translationKey == null) {
			translationKey = Util.createTranslationKey("power", NyctoRegistries.POWER.getId(this));
		}
		return translationKey;
	}

	public final boolean isIn(TagKey<Power> tagKey) {
		return getEntry().isIn(tagKey);
	}
}
