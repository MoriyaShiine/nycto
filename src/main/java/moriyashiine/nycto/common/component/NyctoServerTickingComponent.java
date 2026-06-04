/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

public interface NyctoServerTickingComponent extends NyctoPersistedComponent {
	default void serverTick() {
	}
}
