/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public final class NyctoDamageTypes {
	public static final ResourceKey<DamageType> WOODEN_STAKE = ResourceKey.create(Registries.DAMAGE_TYPE, NyctoNeoForge.id("wooden_stake"));

	private NyctoDamageTypes() {
	}
}
