/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

public interface NyctoPersistedComponent {
	default void readData(NyctoValueInput input) {
	}

	default void writeData(NyctoValueOutput output) {
	}
}
