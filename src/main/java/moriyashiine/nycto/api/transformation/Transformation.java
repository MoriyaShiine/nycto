/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.transformation;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.misc.AttributeModifierSet;
import moriyashiine.nycto.api.misc.PowerHotbarTextureSet;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class Transformation {
	@Nullable
	protected String translationKey;

	public void onAdded(ServerPlayerEntity player) {
		applyModifiers(player, true);
	}

	public void onRemoved(ServerPlayerEntity player) {
		NyctoRegistries.POWER.forEach(power -> NyctoAPI.removePower(player, power));
		applyModifiers(player, false);
	}

	public void tick(ServerPlayerEntity player) {
	}

	public AttributeModifierSet getAttributeModifiers(ServerPlayerEntity player) {
		return new AttributeModifierSet(new HashSet<>());
	}

	public PowerHotbarTextureSet getPowerHotbarTextureSet() {
		return PowerHotbarTextureSet.EMPTY;
	}

	public String getTranslationKey() {
		if (translationKey == null) {
			translationKey = Util.createTranslationKey("transformation", NyctoRegistries.TRANSFORMATION.getId(this));
		}
		return translationKey;
	}

	public void applyModifiers(ServerPlayerEntity player, boolean shouldHave) {
		getAttributeModifiers(player).attributeModifiers().forEach(pair -> SLibUtils.conditionallyApplyAttributeModifier(player, pair.getLeft(), pair.getRight(), shouldHave));
	}
}
