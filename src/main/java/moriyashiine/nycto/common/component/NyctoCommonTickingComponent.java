/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

public interface NyctoCommonTickingComponent extends NyctoPersistedComponent {
	default void tick() {
	}

	default void serverTick() {
		tick();
	}

	default void clientTick() {
		tick();
	}
}
