/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

public interface NyctoClientTickingComponent extends NyctoPersistedComponent {
	default void clientTick() {
	}
}
