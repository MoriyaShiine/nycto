/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.ModConfig;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.mob.DarkFormEntity;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.entity.projectile.AconiteArrowEntity;
import moriyashiine.nycto.common.entity.projectile.BloodFlechetteEntity;
import moriyashiine.nycto.common.entity.projectile.FirebombEntity;
import moriyashiine.nycto.common.entity.projectile.WoodenStakeEntity;
import moriyashiine.nycto.common.tag.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<WoodenStakeEntity> WOODEN_STAKE = registerEntityType("wooden_stake", EntityType.Builder.<WoodenStakeEntity>create(WoodenStakeEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<AconiteArrowEntity> ACONITE_ARROW = registerEntityType("aconite_arrow", EntityType.Builder.<AconiteArrowEntity>create(AconiteArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
	public static final EntityType<FirebombEntity> FIREBOMB = registerEntityType("firebomb", EntityType.Builder.<FirebombEntity>create(FirebombEntity::new, SpawnGroup.MISC).dropsNothing().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
	public static final EntityType<BloodFlechetteEntity> BLOOD_FLECHETTE = registerEntityType("blood_flechette", EntityType.Builder.<BloodFlechetteEntity>create(BloodFlechetteEntity::new, SpawnGroup.MISC).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

	public static final EntityType<VampireEntity> VAMPIRE = registerEntityType("vampire", EntityType.Builder.create(VampireEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).passengerAttachments(2.0F).vehicleAttachment(-0.6F).maxTrackingRange(8), VampireEntity.createVampireAttributes());
	public static final EntityType<HunterEntity> HUNTER = registerEntityType("hunter", EntityType.Builder.create(HunterEntity::new, SpawnGroup.MISC).dropsNothing().spawnableFarFromPlayer().dimensions(0.6F, 1.95F).passengerAttachments(2.0F).vehicleAttachment(-0.6F).maxTrackingRange(8), HunterEntity.createHunterAttributes());

	public static final EntityType<DarkFormEntity> DARK_FORM = registerEntityType("dark_form", EntityType.Builder.create(DarkFormEntity::new, SpawnGroup.MISC).disableSaving().disableSummon().dropsNothing().dimensions(0.8F, 2.75F), MobEntity.createMobAttributes());

	public static void init() {
		SpawnRestriction.register(VAMPIRE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VampireEntity::canSpawn);
		if (ModConfig.spawnVampires) {
			BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_VAMPIRES).and(ctx -> ctx.getBiomeKey() != BiomeKeys.PALE_GARDEN), VAMPIRE.getSpawnGroup(), VAMPIRE, 30, 1, 1);
		}

		FabricTrackedDataRegistry.register(Nycto.id("hunter_type"), HunterEntity.HUNTER_TYPE_TRACKED_DATA);
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4, factories -> factories.add(new TradeOffers.SellItemFactory(ModItems.HUNTER_CONTRACT, 8, 1, 30)));
	}
}
