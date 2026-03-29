/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item;

import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.projectile.arrow.WoodenStake;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.Nullable;

public class WoodenStakeItem extends ArrowItem {
	public static final int DAMAGE = 3;
	private static final int COOLDOWN = 100;

	public WoodenStakeItem(Properties properties) {
		super(properties.attributes(ToolMaterial.WOOD.createToolAttributes(DAMAGE, -3.6F)));
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		return new WoodenStake(level, owner, itemStack, firedFromWeapon);
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
		WoodenStake woodenStake = new WoodenStake(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
		woodenStake.pickup = AbstractArrow.Pickup.ALLOWED;
		return woodenStake;
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
		if (attacker instanceof Player player) {
			if (player.isCreative()) {
				return;
			}
			player.getCooldowns().addCooldown(itemStack, getCooldown(attacker));
			for (Item item : BuiltInRegistries.ITEM) {
				if (item.getDefaultInstance().is(ItemTags.CROSSBOW_ENCHANTABLE)) {
					player.getCooldowns().addCooldown(item.getDefaultInstance(), getCrossbowCooldown(attacker));
				}
			}
			attacker.onEquippedItemBroken(this, EquipmentSlot.MAINHAND);
			itemStack.shrink(1);
		}
	}

	public static int getCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}

	public static int getCrossbowCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}
}
