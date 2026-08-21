package moriyashiine.nycto.common.world.transformation;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.misc.AttributeModifierSet;
import moriyashiine.nycto.api.misc.PowerHotbarTextureSet;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class VampireTransformation extends Transformation {
	public static final float VAMPIRE_EXHAUSTION_MULTIPLIER = 3.25F;

	private static final Identifier ATTRIBUTE_ID = Nycto.id("vampire_bonus");

	public static boolean ignoreIsCalls = false;

	private static final PowerHotbarTextureSet POWER_HOTBAR_TEXTURE_SET = new PowerHotbarTextureSet(
			Nycto.id("hud/power_hotbar/vampire/hotbar"),
			Nycto.id("hud/power_hotbar/vampire/selection"),
			Nycto.id("hud/power_hotbar/vampire/hotbar_overlay"),
			Nycto.id("hud/power_hotbar/vampire/selection_overlay"));

	@Override
	public void onAdded(ServerPlayer player) {
		super.onAdded(player);
		NyctoAPI.addPower(player, NyctoPowers.NIGHT_VISION);
		setComponents(player, true);
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		super.onRemoved(player);
		setComponents(player, false);
	}

	@Override
	public AttributeModifierSet getAttributeModifiers(ServerPlayer player) {
		AttributeModifierSet set = super.getAttributeModifiers(player);
		if (!NyctoAPI.hasPower(player, NyctoPowers.HUMANITY)) {
			int weaknesses = NyctoAPI.getWeaknesses(player, NyctoPowerTags.VAMPIRE_CHOOSABLE);
			set.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTRIBUTE_ID, 1 + (2 / 3D * weaknesses), AttributeModifier.Operation.ADD_VALUE));
			set.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(ATTRIBUTE_ID, 0.15 + (0.1 * weaknesses), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			set.addModifier(Attributes.JUMP_STRENGTH, new AttributeModifier(ATTRIBUTE_ID, 0.06 * weaknesses, AttributeModifier.Operation.ADD_VALUE));
			set.addModifier(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(ATTRIBUTE_ID, 1 + weaknesses, AttributeModifier.Operation.ADD_VALUE));
		}
		return set;
	}

	@Override
	public PowerHotbarTextureSet getPowerHotbarTextureSet() {
		return POWER_HOTBAR_TEXTURE_SET;
	}

	public static void setComponents(LivingEntity living, boolean vampire) {
		NyctoAPI.giveRespawnLeniency(living);
		NyctoEntityComponents.BLOOD.get(living).setRegeneratesNaturally(!vampire);
		SunExposureComponent sunExposure = NyctoEntityComponents.SUN_EXPOSURE.get(living);
		sunExposure.setShouldTick(vampire);
		sunExposure.reset();
		sunExposure.sync();
	}

	public static int getHealTicks(Player player) {
		int ticks = DarkFormPower.isDarkFormActive(player) ? 10 : 15;
		if (NyctoAPI.hasPower(player, NyctoPowers.HUMANITY)) {
			ticks += 5;
		}
		return ticks;
	}
}
