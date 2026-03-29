/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.misc;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public record PowerHotbarTextureSet(@Nullable Identifier powerHotbarTexture,
									@Nullable Identifier powerHotbarSelectionTexture,
									@Nullable Identifier powerHotbarOverlayTexture,
									@Nullable Identifier powerHotbarSelectionOverlayTexture) {
	public static final PowerHotbarTextureSet EMPTY = new PowerHotbarTextureSet(null, null, null, null);
}
