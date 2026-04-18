/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.util;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum VampireSunExposureMode implements StringRepresentable {
	NORMAL(false, true),
	BURN_BUT_NO_DEBUFF(true, false);

	public static final StringRepresentable.EnumCodec<VampireSunExposureMode> CODEC = StringRepresentable.fromEnum(VampireSunExposureMode::values);

	public final boolean burn, debuff;

	VampireSunExposureMode(boolean burn, boolean debuff) {
		this.burn = burn;
		this.debuff = debuff;
	}

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
