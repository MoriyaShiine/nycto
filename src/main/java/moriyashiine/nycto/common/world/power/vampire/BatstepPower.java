/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.init.*;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class BatstepPower extends VampireActivePower {
	public BatstepPower() {
		super(160);
	}

	@Override
	protected int getBaseCost(LivingEntity player) {
		return 3;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return ModSoundEvents.POWER_BATSTEP_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		teleport(level, player);
	}

	public static void teleport(Level level, LivingEntity entity) {
		ServerLevel serverLevel = (ServerLevel) level;
		Vec3 eyePosition = entity.getEyePosition();
		for (int i = 0; i < 16; i++) {
			Vec3 start = eyePosition.add(entity.getLookAngle().scale(i));
			Vec3 end = start.add(entity.getLookAngle());
			HitResult result = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			level.getEntitiesOfClass(LivingEntity.class, new AABB(result.getLocation().subtract(1), result.getLocation().add(1)), foundEntity -> foundEntity.hurtTime == 0 && foundEntity.isAlive() && SLibUtils.shouldHurt(entity, foundEntity)).forEach(foundEntity -> attack(serverLevel, entity, foundEntity));
			if (result.getType() == HitResult.Type.BLOCK) {
				break;
			}
			Vec3 location = result.getLocation();
			entity.dismountTo(location.x(), location.y(), location.z());
			serverLevel.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 8, entity.getBbWidth() / 4, entity.getBbHeight() / 4, entity.getBbWidth() / 4, 0);
			serverLevel.sendParticles(switch (entity.getRandom().nextInt(3)) {
				case 2 -> ModParticleTypes.BATSTEP_LEFT;
				case 1 -> ModParticleTypes.BATSTEP_RIGHT;
				default -> ModParticleTypes.BATSTEP_CENTER;
			}, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 8, entity.getBbWidth() / 4, entity.getBbHeight() / 4, entity.getBbWidth() / 4, 0);
		}
		entity.setDeltaMovement(Vec3.ZERO);
		entity.needsSync = entity.hurtMarked = true;
		entity.resetFallDistance();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BATSTEP.getCost(entity));
	}

	private static void attack(ServerLevel level, LivingEntity attacker, LivingEntity target) {
		float damage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
		ItemStack stack = attacker.getWeaponItem();
		DamageSource source = Optional.ofNullable(stack.getItem().getItemDamageSource(attacker)).orElse(attacker instanceof Player player ? attacker.damageSources().playerAttack(player) : attacker.damageSources().mobAttack(attacker));
		damage = EnchantmentHelper.modifyDamage(level, stack, target, source, damage);
		damage += stack.getItem().getAttackDamageBonus(target, damage, source);
		if (target.hurtServer(level, source, damage)) {
			EnchantmentHelper.doPostAttackEffects(level, target, source);
			target.addEffect(new MobEffectInstance(ModMobEffects.STUNNED, 60));
		}
	}
}
