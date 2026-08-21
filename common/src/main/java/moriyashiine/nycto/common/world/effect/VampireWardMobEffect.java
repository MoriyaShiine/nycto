package moriyashiine.nycto.common.world.effect;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class VampireWardMobEffect extends MobEffect {
	private static final AttributeModifier NON_PLAYER_ATTACK_MODIFIER = new AttributeModifier(Nycto.id("vampire_ward"), -3, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier NON_PLAYER_SPEED_MODIFIER = new AttributeModifier(Nycto.id("vampire_ward"), -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

	public VampireWardMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void onEffectAdded(MobEffectInstance effectInstance, LivingEntity entity) {
		if (entity instanceof ServerPlayer player && NyctoAPI.isVampire(player)) {
			NyctoAPI.applyHealBlock(player, 100);
			NyctoUtil.disableFormChangePowers(player.level(), player, null);
		}
	}

	@Override
	public void onEffectRemoved(MobEffectInstance effectInstance, LivingEntity entity) {
		applyAttributes(entity, NyctoAPI.hasSunDebuff(entity));
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
		applyAttributes(mob, true);
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
		return true;
	}

	public static void applyAttributes(LivingEntity entity, boolean shouldRemove) {
		if (NyctoAPI.isVampire(entity)) {
			if (shouldRemove) {
				if (!applyTransformationModifiers(entity, true)) {
					applyFallbackModifiers(entity, true);
				}
			} else if (!applyFallbackModifiers(entity, false)) {
				applyTransformationModifiers(entity, false);
			}
		}
	}

	private static boolean applyTransformationModifiers(LivingEntity entity, boolean shouldRemove) {
		if (entity instanceof ServerPlayer player) {
			Transformation transformation = NyctoAPI.getTransformation(player);
			if (transformation.getAttributeModifiers(player).isEmpty()) {
				return false;
			}
			transformation.applyModifiers(player, !shouldRemove);
			return true;
		}
		return false;
	}

	private static boolean applyFallbackModifiers(LivingEntity entity, boolean shouldRemove) {
		return SLibUtils.applyAttributeModifier(entity, Attributes.ATTACK_DAMAGE, NON_PLAYER_ATTACK_MODIFIER, shouldRemove) && SLibUtils.applyAttributeModifier(entity, Attributes.MOVEMENT_SPEED, NON_PLAYER_SPEED_MODIFIER, shouldRemove);
	}
}
