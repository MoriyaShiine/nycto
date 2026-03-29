/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.misc;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModDamageTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {LivingEntity.class, Player.class}, priority = 1)
public abstract class SunDamageMixin extends Entity {
	public SunDamageMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
	private DamageSource nycto$sunDamage(DamageSource source) {
		if (source.is(DamageTypeTags.IS_FIRE) && !source.is(DamageTypeTags.IS_PROJECTILE) && NyctoAPI.isVampire(this)) {
			return damageSources().source(ModDamageTypes.SUN, source.getDirectEntity(), source.getEntity());
		}
		return source;
	}
}
