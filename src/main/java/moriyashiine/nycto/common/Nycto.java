/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.client.payload.*;
import moriyashiine.nycto.common.command.TransformationCommand;
import moriyashiine.nycto.common.event.block.CoffinEvent;
import moriyashiine.nycto.common.event.entity.*;
import moriyashiine.nycto.common.event.internal.GenerateLootEvent;
import moriyashiine.nycto.common.event.internal.SyncTruncatedWorldSeedEvent;
import moriyashiine.nycto.common.event.item.PoisonedFoodEvent;
import moriyashiine.nycto.common.event.item.VampiricDaggerEvent;
import moriyashiine.nycto.common.event.item.WoodenStakeEvent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.event.power.vampire.*;
import moriyashiine.nycto.common.event.power.vampire.weakness.HydrophobiaEvent;
import moriyashiine.nycto.common.event.power.vampire.weakness.PyrophobiaEvent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.payload.*;
import moriyashiine.strawberrylib.api.SLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class Nycto implements ModInitializer {
	public static final String MOD_ID = "nycto";

	public static boolean superbSteedsLoaded = false;

	@Override
	public void onInitialize() {
		superbSteedsLoaded = FabricLoader.getInstance().isModLoaded("superb_steeds");
		SLib.init(MOD_ID);
		initRegistries();
		initPayloads();
		initEvents();
	}

	public static Identifier id(String value) {
		return Identifier.fromNamespaceAndPath(MOD_ID, value);
	}

	private void initRegistries() {
		NyctoRegistries.init();
		NyctoAttributes.init();
		NyctoBlocks.init();
		NyctoBlockEntityTypes.init();
		NyctoDataComponents.init();
		NyctoEntitySubPredicateTypes.init();
		NyctoEntityTypes.init();
		NyctoEnvironmentAttributes.init();
		NyctoGameRules.init();
		NyctoHunterTypes.init();
		NyctoItems.init();
		NyctoMenuTypes.init();
		NyctoMobEffects.init();
		NyctoPotions.init();
		NyctoParticleTypes.init();
		NyctoPowers.init();
		NyctoRecipeSerializers.init();
		NyctoSoundEvents.init();
		NyctoTransformations.init();
		NyctoTriggers.init();
		NyctoWorldGeneration.init();
	}

	private void initPayloads() {
		// client payloads
		PayloadTypeRegistry.clientboundPlay().register(SyncTruncatedWorldSeedPayload.TYPE, SyncTruncatedWorldSeedPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(ModifyPowerPayload.TYPE, ModifyPowerPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(SetTransformationPayload.TYPE, SetTransformationPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(SetPowerCooldownPayload.TYPE, SetPowerCooldownPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(AddBloodBarrierParticlesPayload.TYPE, AddBloodBarrierParticlesPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(PlayBloodrushSoundPayload.TYPE, PlayBloodrushSoundPayload.CODEC);
		// common payloads
		PayloadTypeRegistry.serverboundPlay().register(SyncConfigValuesPayload.TYPE, SyncConfigValuesPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ApplyPowerFromAltarPayload.TYPE, ApplyPowerFromAltarPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(DarkFormJumpPayload.TYPE, DarkFormJumpPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(SwapPowersFromAltarPayload.TYPE, SwapPowersFromAltarPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(SyncPowerIndexPayload.TYPE, SyncPowerIndexPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(UsePowerPayload.TYPE, UsePowerPayload.CODEC);
		// common receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncConfigValuesPayload.TYPE, new SyncConfigValuesPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(ApplyPowerFromAltarPayload.TYPE, new ApplyPowerFromAltarPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(DarkFormJumpPayload.TYPE, new DarkFormJumpPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SwapPowersFromAltarPayload.TYPE, new SwapPowersFromAltarPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SyncPowerIndexPayload.TYPE, new SyncPowerIndexPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(UsePowerPayload.TYPE, new UsePowerPayload.Receiver());
	}

	private void initEvents() {
		// COMMAND
		TransformationCommand.init();
		// INTERNAL
		GenerateLootEvent.init();
		SyncTruncatedWorldSeedEvent.init();
		// BLOCK
		CoffinEvent.init();
		// ENTITY
		AttributeEvent.init();
		BeastFormEvent.init();
		BloodEvent.init();
		HunterEvent.init();
		MobEffectEvent.init();
		SunExposureEvent.init();
		TransformationEvent.init();
		// transformation
		VampireEvent.init();
		// ITEM
		PoisonedFoodEvent.init();
		VampiricDaggerEvent.init();
		WoodenStakeEvent.init();
		// POWER
		// util
		HasOwnerEvent.init();
		// vampire
		BatFormEvent.init();
		BatSwarmEvent.init();
		BloodBarrierEvent.init();
		BloodFlechettesEvent.init();
		CarnageEvent.init();
		DarkFormEvent.init();
		HypnotizeEvent.init();
		MistFormEvent.init();
		VampiricThrallEvent.init();
		// vampire weakness
		HydrophobiaEvent.init();
		PyrophobiaEvent.init();
		// werewolf
	}
}
