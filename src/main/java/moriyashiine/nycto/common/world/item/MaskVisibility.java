/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum MaskVisibility implements StringRepresentable {
	VISIBLE("visible"),
	REDUCED("reduced"),
	REMOVED("removed");

	public static final IntFunction<MaskVisibility> BY_ID = ByIdMap.continuous(MaskVisibility::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final Codec<MaskVisibility> CODEC = StringRepresentable.fromValues(MaskVisibility::values);
	public static final StreamCodec<ByteBuf, MaskVisibility> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, MaskVisibility::ordinal);

	private final String name;

	MaskVisibility(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}

	public MaskVisibility cycle() {
		int ordinal = ordinal() + 1;
		if (ordinal == values().length) {
			ordinal = 0;
		}
		return values()[ordinal];
	}

	public String getTranslationKey() {
		return "tooltip.nycto.mask_visibility." + name;
	}
}
