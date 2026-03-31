/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBiomeTags;
import moriyashiine.nycto.common.world.entity.monster.DarkForm;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.entity.projectile.arrow.AconiteArrow;
import moriyashiine.nycto.common.world.entity.projectile.arrow.BloodFlechette;
import moriyashiine.nycto.common.world.entity.projectile.arrow.WoodenStake;
import moriyashiine.nycto.common.world.entity.projectile.throwableitemprojectile.Firebomb;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<WoodenStake> WOODEN_STAKE = registerEntityType("wooden_stake", EntityType.Builder.<WoodenStake>of(WoodenStake::new, MobCategory.MISC).sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20));
	public static final EntityType<AconiteArrow> ACONITE_ARROW = registerEntityType("aconite_arrow", EntityType.Builder.<AconiteArrow>of(AconiteArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20));
	public static final EntityType<Firebomb> FIREBOMB = registerEntityType("firebomb", EntityType.Builder.<Firebomb>of(Firebomb::new, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
	public static final EntityType<BloodFlechette> BLOOD_FLECHETTE = registerEntityType("blood_flechette", EntityType.Builder.<BloodFlechette>of(BloodFlechette::new, MobCategory.MISC).sized(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight()).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20));

	public static final EntityType<Vampire> VAMPIRE = registerEntityType("vampire", EntityType.Builder.of(Vampire::new, MobCategory.MONSTER).sized(0.6F, 1.95F).passengerAttachments(2.0F).ridingOffset(-0.6F).clientTrackingRange(8), Vampire.createAttributes());
	public static final EntityType<Hunter> HUNTER = registerEntityType("hunter", EntityType.Builder.of(Hunter::new, MobCategory.MISC).noLootTable().canSpawnFarFromPlayer().sized(0.6F, 1.95F).passengerAttachments(2.0F).ridingOffset(-0.6F).clientTrackingRange(8), Hunter.createAttributes());

	public static final EntityType<DarkForm> DARK_FORM = registerEntityType("dark_form", EntityType.Builder.of(DarkForm::new, MobCategory.MISC).noSave().noSummon().noLootTable().sized(0.8F, 2.75F), Mob.createMobAttributes());

	public static void init() {
		SpawnPlacements.register(VAMPIRE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Vampire::checkVampireSpawnRules);
		BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_VAMPIRES).and(ctx -> ctx.getBiomeKey() != Biomes.PALE_GARDEN), VAMPIRE.getCategory(), VAMPIRE, 30, 1, 1);

		FabricEntityDataRegistry.register(Nycto.id("hunter_type"), Hunter.HUNTER_TYPE);
	}
}
