/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.*;
import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<SyncedConfigValuesComponent> SYNCED_CONFIG_VALUES = ComponentRegistry.getOrCreate(Nycto.id("synced_config_values"), SyncedConfigValuesComponent.class);

	public static final ComponentKey<BloodComponent> BLOOD = ComponentRegistry.getOrCreate(Nycto.id("blood"), BloodComponent.class);
	public static final ComponentKey<HealBlockComponent> HEAL_BLOCK = ComponentRegistry.getOrCreate(Nycto.id("heal_block"), HealBlockComponent.class);
	public static final ComponentKey<HunterHeatComponent> HUNTER_HEAT = ComponentRegistry.getOrCreate(Nycto.id("hunter_heat"), HunterHeatComponent.class);
	public static final ComponentKey<RespawnLeniencyComponent> RESPAWN_LENIENCY = ComponentRegistry.getOrCreate(Nycto.id("respawn_leniency"), RespawnLeniencyComponent.class);
	public static final ComponentKey<SunExposureComponent> SUN_EXPOSURE = ComponentRegistry.getOrCreate(Nycto.id("sun_exposure"), SunExposureComponent.class);
	public static final ComponentKey<TransformationComponent> TRANSFORMATION = ComponentRegistry.getOrCreate(Nycto.id("transformation"), TransformationComponent.class);
	public static final ComponentKey<VampireChargeJumpComponent> VAMPIRE_CHARGE_JUMP = ComponentRegistry.getOrCreate(Nycto.id("vampire_charge_jump"), VampireChargeJumpComponent.class);

	public static final ComponentKey<NightVisionComponent> NIGHT_VISION = ComponentRegistry.getOrCreate(Nycto.id("night_vision"), NightVisionComponent.class);

	public static final ComponentKey<BatFormComponent> BAT_FORM = ComponentRegistry.getOrCreate(Nycto.id("bat_form"), BatFormComponent.class);
	public static final ComponentKey<BloodBarrierComponent> BLOOD_BARRIER = ComponentRegistry.getOrCreate(Nycto.id("blood_barrier"), BloodBarrierComponent.class);
	public static final ComponentKey<BloodrushComponent> BLOODRUSH = ComponentRegistry.getOrCreate(Nycto.id("bloodrush"), BloodrushComponent.class);
	public static final ComponentKey<CarnageComponent> CARNAGE = ComponentRegistry.getOrCreate(Nycto.id("carnage"), CarnageComponent.class);
	public static final ComponentKey<DarkFormComponent> DARK_FORM = ComponentRegistry.getOrCreate(Nycto.id("dark_form"), DarkFormComponent.class);
	public static final ComponentKey<HaemogenesisComponent> HAEMOGENESIS = ComponentRegistry.getOrCreate(Nycto.id("haemogenesis"), HaemogenesisComponent.class);
	public static final ComponentKey<HypnotizedComponent> HYPNOTIZED = ComponentRegistry.getOrCreate(Nycto.id("hypnotized"), HypnotizedComponent.class);
	public static final ComponentKey<KeenSensesComponent> KEEN_SENSES = ComponentRegistry.getOrCreate(Nycto.id("keen_senses"), KeenSensesComponent.class);
	public static final ComponentKey<MistFormComponent> MIST_FORM = ComponentRegistry.getOrCreate(Nycto.id("mist_form"), MistFormComponent.class);
	public static final ComponentKey<VampiricThrallComponent> VAMPIRIC_THRALL = ComponentRegistry.getOrCreate(Nycto.id("vampiric_thrall"), VampiricThrallComponent.class);
	public static final ComponentKey<VampiricVexComponent> VAMPIRIC_VEX = ComponentRegistry.getOrCreate(Nycto.id("vampiric_vex"), VampiricVexComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(SYNCED_CONFIG_VALUES, SyncedConfigValuesComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

		registry.registerFor(LivingEntity.class, BLOOD, BloodComponent::new);
		registry.registerFor(LivingEntity.class, HEAL_BLOCK, HealBlockComponent::new);
		registry.registerForPlayers(HUNTER_HEAT, HunterHeatComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(RESPAWN_LENIENCY, RespawnLeniencyComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(LivingEntity.class, SUN_EXPOSURE, entity -> new SunExposureComponent(entity, false));
		registry.registerForPlayers(TRANSFORMATION, TransformationComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(VAMPIRE_CHARGE_JUMP, VampireChargeJumpComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

		registry.registerForPlayers(NIGHT_VISION, NightVisionComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

		registry.registerForPlayers(BAT_FORM, BatFormComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(LivingEntity.class, BLOOD_BARRIER, BloodBarrierComponent::new);
		registry.registerForPlayers(BLOODRUSH, BloodrushComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(LivingEntity.class, CARNAGE, CarnageComponent::new);
		registry.registerForPlayers(DARK_FORM, DarkFormComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(LivingEntity.class, HAEMOGENESIS, HaemogenesisComponent::new);
		registry.registerFor(MobEntity.class, HYPNOTIZED, HypnotizedComponent::new);
		registry.registerForPlayers(KEEN_SENSES, KeenSensesComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(MIST_FORM, MistFormComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(MobEntity.class, VAMPIRIC_THRALL, VampiricThrallComponent::new);
		registry.registerFor(VexEntity.class, VAMPIRIC_VEX, VampiricVexComponent::new);
	}
}
