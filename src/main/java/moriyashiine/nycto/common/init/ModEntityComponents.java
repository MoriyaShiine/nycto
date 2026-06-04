/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.NyctoEntityComponentKey;
import moriyashiine.nycto.common.component.entity.*;
import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;

public final class ModEntityComponents {
	public static final NyctoEntityComponentKey<SyncedConfigValuesComponent> SYNCED_CONFIG_VALUES = player("synced_config_values", SyncedConfigValuesComponent.class, SyncedConfigValuesComponent::new);

	public static final NyctoEntityComponentKey<BloodComponent> BLOOD = living("blood", BloodComponent.class, BloodComponent::new);
	public static final NyctoEntityComponentKey<HealBlockComponent> HEAL_BLOCK = living("heal_block", HealBlockComponent.class, HealBlockComponent::new);
	public static final NyctoEntityComponentKey<HunterHeatComponent> HUNTER_HEAT = player("hunter_heat", HunterHeatComponent.class, HunterHeatComponent::new);
	public static final NyctoEntityComponentKey<RespawnLeniencyComponent> RESPAWN_LENIENCY = player("respawn_leniency", RespawnLeniencyComponent.class, RespawnLeniencyComponent::new);
	public static final NyctoEntityComponentKey<SunExposureComponent> SUN_EXPOSURE = living("sun_exposure", SunExposureComponent.class, entity -> new SunExposureComponent(entity, false));
	public static final NyctoEntityComponentKey<TransformationComponent> TRANSFORMATION = player("transformation", TransformationComponent.class, TransformationComponent::new);
	public static final NyctoEntityComponentKey<VampireChargeJumpComponent> VAMPIRE_CHARGE_JUMP = player("vampire_charge_jump", VampireChargeJumpComponent.class, VampireChargeJumpComponent::new);

	public static final NyctoEntityComponentKey<NightVisionComponent> NIGHT_VISION = player("night_vision", NightVisionComponent.class, NightVisionComponent::new);

	public static final NyctoEntityComponentKey<BatFormComponent> BAT_FORM = player("bat_form", BatFormComponent.class, BatFormComponent::new);
	public static final NyctoEntityComponentKey<BloodBarrierComponent> BLOOD_BARRIER = living("blood_barrier", BloodBarrierComponent.class, BloodBarrierComponent::new);
	public static final NyctoEntityComponentKey<BloodrushComponent> BLOODRUSH = player("bloodrush", BloodrushComponent.class, BloodrushComponent::new);
	public static final NyctoEntityComponentKey<CarnageComponent> CARNAGE = living("carnage", CarnageComponent.class, CarnageComponent::new);
	public static final NyctoEntityComponentKey<DarkFormComponent> DARK_FORM = player("dark_form", DarkFormComponent.class, DarkFormComponent::new);
	public static final NyctoEntityComponentKey<HaemogenesisComponent> HAEMOGENESIS = living("haemogenesis", HaemogenesisComponent.class, HaemogenesisComponent::new);
	public static final NyctoEntityComponentKey<HypnotizedComponent> HYPNOTIZED = mob("hypnotized", HypnotizedComponent.class, HypnotizedComponent::new);
	public static final NyctoEntityComponentKey<KeenSensesComponent> KEEN_SENSES = player("keen_senses", KeenSensesComponent.class, KeenSensesComponent::new);
	public static final NyctoEntityComponentKey<MistFormComponent> MIST_FORM = player("mist_form", MistFormComponent.class, MistFormComponent::new);
	public static final NyctoEntityComponentKey<VampiricThrallComponent> VAMPIRIC_THRALL = mob("vampiric_thrall", VampiricThrallComponent.class, VampiricThrallComponent::new);
	public static final NyctoEntityComponentKey<VampiricVexComponent> VAMPIRIC_VEX = entity("vampiric_vex", VampiricVexComponent.class, Vex.class, VampiricVexComponent::new);

	private ModEntityComponents() {
	}

	public static void init() {
	}

	public static Iterable<NyctoEntityComponentKey<?>> components() {
		return java.util.List.of(
				SYNCED_CONFIG_VALUES,
				BLOOD,
				HEAL_BLOCK,
				HUNTER_HEAT,
				RESPAWN_LENIENCY,
				SUN_EXPOSURE,
				TRANSFORMATION,
				VAMPIRE_CHARGE_JUMP,
				NIGHT_VISION,
				BAT_FORM,
				BLOOD_BARRIER,
				BLOODRUSH,
				CARNAGE,
				DARK_FORM,
				HAEMOGENESIS,
				HYPNOTIZED,
				KEEN_SENSES,
				MIST_FORM,
				VAMPIRIC_THRALL,
				VAMPIRIC_VEX);
	}

	public static Iterable<NyctoEntityComponentKey<? extends HasOwnerComponent>> ownerComponents() {
		return java.util.List.of(HYPNOTIZED, VAMPIRIC_THRALL);
	}

	private static <T> NyctoEntityComponentKey<T> player(String name, Class<T> componentClass, java.util.function.Function<Player, T> factory) {
		return entity(name, componentClass, Player.class, factory);
	}

	private static <T> NyctoEntityComponentKey<T> living(String name, Class<T> componentClass, java.util.function.Function<LivingEntity, T> factory) {
		return entity(name, componentClass, LivingEntity.class, factory);
	}

	private static <T> NyctoEntityComponentKey<T> mob(String name, Class<T> componentClass, java.util.function.Function<Mob, T> factory) {
		return entity(name, componentClass, Mob.class, factory);
	}

	private static <E extends net.minecraft.world.entity.Entity, T> NyctoEntityComponentKey<T> entity(String name, Class<T> componentClass, Class<E> entityClass, java.util.function.Function<E, T> factory) {
		return new NyctoEntityComponentKey<>(Nycto.id(name), componentClass, entityClass, factory);
	}
}
