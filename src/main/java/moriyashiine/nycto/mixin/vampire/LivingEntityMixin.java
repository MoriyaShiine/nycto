/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.power.vampire.VilePresencePower;
import moriyashiine.nycto.common.tag.ModStatusEffectTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	public abstract double getAttributeBaseValue(RegistryEntry<EntityAttribute> attribute);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyReturnValue(method = "canFreeze", at = @At("RETURN"))
	private boolean nycto$vampire$freezeImmunity(boolean original) {
		return original && !NyctoAPI.isVampire(this);
	}

	@ModifyReturnValue(method = "canBreatheInWater", at = @At("RETURN"))
	private boolean nycto$vampire$breatheUnderwater(boolean original) {
		return original || NyctoAPI.isVampire(this);
	}

	@ModifyExpressionValue(method = "canHaveStatusEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 2))
	private boolean nycto$vampire$ignorePoisonAndRegen(boolean original) {
		return original || NyctoAPI.isVampire(this);
	}

	@ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
	private boolean nycto$vampire$infectionImmunity(boolean original, StatusEffectInstance effect) {
		return original && !(effect.getEffectType().isIn(ModStatusEffectTags.INFECTION) && NyctoAPI.isVampire(this));
	}

	@ModifyReturnValue(method = "hasInvertedHealingAndHarm", at = @At("RETURN"))
	private boolean nycto$vampire$invertedHealingAndHarm(boolean original) {
		return original || NyctoAPI.isVampire(this);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
	private double nycto$vampire(double original, Entity entity) {
		if (entity != null && !entity.getType().isIn(ConventionalEntityTypeTags.BOSSES) && entity.getType().isIn(EntityTypeTags.UNDEAD) && NyctoAPI.isVampire(this)) {
			if ((Object) this instanceof PlayerEntity player && VilePresencePower.shouldApply(player)) {
				return original;
			}
			return original / 4;
		}
		return original;
	}

	@ModifyReturnValue(method = "getAttributeValue", at = @At("RETURN"))
	private double nycto$vampire(double original, RegistryEntry<EntityAttribute> attribute) {
		return attribute == EntityAttributes.BURNING_TIME && NyctoAPI.isVampire(this) ? getAttributeBaseValue(attribute) : original;
	}
}
