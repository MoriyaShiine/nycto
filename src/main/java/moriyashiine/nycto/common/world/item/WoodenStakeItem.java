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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

public class WoodenStakeItem extends BlockItem implements ProjectileItem {
	public static final int DAMAGE = 3;
	private static final int COOLDOWN = 100;

	public WoodenStakeItem(Block block, Properties properties) {
		super(block, properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer() != null && !context.getPlayer().isShiftKeyDown()) {
			return InteractionResult.PASS;
		}
		return super.useOn(context);
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
			itemStack.consume(1, player);
		}
	}

	public static int getCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}

	public static int getCrossbowCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}
}
