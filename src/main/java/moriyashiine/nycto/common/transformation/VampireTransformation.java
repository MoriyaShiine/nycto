/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.transformation;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.misc.AttributeModifierSet;
import moriyashiine.nycto.api.misc.PowerHotbarTextureSet;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

import java.util.stream.Collectors;

public class VampireTransformation extends Transformation {
	public static final EntityAttributeModifier STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(Nycto.id("vampire_bonus"), 1, EntityAttributeModifier.Operation.ADD_VALUE);

	public static final float VAMPIRE_EXHAUSTION_MULTIPLIER = 3.25F;

	private static final PowerHotbarTextureSet POWER_HOTBAR_TEXTURE_SET = new PowerHotbarTextureSet(
			Nycto.id("hud/power_hotbar/vampire/hotbar"),
			Nycto.id("hud/power_hotbar/vampire/selection"),
			Nycto.id("hud/power_hotbar/vampire/hotbar_overlay"),
			Nycto.id("hud/power_hotbar/vampire/selection_overlay"));

	@Override
	public void onAdded(ServerPlayerEntity player) {
		super.onAdded(player);
		NyctoAPI.addPower(player, ModPowers.NIGHT_VISION);
		setComponents(player, true);
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		vampireChargeJumpComponent.setEnabled(true);
		vampireChargeJumpComponent.sync();
	}

	@Override
	public void onRemoved(ServerPlayerEntity player) {
		super.onRemoved(player);
		setComponents(player, false);
		VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
		vampireChargeJumpComponent.setEnabled(false);
		vampireChargeJumpComponent.sync();
	}

	@Override
	public void tick(ServerPlayerEntity player) {
		if (player.getEntityWorld().getTime() % 6000 == 0) {
			player.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
		}
	}

	@Override
	public AttributeModifierSet getAttributeModifiers(ServerPlayerEntity player) {
		AttributeModifierSet attributeModifierSet = super.getAttributeModifiers(player);
		int negativePowers = NyctoAPI.getPowers(player).stream().filter(instance -> instance.getPower().isIn(ModPowerTags.VAMPIRE_CHOOSABLE) && instance.getPower().isNegative()).collect(Collectors.toSet()).size();
		attributeModifierSet.addModifier(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Nycto.id("vampire_bonus"), 1 + (2 / 3D * negativePowers), EntityAttributeModifier.Operation.ADD_VALUE));
		attributeModifierSet.addModifier(EntityAttributes.MOVEMENT_SPEED, new EntityAttributeModifier(Nycto.id("vampire_bonus"), 0.15 + (0.1 * negativePowers), EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		attributeModifierSet.addModifier(EntityAttributes.JUMP_STRENGTH, new EntityAttributeModifier(Nycto.id("vampire_bonus"), 0.06 * negativePowers, EntityAttributeModifier.Operation.ADD_VALUE));
		attributeModifierSet.addModifier(EntityAttributes.SAFE_FALL_DISTANCE, new EntityAttributeModifier(Nycto.id("vampire_bonus"), 1 + negativePowers, EntityAttributeModifier.Operation.ADD_VALUE));
		return attributeModifierSet;
	}

	@Override
	public PowerHotbarTextureSet getPowerHotbarTextureSet() {
		return POWER_HOTBAR_TEXTURE_SET;
	}

	public static void setComponents(LivingEntity living, boolean vampire) {
		NyctoAPI.giveRespawnLeniency(living);
		ModEntityComponents.BLOOD.get(living).setShouldRegenerateNaturally(!vampire);
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.get(living);
		sunExposureComponent.setShouldTick(vampire);
		sunExposureComponent.reset();
		sunExposureComponent.sync();
	}
}
