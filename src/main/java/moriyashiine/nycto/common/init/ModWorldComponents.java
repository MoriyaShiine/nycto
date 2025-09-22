/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.world.AuraComponent;
import moriyashiine.nycto.common.component.world.power.BatSwarmComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class ModWorldComponents implements WorldComponentInitializer {
	public static final ComponentKey<AuraComponent> AURA = ComponentRegistry.getOrCreate(Nycto.id("aura"), AuraComponent.class);
	public static final ComponentKey<BatSwarmComponent> BAT_SWARM = ComponentRegistry.getOrCreate(Nycto.id("bat_swarm"), BatSwarmComponent.class);

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(AURA, AuraComponent::new);
		registry.register(BAT_SWARM, BatSwarmComponent::new);
	}
}
