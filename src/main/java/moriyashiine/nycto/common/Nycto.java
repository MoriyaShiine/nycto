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
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.event.power.vampire.*;
import moriyashiine.nycto.common.event.power.vampire.weakness.HydrophobiaEvent;
import moriyashiine.nycto.common.event.power.vampire.weakness.PyrophobiaEvent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.payload.*;
import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.strawberrylib.api.event.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class Nycto implements ModInitializer {
	public static final String MOD_ID = "nycto";

	public static boolean superbSteedsLoaded = false;

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		initRegistries();
		initPayloads();
		initEvents();
		superbSteedsLoaded = FabricLoader.getInstance().isModLoaded("superbsteeds");
	}

	public static Identifier id(String value) {
		return Identifier.fromNamespaceAndPath(MOD_ID, value);
	}

	private void initRegistries() {
		NyctoRegistries.init();
		ModAttributes.init();
		ModBlocks.init();
		ModBlockEntityTypes.init();
		ModComponentTypes.init();
		ModEntitySubPredicateTypes.init();
		ModEntityTypes.init();
		ModEnvironmentAttributes.init();
		ModGameRules.init();
		ModItems.init();
		ModMenuTypes.init();
		ModMobEffects.init();
		ModPotions.init();
		ModParticleTypes.init();
		ModPowers.init();
		ModRecipeSerializers.init();
		ModSoundEvents.init();
		ModTransformations.init();
		ModTriggers.init();
		ModWorldGeneration.init();
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
		PayloadTypeRegistry.serverboundPlay().register(ApplyPowerFromAltarPayload.TYPE, ApplyPowerFromAltarPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(DarkFormJumpPayload.TYPE, DarkFormJumpPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(SyncPowerIndexPayload.TYPE, SyncPowerIndexPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(SyncVampireChargeJumpStatusPayload.TYPE, SyncVampireChargeJumpStatusPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(SyncVampireStepHeightStatusPayload.TYPE, SyncVampireStepHeightStatusPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(UsePowerPayload.TYPE, UsePowerPayload.CODEC);
		// common receivers
		ServerPlayNetworking.registerGlobalReceiver(ApplyPowerFromAltarPayload.TYPE, new ApplyPowerFromAltarPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(DarkFormJumpPayload.TYPE, new DarkFormJumpPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SyncPowerIndexPayload.TYPE, new SyncPowerIndexPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SyncVampireChargeJumpStatusPayload.TYPE, new SyncVampireChargeJumpStatusPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(SyncVampireStepHeightStatusPayload.TYPE, new SyncVampireStepHeightStatusPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(UsePowerPayload.TYPE, new UsePowerPayload.Receiver());
	}

	private void initEvents() {
		// INTERNAL
		LootTableEvents.MODIFY.register(new GenerateLootEvent());
		ServerPlayerEvents.JOIN.register(new SyncTruncatedWorldSeedEvent());
		// COMMAND
		CommandRegistrationCallback.EVENT.register(new TransformationCommand());
		// BLOCK
		EntitySleepEvents.ALLOW_BED.register(new CoffinEvent.AllowBed());
		EntitySleepEvents.ALLOW_SLEEPING.register(new CoffinEvent.AllowSleeping());
		ModifyDestroyProgressEvent.MULTIPLY_BASE.register(new ShearsEvent());
		// ENTITY
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new AttributeEvent());
		ModifyDestroyProgressEvent.MULTIPLY_BASE.register(new BeastFormEvent());
		ServerEntityEvents.ENTITY_LOAD.register(new BloodEvent.Load());
		ServerEntityEvents.ENTITY_UNLOAD.register(new BloodEvent.Unload());
		ServerPlayerEvents.COPY_FROM.register(new BloodEvent.Copy());
		ServerMobEffectEvents.ALLOW_ADD.register(new BloodEvent.EffectImmunity());
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
		ModifyMovementEvents.JUMP_DELTA.register(new VampireEvent.ChargeJump());
		UseEntityCallback.EVENT.register(new VampireEvent.DrinkBlood());
		EatFoodEvent.EVENT.register(new VampireEvent.EatFood());
		ServerMobEffectEvents.ALLOW_ADD.register(new VampireEvent.EffectImmunity());
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
		ModifyMovementEvents.MOVEMENT_DELTA.register(new BatFormEvent.ReduceFlightSpeed());
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
		TickEntityEvent.EVENT.register(new VampiricThrallEvent.Defend());
		UseEntityCallback.EVENT.register(new VampiricThrallEvent.RightClickOverride());
		// weakness
		ModifyMovementEvents.MOVEMENT_DELTA.register(new HydrophobiaEvent());
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new PyrophobiaEvent());
	}
}
