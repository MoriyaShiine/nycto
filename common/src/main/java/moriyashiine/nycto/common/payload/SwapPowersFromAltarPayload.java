package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SwapPowersFromAltarPayload(Power first, Power second) implements CustomPacketPayload {
	public static final Type<SwapPowersFromAltarPayload> TYPE = new Type<>(Nycto.id("swap_powers_from_altar"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SwapPowersFromAltarPayload> CODEC = StreamCodec.composite(
			Power.STREAM_CODEC, SwapPowersFromAltarPayload::first,
			Power.STREAM_CODEC, SwapPowersFromAltarPayload::second,
			SwapPowersFromAltarPayload::new
	);

	@Override
	public Type<SwapPowersFromAltarPayload> type() {
		return TYPE;
	}

	public static void send(Power first, Power second) {
		ClientPlayNetworking.send(new SwapPowersFromAltarPayload(first, second));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SwapPowersFromAltarPayload> {
		@Override
		public void receive(SwapPowersFromAltarPayload payload, ServerPlayNetworking.Context context) {
			AltarMenu.swapPowers(context.player(), payload.first(), payload.second());
		}
	}
}
