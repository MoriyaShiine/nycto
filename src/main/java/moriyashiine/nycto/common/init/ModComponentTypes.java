/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import com.mojang.serialization.Codec;
import moriyashiine.nycto.common.world.item.MaskVisibility;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerComponentType;

public class ModComponentTypes {
	public static final DataComponentType<MaskVisibility> MASK_VISIBILITY = registerComponentType("mask_visibility", new DataComponentType.Builder<MaskVisibility>().persistent(MaskVisibility.CODEC).networkSynchronized(MaskVisibility.STREAM_CODEC));
	public static final DataComponentType<Boolean> SHOW_CAPE = registerComponentType("show_cape", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

	public static final DataComponentType<Integer> BLOOD_CHARGE = registerComponentType("blood_charge", new DataComponentType.Builder<Integer>().persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
	public static final DataComponentType<Boolean> POISONED = registerComponentType("poisoned", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

	public static final DataComponentType<Boolean> PLAYER_BLOOD = registerComponentType("player_blood", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
	public static final DataComponentType<Boolean> VAMPIRE_BLOOD = registerComponentType("vampire_blood", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

	public static void init() {
	}
}
