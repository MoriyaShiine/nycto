/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.client.payload.AddBloodBarrierParticlesPayload;
import moriyashiine.nycto.client.payload.ModifyPowerPayload;
import moriyashiine.nycto.client.payload.PlayBloodrushSoundPayload;
import moriyashiine.nycto.client.payload.SetPowerCooldownPayload;
import moriyashiine.nycto.client.payload.SetTransformationPayload;
import moriyashiine.nycto.client.payload.SyncTruncatedWorldSeedPayload;
import moriyashiine.nycto.common.NyctoNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModPayloads {
	private ModPayloads() {
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(SyncTruncatedWorldSeedPayload.TYPE, SyncTruncatedWorldSeedPayload.CODEC, SyncTruncatedWorldSeedPayload::handle);
		registrar.playToClient(ModifyPowerPayload.TYPE, ModifyPowerPayload.CODEC, ModifyPowerPayload::handle);
		registrar.playToClient(SetTransformationPayload.TYPE, SetTransformationPayload.CODEC, SetTransformationPayload::handle);
		registrar.playToClient(SetPowerCooldownPayload.TYPE, SetPowerCooldownPayload.CODEC, SetPowerCooldownPayload::handle);
		registrar.playToClient(AddBloodBarrierParticlesPayload.TYPE, AddBloodBarrierParticlesPayload.CODEC, AddBloodBarrierParticlesPayload::handle);
		registrar.playToClient(PlayBloodrushSoundPayload.TYPE, PlayBloodrushSoundPayload.CODEC, PlayBloodrushSoundPayload::handle);

		registrar.playToServer(ApplyPowerFromAltarPayload.TYPE, ApplyPowerFromAltarPayload.CODEC, ApplyPowerFromAltarPayload::handle);
		registrar.playToServer(DarkFormJumpPayload.TYPE, DarkFormJumpPayload.CODEC, DarkFormJumpPayload::handle);
		registrar.playToServer(SwapPowersFromAltarPayload.TYPE, SwapPowersFromAltarPayload.CODEC, SwapPowersFromAltarPayload::handle);
		registrar.playToServer(SyncPowerIndexPayload.TYPE, SyncPowerIndexPayload.CODEC, SyncPowerIndexPayload::handle);
		registrar.playToServer(SyncVampireChargeJumpStatusPayload.TYPE, SyncVampireChargeJumpStatusPayload.CODEC, SyncVampireChargeJumpStatusPayload::handle);
		registrar.playToServer(SyncVampireStepHeightStatusPayload.TYPE, SyncVampireStepHeightStatusPayload.CODEC, SyncVampireStepHeightStatusPayload::handle);
		registrar.playToServer(UsePowerPayload.TYPE, UsePowerPayload.CODEC, UsePowerPayload::handle);
	}
}
