/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record SyncPlayerDataPayload(boolean vampire, int blood, int maxBlood, List<String> powers, Map<String, Integer> cooldowns, int activeIndex, int bloodBarrierLayers, int healBlockTicks, int upgradeCostSeed, boolean nightVisionEnabled) implements CustomPacketPayload {
	public static final Type<SyncPlayerDataPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_player_data"));
	public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPayload> CODEC = new StreamCodec<>() {
		@Override
		public SyncPlayerDataPayload decode(FriendlyByteBuf buffer) {
			boolean vampire = buffer.readBoolean();
			int blood = buffer.readVarInt();
			int maxBlood = buffer.readVarInt();
			int powerCount = buffer.readVarInt();
			List<String> powers = new ArrayList<>(powerCount);
			for (int i = 0; i < powerCount; i++) {
				powers.add(buffer.readUtf());
			}
			int cooldownCount = buffer.readVarInt();
			Map<String, Integer> cooldowns = new LinkedHashMap<>(cooldownCount);
			for (int i = 0; i < cooldownCount; i++) {
				cooldowns.put(buffer.readUtf(), buffer.readVarInt());
			}
			int activeIndex = buffer.readVarInt();
			int bloodBarrierLayers = buffer.readVarInt();
			int healBlockTicks = buffer.readVarInt();
			int upgradeCostSeed = buffer.readInt();
			boolean nightVisionEnabled = buffer.readBoolean();
			return new SyncPlayerDataPayload(vampire, blood, maxBlood, powers, cooldowns, activeIndex, bloodBarrierLayers, healBlockTicks, upgradeCostSeed, nightVisionEnabled);
		}

		@Override
		public void encode(FriendlyByteBuf buffer, SyncPlayerDataPayload payload) {
			buffer.writeBoolean(payload.vampire());
			buffer.writeVarInt(payload.blood());
			buffer.writeVarInt(payload.maxBlood());
			buffer.writeVarInt(payload.powers().size());
			for (String power : payload.powers()) {
				buffer.writeUtf(power);
			}
			buffer.writeVarInt(payload.cooldowns().size());
			for (Map.Entry<String, Integer> entry : payload.cooldowns().entrySet()) {
				buffer.writeUtf(entry.getKey());
				buffer.writeVarInt(entry.getValue());
			}
			buffer.writeVarInt(payload.activeIndex());
			buffer.writeVarInt(payload.bloodBarrierLayers());
			buffer.writeVarInt(payload.healBlockTicks());
			buffer.writeInt(payload.upgradeCostSeed());
			buffer.writeBoolean(payload.nightVisionEnabled());
		}
	};

	public SyncPlayerDataPayload {
		powers = List.copyOf(powers);
		cooldowns = Map.copyOf(cooldowns);
	}

	@Override
	public Type<SyncPlayerDataPayload> type() {
		return TYPE;
	}

	public static SyncPlayerDataPayload from(ServerPlayer player) {
		return new SyncPlayerDataPayload(
				NyctoData.isVampire(player),
				NyctoData.getBlood(player),
				NyctoData.getMaxBlood(player),
				new ArrayList<>(NyctoData.getPowers(player)),
				NyctoData.getCooldowns(player),
				NyctoData.getActivePowerIndex(player),
				NyctoData.getBloodBarrierLayers(player),
				NyctoData.getHealBlockTicks(player),
				NyctoData.getUpgradeCostSeed(player),
				NyctoData.isNightVisionEnabled(player));
	}

	public static void send(ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, from(player));
	}

	public static void handle(SyncPlayerDataPayload payload, IPayloadContext context) {
		Player player = context.player();
		NyctoData.applySyncedState(player, payload.vampire(), payload.blood(), payload.maxBlood(), payload.powers(), payload.cooldowns(), payload.activeIndex(), payload.bloodBarrierLayers(), payload.healBlockTicks(), payload.upgradeCostSeed(), payload.nightVisionEnabled());
	}
}
