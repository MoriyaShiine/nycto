/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.entity.DarkForm;
import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.entity.projectile.AconiteArrowProjectile;
import moriyashiine.nycto.neoforge.entity.projectile.BloodFlechetteProjectile;
import moriyashiine.nycto.neoforge.entity.projectile.FirebombProjectile;
import moriyashiine.nycto.neoforge.entity.projectile.WoodenStakeProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoEntityTypes {
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<EntityType<?>, EntityType<Vampire>> VAMPIRE = ENTITY_TYPES.register("vampire", id -> EntityType.Builder.of(Vampire::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.passengerAttachments(2.0F)
			.ridingOffset(-0.6F)
			.clientTrackingRange(8)
			.build(id.toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<DarkForm>> DARK_FORM = ENTITY_TYPES.register("dark_form", id -> EntityType.Builder.of(DarkForm::new, MobCategory.MISC)
			.noSave()
			.noSummon()
			.sized(0.8F, 2.75F)
			.clientTrackingRange(8)
			.build(id.toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<WoodenStakeProjectile>> WOODEN_STAKE = ENTITY_TYPES.register("wooden_stake", id -> EntityType.Builder.<WoodenStakeProjectile>of(WoodenStakeProjectile::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(id.toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<AconiteArrowProjectile>> ACONITE_ARROW = ENTITY_TYPES.register("aconite_arrow", id -> EntityType.Builder.<AconiteArrowProjectile>of(AconiteArrowProjectile::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(id.toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<FirebombProjectile>> FIREBOMB = ENTITY_TYPES.register("firebomb", id -> EntityType.Builder.<FirebombProjectile>of(FirebombProjectile::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(10)
			.build(id.toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<BloodFlechetteProjectile>> BLOOD_FLECHETTE = ENTITY_TYPES.register("blood_flechette", id -> EntityType.Builder.<BloodFlechetteProjectile>of(BloodFlechetteProjectile::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(id.toString()));

	private NyctoEntityTypes() {
	}

	public static void register(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}

	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(VAMPIRE.get(), Vampire.createAttributes().build());
		event.put(DARK_FORM.get(), DarkForm.createAttributes().build());
	}

	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(VAMPIRE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Vampire::checkVampireSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}
}
