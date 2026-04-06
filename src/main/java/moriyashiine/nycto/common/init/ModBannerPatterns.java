/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class ModBannerPatterns {
	public static final ResourceKey<BannerPattern> VAMPIRE_BAT = ResourceKey.create(Registries.BANNER_PATTERN, Nycto.id("vampire_bat"));
	public static final ResourceKey<BannerPattern> WOLF_SKULL = ResourceKey.create(Registries.BANNER_PATTERN, Nycto.id("wolf_skull"));
	public static final ResourceKey<BannerPattern> HUNTERS_MARK = ResourceKey.create(Registries.BANNER_PATTERN, Nycto.id("hunters_mark"));

	public static void bootstrap(BootstrapContext<BannerPattern> registry) {
		BannerPatterns.register(registry, VAMPIRE_BAT);
		BannerPatterns.register(registry, WOLF_SKULL);
		BannerPatterns.register(registry, HUNTERS_MARK);
	}
}
