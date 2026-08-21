package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SyncedConfigValuesComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncConfigValuesPayload(boolean vampireChargeJump, boolean vampireStepHeight) implements CustomPacketPayload {
	public static final Type<SyncConfigValuesPayload> TYPE = new Type<>(Nycto.id("sync_config_values"));
	public static final StreamCodec<FriendlyByteBuf, SyncConfigValuesPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, SyncConfigValuesPayload::vampireChargeJump,
			ByteBufCodecs.BOOL, SyncConfigValuesPayload::vampireStepHeight,
			SyncConfigValuesPayload::new
	);

	@Override
	public Type<SyncConfigValuesPayload> type() {
		return TYPE;
	}

	public static void send(boolean vampireChargeJump, boolean vampireStepHeight) {
		ClientPlayNetworking.send(new SyncConfigValuesPayload(vampireChargeJump, vampireStepHeight));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncConfigValuesPayload> {
		@Override
		public void receive(SyncConfigValuesPayload payload, ServerPlayNetworking.Context context) {
			SyncedConfigValuesComponent syncedConfigValues = NyctoEntityComponents.SYNCED_CONFIG_VALUES.get(context.player());
			syncedConfigValues.setVampireChargeJump(payload.vampireChargeJump());
			syncedConfigValues.setVampireStepHeight(payload.vampireStepHeight());
		}
	}
}
