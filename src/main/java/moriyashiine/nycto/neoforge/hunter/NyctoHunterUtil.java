/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.hunter;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.entity.projectile.WoodenStakeProjectile;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class NyctoHunterUtil {
	private static final String VAMPIRE_ENTITY_KEY = "vampire";
	private static final int WOODEN_STAKE_COOLDOWN = 100;

	private NyctoHunterUtil() {
	}

	public static boolean isVampire(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity instanceof Player player) {
			return NyctoData.isVampire(player);
		}
		return entity.getType().builtInRegistryHolder().key().location().getPath().contains(VAMPIRE_ENTITY_KEY);
	}

	public static boolean isLivingVampire(Entity entity) {
		return entity instanceof LivingEntity && isVampire(entity);
	}

	public static boolean isVampireHunterArmor(ItemStack stack) {
		return stack.is(NyctoHunterContent.VAMPIRE_HUNTER_HELMET.get())
				|| stack.is(NyctoHunterContent.VAMPIRE_HUNTER_CHESTPLATE.get())
				|| stack.is(NyctoHunterContent.VAMPIRE_HUNTER_LEGGINGS.get())
				|| stack.is(NyctoHunterContent.VAMPIRE_HUNTER_BOOTS.get());
	}

	public static int getVampireHunterArmorCount(LivingEntity entity) {
		int count = 0;
		for (ItemStack stack : entity.getArmorSlots()) {
			if (isVampireHunterArmor(stack)) {
				count++;
			}
		}
		return count;
	}

	public static boolean blocksBloodDrain(LivingEntity entity) {
		return getVampireHunterArmorCount(entity) >= 1;
	}

	public static boolean hasGarlicAura(LivingEntity entity) {
		return getVampireHunterArmorCount(entity) >= 2;
	}

	public static int woodenStakeCooldown(LivingEntity entity) {
		return getVampireHunterArmorCount(entity) >= 3 ? WOODEN_STAKE_COOLDOWN / 2 : WOODEN_STAKE_COOLDOWN;
	}

	public static boolean blocksVampireCriticals(LivingEntity entity) {
		return getVampireHunterArmorCount(entity) >= 4;
	}

	public static boolean isVampireWeaknessDamage(DamageSource source) {
		if (source.getDirectEntity() instanceof WoodenStakeProjectile) {
			return true;
		}
		if (source.getEntity() instanceof LivingEntity attacker) {
			ItemStack weapon = attacker.getMainHandItem();
			return weapon.is(NyctoItems.WOODEN_STAKE.get()) || weapon.is(NyctoHunterContent.GARLIC_COATED_HALBERD.get());
		}
		return false;
	}
}
