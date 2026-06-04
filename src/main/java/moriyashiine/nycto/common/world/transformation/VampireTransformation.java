/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.transformation;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.misc.AttributeModifierSet;
import moriyashiine.nycto.api.misc.PowerHotbarTextureSet;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class VampireTransformation extends Transformation {
	public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(Nycto.id("vampire_bonus"), 1, AttributeModifier.Operation.ADD_VALUE);

	public static final float VAMPIRE_EXHAUSTION_MULTIPLIER = 3.25F;

	public static boolean ignoreIsCalls = false;

	private static final PowerHotbarTextureSet POWER_HOTBAR_TEXTURE_SET = new PowerHotbarTextureSet(
			Nycto.id("hud/power_hotbar/vampire/hotbar"),
			Nycto.id("hud/power_hotbar/vampire/selection"),
			Nycto.id("hud/power_hotbar/vampire/hotbar_overlay"),
			Nycto.id("hud/power_hotbar/vampire/selection_overlay"));

	@Override
	public void onAdded(ServerPlayer player) {
		super.onAdded(player);
		NyctoAPI.addPower(player, ModPowers.NIGHT_VISION);
		setComponents(player, true);
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		vampireChargeJumpComponent.setEnabled(true);
		vampireChargeJumpComponent.sync();
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		super.onRemoved(player);
		setComponents(player, false);
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		vampireChargeJumpComponent.setEnabled(false);
		vampireChargeJumpComponent.sync();
	}

	@Override
	public void tick(ServerPlayer player) {
		if (player.level().getGameTime() % 6000 == 0) {
			player.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
		}
	}

	@Override
	public AttributeModifierSet getAttributeModifiers(ServerPlayer player) {
		AttributeModifierSet set = super.getAttributeModifiers(player);
		if (!NyctoAPI.hasPower(player, ModPowers.HUMANITY)) {
			set.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(Nycto.id("vampire_bonus"), 1, AttributeModifier.Operation.ADD_VALUE));
			set.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(Nycto.id("vampire_bonus"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			set.addModifier(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(Nycto.id("vampire_bonus"), 1, AttributeModifier.Operation.ADD_VALUE));
		}
		return set;
	}

	@Override
	public PowerHotbarTextureSet getPowerHotbarTextureSet() {
		return POWER_HOTBAR_TEXTURE_SET;
	}

	public static void setComponents(LivingEntity living, boolean vampire) {
		NyctoAPI.giveRespawnLeniency(living);
		ModEntityComponents.BLOOD.get(living).setRegeneratesNaturally(!vampire);
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.get(living);
		sunExposureComponent.setShouldTick(false);
		sunExposureComponent.reset();
		sunExposureComponent.sync();
	}

	public static int getHealTicks(Player player) {
		int ticks = DarkFormPower.isDarkFormActive(player) ? 10 : 15;
		if (NyctoAPI.hasPower(player, ModPowers.HUMANITY)) {
			ticks += 5;
		}
		return ticks;
	}
}
