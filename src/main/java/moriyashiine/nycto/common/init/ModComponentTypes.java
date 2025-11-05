/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerComponentType;

public class ModComponentTypes {
	public static final ComponentType<Boolean> SHOW_CAPE = registerComponentType("show_cape", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));

	public static final ComponentType<Integer> BLOOD_CHARGE = registerComponentType("blood_charge", new ComponentType.Builder<Integer>().codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));
	public static final ComponentType<Boolean> POISONED = registerComponentType("poisoned", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));

	public static final ComponentType<Boolean> PLAYER_BLOOD = registerComponentType("player_blood", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
	public static final ComponentType<Boolean> VAMPIRE_BLOOD = registerComponentType("vampire_blood", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));

	public static void init() {
	}
}
