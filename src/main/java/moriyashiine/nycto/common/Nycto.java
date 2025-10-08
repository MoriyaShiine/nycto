/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.client.payload.*;
import moriyashiine.nycto.common.command.TransformationCommand;
import moriyashiine.nycto.common.event.block.CoffinEvent;
import moriyashiine.nycto.common.event.block.ShearsEvent;
import moriyashiine.nycto.common.event.entity.*;
import moriyashiine.nycto.common.event.internal.GenerateLootEvent;
import moriyashiine.nycto.common.event.internal.SyncTruncatedWorldSeedEvent;
import moriyashiine.nycto.common.event.item.PoisonedFoodEvent;
import moriyashiine.nycto.common.event.item.VampiricDaggerEvent;
import moriyashiine.nycto.common.event.item.WoodenStakeEvent;
import moriyashiine.nycto.common.event.power.*;
import moriyashiine.nycto.common.event.power.weakness.HydrophobiaEvent;
import moriyashiine.nycto.common.event.power.weakness.PyrophobiaEvent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.payload.ApplyPowerFromAltarPayload;
import moriyashiine.nycto.common.payload.DarkFormJumpPayload;
import moriyashiine.nycto.common.payload.SyncPowerIndexPayload;
import moriyashiine.nycto.common.payload.UsePowerPayload;
import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.strawberrylib.api.event.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class Nycto implements ModInitializer {
	public static final String MOD_ID = "nycto";

	public static boolean superbSteedsLoaded = false;

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		initRegistries();
		initCommands();
		initEvents();
		initPayloads();
		superbSteedsLoaded = FabricLoader.getInstance().isModLoaded("superbsteeds");
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}

	private void initRegistries() {
		NyctoRegistries.init();
		ModBlocks.init();
		ModBlockEntityTypes.init();
		ModComponentTypes.init();
		ModEntityAttributes.init();
		ModEntityTypes.init();
		ModGameRules.init();
		ModItems.init();
		ModPotions.init();
		ModParticleTypes.init();
		ModPowers.init();
		ModRecipeSerializers.init();
		ModScreenHandlerTypes.init();
		ModSoundEvents.init();
		ModStatusEffects.init();
		ModTransformations.init();
		ModWorldGeneration.init();
	}

	private void initCommands() {
		CommandRegistrationCallback.EVENT.register(new TransformationCommand());
	}

	private void initEvents() {
		// INTERNAL
		LootTableEvents.MODIFY.register(new GenerateLootEvent());
		ServerPlayerEvents.JOIN.register(new SyncTruncatedWorldSeedEvent());
		// BLOCK
		EntitySleepEvents.ALLOW_BED.register(new CoffinEvent.AllowBed());
		EntitySleepEvents.ALLOW_SLEEPING.register(new CoffinEvent.AllowSleeping());
		EntitySleepEvents.ALLOW_SLEEP_TIME.register(new CoffinEvent.AllowSleepTime());
		ModifyBlockBreakingSpeedEvent.MULTIPLY_BASE.register(new ShearsEvent());
		// ENTITY
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new AttributeEvent());
		ModifyBlockBreakingSpeedEvent.MULTIPLY_BASE.register(new BeastFormEvent());
		ServerEntityEvents.ENTITY_LOAD.register(new BloodEvent.Load());
		ServerEntityEvents.ENTITY_UNLOAD.register(new BloodEvent.Unload());
		ServerPlayerEvents.COPY_FROM.register(new BloodEvent.Copy());
		PreventEquipmentUsageEvent.EVENT.register(new CannotUseEquipmentEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new HunterEvent.Heat());
		TickEntityEvent.EVENT.register(new HunterEvent.Aura());
		ModifyCriticalStatusEvent.EVENT.register(new HunterEvent.CriticalImmunity());
		PreventHostileTargetingEvent.EVENT.register(new HunterEvent.PreventTargeting());
		ServerPlayerEvents.AFTER_RESPAWN.register(new SunExposureEvent());
		ServerPlayerEvents.COPY_FROM.register(new TransformationEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new StatusEffectEvent.VampireWard());
		AfterDamageIncludingDeathEvent.EVENT.register(new StatusEffectEvent.Stunned());
		// vampire
		ServerLivingEntityEvents.ALLOW_DEATH.register(new VampireEvent.BloodVeil());
		ModifyMovementEvents.JUMP_VELOCITY.register(new VampireEvent.ChargeJump());
		UseEntityCallback.EVENT.register(new VampireEvent.DrinkBlood());
		EatFoodEvent.EVENT.register(new VampireEvent.EatFood());
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new VampireEvent.FreezeImmunity());
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new VampireEvent.HealBlock());
		AfterDamageIncludingDeathEvent.EVENT.register(new VampireEvent.WeaknessItem());
		PlayerBlockBreakEvents.AFTER.register(new VampireEvent.BreakHarming());
		TickEntityEvent.EVENT.register(new VampireEvent.TickHarming());
		// ITEM
		EatFoodEvent.EVENT.register(new PoisonedFoodEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new VampiricDaggerEvent());
		ModifyStackDamageEvent.ADD.register(new WoodenStakeEvent());
		// POWER
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new BatFormEvent.ReduceDamage());
		ModifyMovementEvents.MOVEMENT_VELOCITY.register(new BatFormEvent.ReduceFlightSpeed());
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new BatSwarmEvent());
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new BloodBarrierEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new BloodFlechettesEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new CarnageEvent());
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new DarkFormEvent());
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new HasOwnerEvent());
		AfterDamageIncludingDeathEvent.EVENT.register(new HypnotizeEvent());
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new MistFormEvent.AllowDamage());
		ModifyCriticalStatusEvent.EVENT.register(new MistFormEvent.ForceCritical());
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new VampiricThrallEvent.Revenge());
		UseEntityCallback.EVENT.register(new VampiricThrallEvent.RightClickOverride());
		// weakness
		ModifyMovementEvents.MOVEMENT_VELOCITY.register(new HydrophobiaEvent());
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new PyrophobiaEvent());
	}

	private void initPayloads() {
		// client payloads
		PayloadTypeRegistry.playS2C().register(SyncTruncatedWorldSeedPayload.ID, SyncTruncatedWorldSeedPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ModifyPowerPayload.ID, ModifyPowerPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SetTransformationPayload.ID, SetTransformationPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SetPowerCooldownPayload.ID, SetPowerCooldownPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AddBloodBarrierParticlesPayload.ID, AddBloodBarrierParticlesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayBloodrushSoundPayload.ID, PlayBloodrushSoundPayload.CODEC);
		// common payloads
		PayloadTypeRegistry.playC2S().register(ApplyPowerFromAltarPayload.ID, ApplyPowerFromAltarPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DarkFormJumpPayload.ID, DarkFormJumpPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SyncPowerIndexPayload.ID, SyncPowerIndexPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UsePowerPayload.ID, UsePowerPayload.CODEC);
		// common receivers
		ServerPlayNetworking.registerGlobalReceiver(ApplyPowerFromAltarPayload.ID, new ApplyPowerFromAltarPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(DarkFormJumpPayload.ID, new DarkFormJumpPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SyncPowerIndexPayload.ID, new SyncPowerIndexPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(UsePowerPayload.ID, new UsePowerPayload.Receiver());
	}
}
