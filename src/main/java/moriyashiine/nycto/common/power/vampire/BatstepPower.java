/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.power.vampire;

import moriyashiine.nycto.common.init.*;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

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
	public SoundEvent getUseSound(PlayerEntity player) {
		return ModSoundEvents.POWER_BATSTEP_USE;
	}

	@Override
	public void use(ServerWorld world, ServerPlayerEntity player) {
		teleport(world, player);
	}

	public static void teleport(World world, LivingEntity entity) {
		ServerWorld serverWorld = (ServerWorld) world;
		Vec3d pos = entity.getEyePos();
		for (int i = 0; i < 16; i++) {
			Vec3d start = pos.add(entity.getRotationVector().multiply(i));
			Vec3d end = start.add(entity.getRotationVector());
			HitResult raycast = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
			world.getEntitiesByClass(LivingEntity.class, new Box(raycast.getPos().subtract(1), raycast.getPos().add(1)), foundEntity -> foundEntity.hurtTime == 0 && foundEntity.isAlive() && SLibUtils.shouldHurt(entity, foundEntity)).forEach(foundEntity -> attack(serverWorld, entity, foundEntity));
			if (raycast.getType() == HitResult.Type.BLOCK) {
				break;
			}
			Vec3d target = raycast.getPos();
			entity.requestTeleportAndDismount(target.getX(), target.getY(), target.getZ());
			serverWorld.spawnParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(), 8, entity.getWidth() / 4, entity.getHeight() / 4, entity.getWidth() / 4, 0);
			serverWorld.spawnParticles(switch (entity.getRandom().nextInt(3)) {
				case 2 -> ModParticleTypes.BATSTEP_LEFT;
				case 1 -> ModParticleTypes.BATSTEP_RIGHT;
				default -> ModParticleTypes.BATSTEP_CENTER;
			}, entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(), 8, entity.getWidth() / 4, entity.getHeight() / 4, entity.getWidth() / 4, 0);
		}
		entity.setVelocity(Vec3d.ZERO);
		entity.velocityDirty = entity.knockedBack = true;
		entity.onLanding();
		ModEntityComponents.BLOOD.get(entity).drain(ModPowers.BATSTEP.getCost(entity));
	}

	private static void attack(ServerWorld world, LivingEntity attacker, LivingEntity target) {
		float damage = (float) attacker.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
		ItemStack stack = attacker.getWeaponStack();
		DamageSource source = Optional.ofNullable(stack.getItem().getDamageSource(attacker)).orElse(attacker instanceof PlayerEntity player ? attacker.getDamageSources().playerAttack(player) : attacker.getDamageSources().mobAttack(attacker));
		damage = EnchantmentHelper.getDamage(world, stack, target, source, damage);
		damage += stack.getItem().getBonusAttackDamage(target, damage, source);
		if (target.damage(world, source, damage)) {
			EnchantmentHelper.onTargetDamaged(world, target, source);
			target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STUNNED, 60));
		}
	}
}
