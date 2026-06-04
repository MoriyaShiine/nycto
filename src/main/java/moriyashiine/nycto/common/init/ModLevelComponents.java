/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.NyctoLevelComponentKey;
import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.component.level.power.BatSwarmComponent;

public final class ModLevelComponents {
	public static final NyctoLevelComponentKey<AuraComponent> AURA = new NyctoLevelComponentKey<>(Nycto.id("aura"), AuraComponent.class, AuraComponent::new);
	public static final NyctoLevelComponentKey<BatSwarmComponent> BAT_SWARM = new NyctoLevelComponentKey<>(Nycto.id("bat_swarm"), BatSwarmComponent.class, BatSwarmComponent::new);

	private ModLevelComponents() {
	}

	public static void init() {
	}

	public static Iterable<NyctoLevelComponentKey<?>> components() {
		return java.util.List.of(AURA, BAT_SWARM);
	}
}
