package moriyashiine.nycto.api.world.transformation;

import com.mojang.serialization.Codec;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.misc.AttributeModifierSet;
import moriyashiine.nycto.api.misc.PowerHotbarTextureSet;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Util;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;

public class Transformation {
	public static final Codec<Transformation> CODEC = NyctoRegistries.TRANSFORMATION.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, Transformation> STREAM_CODEC = ByteBufCodecs.registry(NyctoRegistries.TRANSFORMATION_KEY);

	@Nullable
	protected String descriptionId;

	public void onAdded(ServerPlayer player) {
		applyModifiers(player, true);
	}

	public void onRemoved(ServerPlayer player) {
		NyctoRegistries.POWER.forEach(power -> NyctoAPI.removePower(player, power));
		applyModifiers(player, false);
	}

	public AttributeModifierSet getAttributeModifiers(ServerPlayer player) {
		return new AttributeModifierSet(new HashSet<>());
	}

	public PowerHotbarTextureSet getPowerHotbarTextureSet() {
		return PowerHotbarTextureSet.EMPTY;
	}

	public String getOrCreateDescriptionId() {
		if (descriptionId == null) {
			descriptionId = Util.makeDescriptionId("transformation", NyctoRegistries.TRANSFORMATION.getKey(this));
		}
		return descriptionId;
	}

	public void applyModifiers(ServerPlayer player, boolean shouldHave) {
		getAttributeModifiers(player).attributeModifiers().forEach(tuple -> SLibUtils.applyAttributeModifier(player, tuple.getFirst(), tuple.getSecond(), shouldHave));
	}
}
