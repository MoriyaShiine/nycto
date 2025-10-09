/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.misc;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {LivingEntity.class, PlayerEntity.class}, priority = 1)
public abstract class SunDamageMixin extends Entity {
	public SunDamageMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private DamageSource nycto$sunDamage(DamageSource value) {
		if (value.isIn(DamageTypeTags.IS_FIRE) && !value.isIn(DamageTypeTags.IS_PROJECTILE) && NyctoAPI.isVampire(this)) {
			return getDamageSources().create(ModDamageTypes.SUN, value.getSource(), value.getAttacker());
		}
		return value;
	}
}
