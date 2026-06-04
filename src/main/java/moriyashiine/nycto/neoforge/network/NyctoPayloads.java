/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NyctoPayloads {
	private NyctoPayloads() {
	}

	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(UseActivePowerPayload.TYPE, UseActivePowerPayload.CODEC, UseActivePowerPayload::handle);
		registrar.playToServer(SetActivePowerPayload.TYPE, SetActivePowerPayload.CODEC, SetActivePowerPayload::handle);
		registrar.playToServer(DarkFormJumpPayload.TYPE, DarkFormJumpPayload.CODEC, DarkFormJumpPayload::handle);
		registrar.playToServer(SwapAltarPowersPayload.TYPE, SwapAltarPowersPayload.CODEC, SwapAltarPowersPayload::handle);
		registrar.playToClient(AddBloodBarrierParticlesPayload.TYPE, AddBloodBarrierParticlesPayload.CODEC, AddBloodBarrierParticlesPayload::handle);
		registrar.playToClient(SyncBloodBarrierPayload.TYPE, SyncBloodBarrierPayload.CODEC, SyncBloodBarrierPayload::handle);
		registrar.playToClient(SyncBatFormPayload.TYPE, SyncBatFormPayload.CODEC, SyncBatFormPayload::handle);
		registrar.playToClient(SyncCarnagePayload.TYPE, SyncCarnagePayload.CODEC, SyncCarnagePayload::handle);
		registrar.playToClient(SyncBloodrushPayload.TYPE, SyncBloodrushPayload.CODEC, SyncBloodrushPayload::handle);
		registrar.playToClient(SyncDarkFormPayload.TYPE, SyncDarkFormPayload.CODEC, SyncDarkFormPayload::handle);
		registrar.playToClient(SyncEntityBloodPayload.TYPE, SyncEntityBloodPayload.CODEC, SyncEntityBloodPayload::handle);
		registrar.playToClient(SyncKeenSensesPayload.TYPE, SyncKeenSensesPayload.CODEC, SyncKeenSensesPayload::handle);
		registrar.playToClient(SyncMistFormPayload.TYPE, SyncMistFormPayload.CODEC, SyncMistFormPayload::handle);
		registrar.playToClient(SyncPlayerDataPayload.TYPE, SyncPlayerDataPayload.CODEC, SyncPlayerDataPayload::handle);
		registrar.playToClient(SyncThrallPayload.TYPE, SyncThrallPayload.CODEC, SyncThrallPayload::handle);
	}
}
