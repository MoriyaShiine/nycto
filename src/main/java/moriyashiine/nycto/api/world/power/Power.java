/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.power;

import com.mojang.serialization.Codec;
import moriyashiine.nycto.api.init.NyctoRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import org.jspecify.annotations.Nullable;

public class Power {
	public static final Codec<Power> CODEC = NyctoRegistries.POWER.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, Power> STREAM_CODEC = ByteBufCodecs.registry(NyctoRegistries.POWER_KEY);

	@Nullable
	protected String descriptionId;

	private Holder<Power> holder;
	private Identifier textureLocation;

	public void onAdded(ServerPlayer player) {
	}

	public void onRemoved(ServerPlayer player) {
	}

	public void tick(ServerPlayer player) {
	}

	public boolean isWeakness() {
		return false;
	}

	public final Holder<Power> getHolder() {
		if (holder == null) {
			holder = NyctoRegistries.POWER.wrapAsHolder(this);
		}
		return holder;
	}

	public final Identifier getOrCreateTextureLocation() {
		if (textureLocation == null) {
			Identifier id = NyctoRegistries.POWER.getKey(this);
			textureLocation = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/power/" + id.getPath() + ".png");
		}
		return textureLocation;
	}

	public final String getOrCreateDescriptionId() {
		if (descriptionId == null) {
			descriptionId = Util.makeDescriptionId("power", NyctoRegistries.POWER.getKey(this));
		}
		return descriptionId;
	}

	public final boolean is(TagKey<Power> tagKey) {
		return getHolder().is(tagKey);
	}
}
